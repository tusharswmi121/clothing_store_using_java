package model;

public class Product {
    private int id;
    private String name;
    private double price;
    private String description;
    private String imagePath;
    private String category; // Added category field

    // Constructor with ProductBuilder
    private Product(ProductBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.description = builder.description;
        this.imagePath = builder.imagePath;
        this.category = builder.category; // Set category from builder
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public String getCategory() { return category; } // Getter for category

    @Override
    public String toString() {
        return name + " - ₹" + price;
    }

    // Static Builder Class
    public static class ProductBuilder {
        private int id;
        private String name;
        private double price;
        private String description;
        private String imagePath;
        private String category; // Added category field to builder

        public ProductBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public ProductBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder setImagePath(String imagePath) {
            this.imagePath = imagePath;
            return this;
        }

        public ProductBuilder setCategory(String category) { // Added setCategory method
            this.category = category;
            return this;
        }

        public Product build() {
            return new Product(this); // Create and return the Product object
        }
    }

    // New constructor for quick creation with just name and price
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.description = "";
        this.imagePath = "";
        this.category = "";
    }
}
