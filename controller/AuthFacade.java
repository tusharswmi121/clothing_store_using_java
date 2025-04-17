package controller;

import model.User;

public class AuthFacade {
    private AuthController authController;

    public AuthFacade() {
        authController = new AuthController();
    }

    // Login functionality
    public User login(String username, String password) {
        return authController.login(username, password);
    }

    // Signup functionality
    public boolean signup(String username, String password) {
        return authController.signup(username, password);
    }

}
