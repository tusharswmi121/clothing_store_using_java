package controller;

import java.util.ArrayList;
import java.util.List;
import model.Product;

public class CartController {
    private List<Product> cart;

    public CartController() {
        cart = new ArrayList<>();
    }

    public void addToCart(Product product) {
        cart.add(product);
    }

    public void removeFromCart(Product product) {
        cart.remove(product);
    }

    public List<Product> getCartItems() {
        return cart;
    }

    public double getTotalPrice() {
        return cart.stream()
                   .mapToDouble(Product::getPrice)
                   .sum();
    }

    public void clearCart() {
        cart.clear();
    }
}
