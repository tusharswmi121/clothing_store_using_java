package view;

import model.Database;
import model.Order;
import model.User;
import controller.CartController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryView extends JFrame {
    private User currentUser;

    public OrderHistoryView(User currentUser) {
        this.currentUser = currentUser;

        setTitle("🧾 Order History");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 250, 245));

        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel heading = new JLabel("🧾 Your Order History", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(60, 60, 60));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        headerPanel.add(heading, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // Order History Table
        JTable orderTable = new JTable();
        orderTable.setModel(createTableModel());
        orderTable.setFillsViewportHeight(true);
        orderTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        orderTable.setRowHeight(30);
        orderTable.setSelectionBackground(new Color(173, 216, 230));  // Light blue selection

        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        JButton backButton = new JButton("Back to Products");
        backButton.setBackground(Color.BLACK);  // Green button color
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        backButton.addActionListener(e -> {
            dispose(); // Close current window
            new ProductView(new CartController(), currentUser); // Open product view
        });

        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load order history into table
        loadOrderHistory(orderTable);

        setVisible(true);
    }

    // Create a model for the table
    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(
                new Object[][]{},
                new String[]{"Product Name", "Price", "Payment Method", "Date"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    // Fetch orders for the current user and populate the table
    private void loadOrderHistory(JTable orderTable) {
        List<Order> orders = fetchOrderHistoryFromDatabase();

        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();

        for (Order order : orders) {
            Object[] rowData = new Object[]{
                    order.getProductName(),
                    "₹" + order.getProductPrice(),
                    order.getPaymentMethod(),
                    order.getPaymentDate()
            };
            model.addRow(rowData);
        }
    }

    // Fetch order history from the database
    private List<Order> fetchOrderHistoryFromDatabase() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT p.name, p.price, pay.payment_method, pay.payment_date " +
                     "FROM payment pay " +
                     "JOIN products p ON pay.product_id = p.id " +
                     "WHERE pay.user_id = ?";

        try (Connection connection = Database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, currentUser.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String productName = rs.getString("name");
                double productPrice = rs.getDouble("price");
                String paymentMethod = rs.getString("payment_method");
                Timestamp paymentDate = rs.getTimestamp("payment_date");

                // Add each order to the list
                orders.add(new Order(productName, productPrice, paymentMethod, paymentDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }


}
