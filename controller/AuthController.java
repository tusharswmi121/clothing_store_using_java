package controller;

import model.Database;
import model.User;
import java.sql.*;

public class AuthController {

    // Login method now returns true if the login is successful, false otherwise
    public User login(String username, String password) {
        try {
            Connection conn = Database.getConnection();
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
    
            if (rs.next()) {
                // If credentials are valid, return the User object
                User user = new User(rs.getInt("id"), rs.getString("username"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Login failed
    }
    

    // Signup method now accepts username and password as String arguments
    public boolean signup(String username, String password) {
        try (Connection con = Database.getConnection()) {
            // Check if the username already exists
            String checkQuery = "SELECT * FROM users WHERE username=?";
            PreparedStatement checkPs = con.prepareStatement(checkQuery);
            checkPs.setString(1, username);
            ResultSet rs = checkPs.executeQuery();

            if (!rs.next()) { // Username is available
                // Insert the new user
                String query = "INSERT INTO users(username, password) VALUES (?,?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);
                int rowsAffected = ps.executeUpdate();

                // If insertion is successful, return true
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if username already exists or error occurs
    }
}
