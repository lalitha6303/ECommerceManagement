
package cart;

import product.Product;
import product.ProductRepository;
import product.ProductService;

public class CartServiceTest {

    public static void main(String[] args) {

        ProductRepository repository = new ProductRepository();
        ProductService productService = new ProductService(repository);

        productService.addProduct(
                new Product(201, "Laptop", 50000, "Dell", 5));

        productService.addProduct(
                new Product(202, "Mouse", 500, "Logitech", 10));

        CartService cartService = new CartService(productService);

        System.out.println("CUSTOMER 101 ADDS LAPTOP");
        System.out.println(cartService.addProduct(101, 201, 2));

        System.out.println("\nCUSTOMER 102 ADDS MOUSE");
        System.out.println(cartService.addProduct(102, 202, 3));

        System.out.println("\nCUSTOMER 101 CART TOTAL");
        System.out.println(cartService.getSubtotal(101));

        System.out.println("\nCUSTOMER 102 CART TOTAL");
        System.out.println(cartService.getSubtotal(102));

        System.out.println("\nREMOVE LAPTOP FROM CUSTOMER 101");
        System.out.println(cartService.removeProduct(101, 201));

        System.out.println("CUSTOMER 101 TOTAL");
        System.out.println(cartService.getSubtotal(101));
    }
}
