package model.entities.application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Department department = new Department(1, "Books");
        System.out.println(department);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        Seller seller = new Seller(2, "Arthur", "arthur@gmail.com", new Date(), 2300.00, department);
        System.out.println(seller);
    }
}
