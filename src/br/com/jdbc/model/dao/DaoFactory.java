package br.com.jdbc.model.dao;

import br.com.jdbc.db.DB;
import br.com.jdbc.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }
}
