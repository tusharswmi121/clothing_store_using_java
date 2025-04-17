package view;

import controller.CartController;
import model.Database;
import model.Product;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CartView extends JFrame {
    private CartController cartController;
    private User currentUser;
    private JList<Product> cartList;
    private DefaultListModel<Product> cartListModel;
    private JButton removeButton, makePaymentButton;

    public CartView(CartController cartController, User currentUser) {
        this.cartController = cartController;
        this.currentUser = currentUser;

        setTitle("🛒 Your Cart");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(255, 250, 245));

        // Top label
        JLabel cartLabel = new JLabel("🧾 Items in Your Cart", SwingConstants.CENTER);
        cartLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        cartLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(cartLabel, BorderLayout.NORTH);

        // Cart List
        cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);
        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cartList.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(cartList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        bottomPanel.setBackground(Color.BLACK);

        removeButton = new JButton("❌ Remove from Cart");
        styleButton(removeButton, new Color(220, 20, 60));

        makePaymentButton = new JButton("💳 Make Payment");
        styleButton(makePaymentButton, new Color(0, 128, 128));

        bottomPanel.add(removeButton);
        bottomPanel.add(makePaymentButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button Actions
        removeButton.addActionListener(e -> {
            Product selectedProduct = cartList.getSelectedValue();
            if (selectedProduct != null) {
                cartController.removeFromCart(selectedProduct);
                cartListModel.removeElement(selectedProduct);
                JOptionPane.showMessageDialog(this, selectedProduct.getName() + " removed from cart.");
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item to remove.");
            }
        });

        makePaymentButton.addActionListener(e -> {
            if (cartListModel.size() > 0) {
                String[] paymentMethods = {"Credit Card", "Debit Card", "PayPal", "UPI", "Cash on Delivery"};
                String selectedPaymentMethod = (String) JOptionPane.showInputDialog(
                        this,
                        "Select a payment method:",
                        "Payment",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        paymentMethods,
                        paymentMethods[0]
                );

                if (selectedPaymentMethod != null) {
                    for (int i = 0; i < cartListModel.size(); i++) {
                        Product product = cartListModel.get(i);
                        savePaymentDetails(currentUser, product, selectedPaymentMethod);
                    }
                    
                    JOptionPane.showMessageDialog(this, "✅ Payment successful!");
                    cartListModel.clear();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Your cart is empty.");
            }
        });

        loadCartItems();
        setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);  // Change text color to black
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(180, 40));
    }
    

    private void loadCartItems() {
        List<Product> cartItems = cartController.getCartItems();
        for (Product product : cartItems) {
            cartListModel.addElement(product);
        }
    }

    private void savePaymentDetails(User user, Product product, String paymentMethod) {
        String sql = "INSERT INTO payment (user_id, product_id, payment_method) VALUES (?, ?, ?)";

        try (Connection connection = Database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, user.getId());
            stmt.setInt(2, product.getId());
            stmt.setString(3, paymentMethod);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
