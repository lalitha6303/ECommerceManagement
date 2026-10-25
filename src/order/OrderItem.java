
package order;

import product.Product;

public class OrderItem {

    private final int productId;
    private final String productName;
    private final double price;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid order item");
        }

        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return price * quantity;
    }
}
