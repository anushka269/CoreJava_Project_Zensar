package com.acc.service;

import com.acc.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OprableImpl implements Oprable {
    private Connection con;
    private final Scanner sc = new Scanner(System.in);

    public OprableImpl(Connection con) {
        this.con = con;
    }

    @Override
    public void addProduct() {
        Product product = new Product();

        System.out.print("Enter product name: ");
        product.setName(sc.nextLine());
        System.out.print("Enter product description: ");
        product.setDescription(sc.nextLine());
        System.out.print("Enter product price: ");
        product.setPrice(sc.nextDouble());
        System.out.print("Enter product quantity: ");
        product.setQuantity(sc.nextInt());
        sc.nextLine();  // Consume newline
        System.out.print("Enter product category: ");
        product.setCategory(sc.nextLine());

        try {
            String query = "INSERT INTO products (name, description, price, quantity, category) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getDescription());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.setString(5, product.getCategory());
            pstmt.executeUpdate();

            System.out.println("Product added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding product.");
            e.printStackTrace();
        }
    }

    @Override

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try {
            String query = "SELECT * FROM products";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Print table header
            System.out.println("+----+---------------------+---------------------+---------+----------+-----------------+");
            System.out.println("| ID | Name                | Description         | Price   | Quantity | Category        |");
            System.out.println("+----+---------------------+---------------------+---------+----------+-----------------+");

            // Process each row and print product data in a table format
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setCategory(rs.getString("category"));
                products.add(product);

                // Print the product data in a table row
                System.out.printf("| %-2d | %-19s | %-19s | %-7.2f | %-8d | %-15s |\n", 
                                   product.getProductId(),
                                   product.getName(),
                                   product.getDescription(),
                                   product.getPrice(),
                                   product.getQuantity(),
                                   product.getCategory());
            }

            System.out.println("+----+---------------------+---------------------+---------+----------+-----------------+");

        } catch (SQLException e) {
            System.out.println("Error reading products.");
            e.printStackTrace();
        }

        return products;
    }


    @Override
    public Product find(int id) {
        Product product = null;

        try {
            String query = "SELECT * FROM products WHERE product_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setCategory(rs.getString("category"));
            }

        } catch (SQLException e) {
            System.out.println("Error finding product.");
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public void updateProduct() {
        System.out.print("Enter the ID of the product to update: ");
        int productId = sc.nextInt();
        sc.nextLine();  // Consume newline

        System.out.println("What do you want to update?");
        System.out.println("1. Name");
        System.out.println("2. Description");
        System.out.println("3. Price");
        System.out.println("4. Quantity");
        System.out.println("5. Category");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine();  // Consume newline

        try {
            String query = null;
            PreparedStatement pstmt = null;

            switch (choice) {
                case 1: // Update Name
                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    query = "UPDATE products SET name = ? WHERE product_id = ?";
                    pstmt = con.prepareStatement(query);
                    pstmt.setString(1, newName);
                    pstmt.setInt(2, productId);
                    break;

                case 2: // Update Description
                    System.out.print("Enter new description: ");
                    String newDescription = sc.nextLine();
                    query = "UPDATE products SET description = ? WHERE product_id = ?";
                    pstmt = con.prepareStatement(query);
                    pstmt.setString(1, newDescription);
                    pstmt.setInt(2, productId);
                    break;

                case 3: // Update Price
                    System.out.print("Enter new price: ");
                    double newPrice = sc.nextDouble();
                    query = "UPDATE products SET price = ? WHERE product_id = ?";
                    pstmt = con.prepareStatement(query);
                    pstmt.setDouble(1, newPrice);
                    pstmt.setInt(2, productId);
                    break;

                case 4: // Update Quantity
                    System.out.print("Enter new quantity: ");
                    int newQuantity = sc.nextInt();
                    query = "UPDATE products SET quantity = ? WHERE product_id = ?";
                    pstmt = con.prepareStatement(query);
                    pstmt.setInt(1, newQuantity);
                    pstmt.setInt(2, productId);
                    break;

                case 5: // Update Category
                    System.out.print("Enter new category: ");
                    String newCategory = sc.nextLine();
                    query = "UPDATE products SET category = ? WHERE product_id = ?";
                    pstmt = con.prepareStatement(query);
                    pstmt.setString(1, newCategory);
                    pstmt.setInt(2, productId);
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    return;
            }

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("No product found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating product.");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct() {
        System.out.print("Enter the ID of the product to delete: ");
        int productId = sc.nextInt();

        try {
            String query = "DELETE FROM products WHERE product_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, productId);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("No product found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting product.");
            e.printStackTrace();
        }
    }

    @Override
    public void searchProduct() {
        System.out.print("Enter product name or category to search: ");
        String searchQuery = sc.nextLine();

        try {
            String query = "SELECT * FROM products WHERE name LIKE ? OR category LIKE ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, "%" + searchQuery + "%");
            pstmt.setString(2, "%" + searchQuery + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Product ID: " + rs.getInt("product_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Price: " + rs.getDouble("price"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("-----------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error searching for product.");
            e.printStackTrace();
        }
    }
}
