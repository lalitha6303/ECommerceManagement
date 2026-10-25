
package subscription;

import product.Product;
import product.ProductRepository;
import product.ProductService;
import cart.CartService;
import order.Order;
import order.OrderRepository;
import order.OrderService;

public class SubscriptionShippingTest {

    public static void main(String[] args) {

        ProductRepository productRepository =
                new ProductRepository();

        ProductService productService =
                new ProductService(productRepository);

        productService.addProduct(
                new Product(101, "Laptop", 50000, "Dell", 5)
        );

        CartService cartService =
                new CartService(productService);

        OrderRepository orderRepository =
                new OrderRepository();

        OrderService orderService =
                new OrderService(
                        cartService, productService, orderRepository
                );

        SubscriptionRepository subscriptionRepository =
                new SubscriptionRepository();

        SubscriptionService subscriptionService =
                new SubscriptionService(subscriptionRepository);

        subscriptionService.createSubscription(
                1, SubscriptionPlan.YEARLY
        );

        orderService.setFreeShippingChecker(
                subscriptionService::hasFreeShipping
        );

        cartService.addProduct(1, 101, 1);

        Order order = orderService.placeOrder(1);

        if (order != null) {
            System.out.println("Shipping: Rs. "
                    + order.getShippingCost());
            System.out.println("Total: Rs. "
                    + order.getTotalAmount());
        }
    }
}
