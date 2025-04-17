package model;

import java.sql.Timestamp;

public class Order {
    private String productName;
    private double productPrice;
    private String paymentMethod;
    private Timestamp paymentDate;

    public Order(String productName, double productPrice, String paymentMethod, Timestamp paymentDate) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    @Override
    public String toString() {
        return productName + " - ₹" + productPrice + " | Payment Method: " + paymentMethod + " | Date: " + paymentDate;
    }
}
