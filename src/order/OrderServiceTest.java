
package order;

import cart.CartService;
import product.Product;
import product.ProductRepository;
import product.ProductService;

public class OrderServiceTest {

    public static void main(String[] args) {

        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);

        CartService cartService = new CartService(productService);
        OrderRepository orderRepository = new OrderRepository();

        OrderService orderService = new OrderService(
                cartService,
                productService,
                orderRepository
        );

        Product laptop = new Product(
                101, "Laptop", 50000, "Dell", 10
        );

        productService.addProduct(laptop);

        cartService.addProduct(1, 101, 2);

        Order order = orderService.placeOrder(1);

        if (order != null) {
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Expected total: Rs. 100100");
            System.out.println("Actual total: Rs. " + order.getTotalAmount());

            System.out.println(
                    "Remaining stock: "
                            + productService.getProductById(101).getQuantity()
            );

            System.out.println(
                    "Cart empty: " + cartService.getCart(1).isEmpty()
            );

            orderService.updateOrderStatus(
                    order.getOrderId(),
                    OrderStatus.DISPATCHED
            );

            System.out.println(
                    orderService.getTrackingDetails(order.getOrderId())
            );
        }
    }
}
