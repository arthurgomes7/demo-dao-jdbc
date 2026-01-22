package br.com.jdbc.model.dao.impl;

import br.com.jdbc.db.DB;
import br.com.jdbc.db.DbException;
import br.com.jdbc.model.dao.SellerDao;
import br.com.jdbc.model.entities.Department;
import br.com.jdbc.model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement st = null;

        try {
            st =  conn.prepareStatement(
                    "INSERT INTO seller "
                            + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                             +"(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS); // Retorna o ID do Seller que foi adicionado

            st.setString(1, seller.getNome());
            st.setString(2, seller.getEmail());
            st.setDate(3, new Date(seller.getDataAniversario().getTime()));
            st.setDouble(4, seller.getSalario());
            st.setInt(5, seller.getDepartamento().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    seller.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Erro inesperado, NENHUMA linha foi alterada.");
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;

        try {
            st =  conn.prepareStatement(
                    "UPDATE seller "
                            + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                            + "WHERE Id = ?",
                    Statement.RETURN_GENERATED_KEYS); // Retorna o ID do Seller que foi adicionado

            st.setString(1, seller.getNome());
            st.setString(2, seller.getEmail());
            st.setDate(3, new Date(seller.getDataAniversario().getTime()));
            st.setDouble(4, seller.getSalario());
            st.setInt(5, seller.getDepartamento().getId());
            st.setInt(6, seller.getId());

            st.executeUpdate();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM seller WHERE Id = ?");
            st.setInt(1, id);

            int rows = st.executeUpdate();

            if (rows == 0){
                throw new DbException("ID não encontrado");
            }
        }
        catch (SQLException e){
           throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Department dp = instanciarDepartamento(rs);
                return instanciarSeller(rs, dp);
            }
        } catch (SQLException e) {
            e.getMessage();
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        return null;
    }

    private Seller instanciarSeller(ResultSet rs, Department dp) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setNome(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setSalario(rs.getDouble("BaseSalary"));
        seller.setDataAniversario(rs.getDate("BirthDate"));
        seller.setDepartamento(dp);

        return seller;
    }

    private Department instanciarDepartamento(ResultSet rs) throws SQLException {
        Department dp = new Department();
        dp.setId(rs.getInt("DepartmentId"));
        dp.setName(rs.getString("DepName"));

        return dp;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Id");

            rs = st.executeQuery();

            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = instanciarDepartamento(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instanciarSeller(rs, dep);
                sellerList.add(seller);
            }
            return sellerList;
        } catch (SQLException e) {
            e.getMessage();
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return null;
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Id");

            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> sellerList = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = instanciarDepartamento(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instanciarSeller(rs, dep);
                sellerList.add(seller);
            }
            return sellerList;
        } catch (SQLException e) {
            e.getMessage();
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

        return null;
    }
}
