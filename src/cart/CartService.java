
package cart;

import java.util.HashMap;
import java.util.Map;
import product.Product;
import product.ProductService;

public class CartService {

    private final Map<Integer, Cart> carts = new HashMap<>();
    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public Cart getCart(int customerId) {
        return carts.computeIfAbsent(
                customerId, Cart::new);
    }

    public boolean addProduct(int customerId, int productId, int quantity) {
        Product product = productService.getProductById(productId);

        if (product == null) {
            return false;
        }

        return getCart(customerId).addProduct(product, quantity);
    }

    public boolean removeProduct(int customerId, int productId) {
        Cart cart = carts.get(customerId);

        return cart != null && cart.removeProduct(productId);
    }

    public boolean updateQuantity(
            int customerId, int productId, int quantity) {

        Cart cart = carts.get(customerId);

        return cart != null
                && cart.updateQuantity(productId, quantity);
    }

    public double getSubtotal(int customerId) {
        Cart cart = carts.get(customerId);

        return cart == null ? 0 : cart.getSubtotal();
    }

    public void clearCart(int customerId) {
        Cart cart = carts.get(customerId);

        if (cart != null) {
            cart.clearCart();
        }
    }
}
