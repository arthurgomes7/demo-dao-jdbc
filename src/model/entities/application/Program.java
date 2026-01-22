package model.entities.application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {


        System.out.println("=== Teste 1: findById ===");
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(1);
        System.out.println(seller);

        System.out.println("\n=== Teste 2: findByDepartment ===");
        Department department = new Department(1, null);
        List<Seller> sellerList = sellerDao.findByDepartment(department);
        for (Seller s : sellerList){
            System.out.println(s);
        }

        System.out.println("\n=== Teste 3: findAll ===");
        List<Seller> sellerList2 = sellerDao.findAll();
        for (Seller s : sellerList2){
            System.out.println(s);
        }
    }
}
