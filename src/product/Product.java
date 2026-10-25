
package product;

public class Product {

    private int productId;
    private String productName;
    private double price;
    private String companyName;
    private int quantity;

    public Product(int productId, String productName, double price,
                   String companyName, int quantity) {
        this.productId = productId;
        this.productName = productName;
        setPrice(price);
        this.companyName = companyName;
        setQuantity(quantity);
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public void displayProduct() {
        System.out.println("Product ID: " + productId);
        System.out.println("Product Name: " + productName);
        System.out.println("Price: " + price);
        System.out.println("Company: " + companyName);
        System.out.println("Available Quantity: " + quantity);
    }
}
