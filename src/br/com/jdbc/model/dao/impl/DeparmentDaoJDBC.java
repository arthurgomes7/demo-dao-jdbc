package br.com.jdbc.model.dao.impl;

import br.com.jdbc.db.DB;
import br.com.jdbc.db.DbException;
import br.com.jdbc.model.dao.DepartmentDao;
import br.com.jdbc.model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeparmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    @Override
    public void insert(Department department) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("INSERT INTO department "
                    + "(Id, Name) "
                    + "VALUES "
                    +"(?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            st.setInt(1, 5);
            st.setInt(2, 6);

            int rows = st.executeUpdate();

            if (rows > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    department.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else{
                throw new SQLException("ERROR! Nenhuma linha foi modificada");
            }
        }
        catch (SQLException e){
            e.getMessage();
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department department) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE department "
                            + "SET Id = ?, Name = ? "
                            + "WHERE Id = ?");

            st.setInt(1, department.getId());
            st.setString(2, department.getName());
            st.setInt(3, department.getId());

            int rows = st.executeUpdate();
            if (rows > 0){
                System.out.println("Sucess! Rows Affected: " + rows);
            }
            else {
                throw new DbException("ERROR! Nenhuma linha afetada");
            }
        }
        catch (SQLException e){
            e.getMessage();
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
            st.setInt(1, id);

            int rows = st.executeUpdate();

            if (rows == 0){
                System.out.println("ID não encontrado");
            }

        }
        catch (SQLException e) {
            e.getMessage();
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM department WHERE Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department obj = new Department();
                obj.setId(rs.getInt("Id"));
                obj.setName(rs.getString("Name"));
                return obj;
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM department ORDER BY Name");
            rs = st.executeQuery();

            List<Department> list = new ArrayList<>();

            while (rs.next()) {
                Department obj = new Department();
                obj.setId(rs.getInt("Id"));
                obj.setName(rs.getString("Name"));
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
