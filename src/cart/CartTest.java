
package cart;

import product.Product;

public class CartTest {

    public static void main(String[] args) {

        Product laptop = new Product(
                201, "Laptop", 50000, "Dell", 5);

        Product mouse = new Product(
                202, "Mouse", 500, "Logitech", 10);

        Cart cart = new Cart(101);

        System.out.println("ADD LAPTOP");
        System.out.println(cart.addProduct(laptop, 2));

        System.out.println("\nADD MOUSE");
        System.out.println(cart.addProduct(mouse, 3));

        System.out.println("\nCART ITEMS");

        for (CartItem item : cart.getItems()) {
            System.out.println(
                    item.getProduct().getProductName()
                            + " | Quantity: " + item.getQuantity()
                            + " | Subtotal: " + item.getSubtotal());
        }

        System.out.println("\nCART TOTAL: " + cart.getSubtotal());

        System.out.println("\nUPDATE LAPTOP QUANTITY");
        System.out.println(cart.updateQuantity(201, 3));
        System.out.println("UPDATED TOTAL: " + cart.getSubtotal());

        System.out.println("\nTRY EXCEEDING STOCK");
        System.out.println(cart.addProduct(laptop, 10));

        System.out.println("\nREMOVE MOUSE");
        System.out.println(cart.removeProduct(202));
        System.out.println("FINAL TOTAL: " + cart.getSubtotal());
    }
}
