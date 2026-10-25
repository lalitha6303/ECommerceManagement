
package cart;

import java.util.ArrayList;
import java.util.List;
import product.Product;

public class Cart {

    private final int customerId;
    private final List<CartItem> items = new ArrayList<>();

    public Cart(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public boolean addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0
                || quantity > product.getQuantity()) {
            return false;
        }

        for (CartItem item : items) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                int newQuantity = item.getQuantity() + quantity;

                if (newQuantity > product.getQuantity()) {
                    return false;
                }

                item.setQuantity(newQuantity);
                return true;
            }
        }

        items.add(new CartItem(product, quantity));
        return true;
    }

    public boolean removeProduct(int productId) {
        return items.removeIf(
                item -> item.getProduct().getProductId() == productId);
    }

    public boolean updateQuantity(int productId, int quantity) {
        if (quantity <= 0) {
            return false;
        }

        for (CartItem item : items) {
            if (item.getProduct().getProductId() == productId) {
                if (quantity > item.getProduct().getQuantity()) {
                    return false;
                }

                item.setQuantity(quantity);
                return true;
            }
        }

        return false;
    }

    public double getSubtotal() {
        double total = 0;

        for (CartItem item : items) {
            total += item.getSubtotal();
        }

        return total;
    }

    public void clearCart() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
