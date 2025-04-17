package controller;

import model.Database;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductController {

    // Fetch all products from the database
    public List<Product> fetchAllProducts() {
        List<Product> productList = new ArrayList<>();

        try (Connection con = Database.getConnection()) {
            String query = "SELECT * FROM products";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Product product = new Product.ProductBuilder()
                        .setId(rs.getInt("id"))
                        .setName(rs.getString("name"))
                        .setPrice(rs.getDouble("price"))
                        .setDescription(rs.getString("description"))
                        .setImagePath(rs.getString("image_path"))
                        .setCategory(rs.getString("category")) // Fetch category
                        .build();
            
                productList.add(product);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    // Get a specific product by ID
    public Product getProductById(int productId) {
        try (Connection con = Database.getConnection()) {
            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Product.ProductBuilder()
                        .setId(rs.getInt("id"))
                        .setName(rs.getString("name"))
                        .setPrice(rs.getDouble("price"))
                        .setDescription(rs.getString("description"))
                        .setImagePath(rs.getString("image_path"))
                        .setCategory(rs.getString("category")) // Fetch category
                        .build();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
