package view;

import controller.AuthFacade;
import callback.LoginCallback;

import javax.swing.*;
import java.awt.*;

public class SignupView extends JFrame {
    private LoginCallback callback;

    public SignupView(LoginCallback callback) {
        this.callback = callback;

        setTitle("Sign Up - Online Clothing Store");
        setSize(450, 370);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Create Your Account ✨", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        formPanel.setBackground(new Color(245, 255, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(userLabel, gbc);

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gbc.gridy = 1;
        formPanel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 2;
        formPanel.add(passLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gbc.gridy = 3;
        formPanel.add(passwordField, gbc);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signupButton.setBackground(Color.BLACK);
        signupButton.setForeground(Color.BLACK);
        gbc.gridy = 4;
        gbc.insets = new Insets(15, 0, 0, 0);
        formPanel.add(signupButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        signupButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            AuthFacade authFacade = new AuthFacade();
            boolean success = authFacade.signup(username, password);

            if (success) {
                JOptionPane.showMessageDialog(this, "Signup successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close signup window
                new LoginView(callback); // Use the same callback when returning to login
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists.", "Signup Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
