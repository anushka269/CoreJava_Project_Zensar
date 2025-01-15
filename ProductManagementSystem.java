package com.acc;

import com.acc.model.Product;
import com.acc.service.Oprable;
import com.acc.service.OprableImpl;
import com.acc.util.DBConnection;

import java.util.List;
import java.util.Scanner;

public class ProductManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Oprable productManager = new OprableImpl(DBConnection.getConnection());

        int choice;
        do {
            System.out.println("\n----- Product Management System -----");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    productManager.addProduct();
                    break;
                case 2:
                    List<Product> products = productManager.findAll();
                    
                    break;
                case 3:
                    productManager.updateProduct();
                    break;
                case 4:
                    productManager.deleteProduct();
                    break;
                case 5:
                    productManager.searchProduct();
                    break;
                case 6:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 6);

        DBConnection.closeConnection();
    }
}
