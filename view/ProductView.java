package view;

import controller.CartController;
import controller.ProductController;
import model.Product;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class ProductView extends JFrame {
    private CartController cartController;
    private User currentUser;
    private Product selectedProduct = null;
    private JPanel selectedPanel = null;
    private List<Product> allProducts;
    private JPanel productGrid;
    private JComboBox<String> categoryComboBox;

    public ProductView(CartController cartController, User currentUser) {
        this.cartController = cartController;
        this.currentUser = currentUser;

        setTitle("Product Browser");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for heading and category dropdown
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel heading = new JLabel("Browse Our Products", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        topPanel.add(heading, BorderLayout.NORTH);

        // Dropdown for category filtering
        categoryComboBox = new JComboBox<>(new String[]{
                "All", "Clothes", "Perfumes", "Shoes", "Watches", "Phones", "Earbuds", "Bottles"
        });
        categoryComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        categoryComboBox.setPreferredSize(new Dimension(200, 40)); // wider and taller dropdown
        categoryComboBox.setSelectedItem("All");
        categoryComboBox.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        topPanel.add(categoryComboBox, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Product grid
        productGrid = new JPanel(new GridLayout(0, 3, 20, 20));
        productGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        productGrid.setBackground(new Color(245, 255, 250));
        JScrollPane scrollPane = new JScrollPane(productGrid);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        JButton addButton = new JButton("Add to Cart");
        JButton viewCartButton = new JButton("View Cart");
        JButton orderHistoryButton = new JButton("Order History");

        bottomPanel.add(addButton);
        bottomPanel.add(viewCartButton);
        bottomPanel.add(orderHistoryButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load all products once
        ProductController productController = new ProductController();
        allProducts = productController.fetchAllProducts();

        // Load initially all
        loadProducts("All");

        // Filter on category change
        categoryComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                loadProducts(selectedCategory);
            }
        });

        // Button actions
        addButton.addActionListener(e -> {
            if (selectedProduct != null) {
                cartController.addToCart(selectedProduct);
                JOptionPane.showMessageDialog(ProductView.this, selectedProduct.getName() + " added to cart.");
                if (selectedPanel != null) {
                    selectedPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                    selectedPanel.setBackground(Color.WHITE);
                    selectedPanel = null;
                    selectedProduct = null;
                }
            } else {
                JOptionPane.showMessageDialog(ProductView.this, "Please select a product first.");
            }
        });

        viewCartButton.addActionListener(e -> new CartView(cartController, currentUser)); // Open cart view

        orderHistoryButton.addActionListener(e -> new OrderHistoryView(currentUser)); // Open order history view

        setVisible(true);
    }

    private void loadProducts(String category) {
        productGrid.removeAll();
        List<Product> productsToDisplay;

        if ("All".equalsIgnoreCase(category)) {
            productsToDisplay = allProducts;
        } else {
            productsToDisplay = allProducts.stream()
                    .filter(p -> p.getCategory() != null && p.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }

        for (Product product : productsToDisplay) {
            JPanel card = createProductCard(product);
            productGrid.add(card);
        }

        productGrid.revalidate();
        productGrid.repaint();
    }

    private JPanel createProductCard(Product product) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(280, 250));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        panel.setBackground(Color.WHITE);

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (product.getImagePath() != null && new File(product.getImagePath()).exists()) {
            ImageIcon icon = new ImageIcon(product.getImagePath());
            Image img = icon.getImage().getScaledInstance(270, 145, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        }

        panel.add(imageLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel priceLabel = new JLabel("₹" + product.getPrice());
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setBackground(new Color(60, 179, 113));
        addToCartButton.setForeground(Color.BLACK);

        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(addToCartButton);

        panel.add(infoPanel, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedPanel != null) {
                    selectedPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                    selectedPanel.setBackground(Color.WHITE);
                }
                selectedPanel = panel;
                selectedProduct = product;
                selectedPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
                selectedPanel.setBackground(new Color(173, 216, 230));
            }
        });

        addToCartButton.addActionListener(e -> {
            cartController.addToCart(product);
            JOptionPane.showMessageDialog(ProductView.this, product.getName() + " added to cart.");
        });

        return panel;
    }
}
