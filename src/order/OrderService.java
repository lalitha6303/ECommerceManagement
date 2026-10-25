
package order;

import cart.Cart;
import cart.CartItem;
import cart.CartService;
import product.Product;
import product.ProductService;

import java.util.ArrayList;
import java.util.List;
import order.OrderStatus;
import discount.DiscountService;
public class OrderService {

    private final CartService cartService;
    private final ProductService productService;
    private final OrderRepository orderRepository;

    private int nextOrderId = 1001;

    private static final double SHIPPING_COST = 100.0;

    private java.util.function.IntPredicate freeShippingChecker =
            customerId -> false;

    public OrderService(
            CartService cartService,
            ProductService productService,
            OrderRepository orderRepository) {

        this.cartService = cartService;
        this.productService = productService;
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(int customerId) {

        Cart cart = cartService.getCart(customerId);

        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return null;
        }

        List<CartItem> cartItems = cart.getItems();
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cartItems) {
            Product product = productService.getProductById(
                    item.getProduct().getProductId());

            if (product == null ||
                    product.getQuantity() < item.getQuantity()) {
                System.out.println(
                        "Insufficient stock for product: "
                                + item.getProduct().getProductName());
                return null;
            }

            orderItems.add(
                    new OrderItem(product, item.getQuantity()));
        }


        double shippingCost = freeShippingChecker.test(customerId)
                ? 0.0
                : SHIPPING_COST;
        if (shippingCost == 0.0) {
            System.out.println(
                    "Free shipping applied - Thanks to your active subscription!"
            );
        }

        Order order = new Order(
                nextOrderId++,
                customerId,
                orderItems,
                shippingCost,
                0
        );


        if (!orderRepository.addOrder(order)) {
            return null;
        }

        for (CartItem item : cartItems) {
            Product product = productService.getProductById(
                    item.getProduct().getProductId());

            productService.updateQuantity(
                    product.getProductId(),
                    product.getQuantity() - item.getQuantity()
            );
        }

        nextOrderId++;
        cartService.clearCart(customerId);

        System.out.println("Order placed successfully!");
        order.displayOrder();

        return order;
    }

    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> getOrdersByCustomer(int customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public boolean updateOrderStatus(int orderId, OrderStatus newStatus) {

        Order order = orderRepository.findById(orderId);

        if (order == null || newStatus == null) {
            return false;
        }

        OrderStatus currentStatus = order.getStatus();

        boolean validTransition = false;

        switch (currentStatus) {
            case ORDER_RECEIVED:
                validTransition = newStatus == OrderStatus.DISPATCHED;
                break;

            case DISPATCHED:
                validTransition = newStatus == OrderStatus.WITH_COURIER;
                break;

            case WITH_COURIER:
                validTransition = newStatus == OrderStatus.DELIVERED;
                break;

            case DELIVERED:
                validTransition = newStatus == OrderStatus.RETURNED;
                break;

            case RETURNED:
                validTransition = newStatus == OrderStatus.RETURN_RECEIVED;
                break;

            case RETURN_RECEIVED:
                validTransition = false;
                break;
        }

        if (!validTransition) {
            System.out.println("Invalid order status transition.");
            return false;
        }

        order.setStatus(newStatus);

        if (newStatus == OrderStatus.DISPATCHED) {
            order.setTrackingNumber("TRK" + order.getOrderId());
        }

        System.out.println("Order status updated to: " + newStatus);
        return true;
    }
    public void setFreeShippingChecker(
            java.util.function.IntPredicate checker) {
        if (checker == null) {
            throw new IllegalArgumentException(
                    "Free shipping checker is required"
            );
        }

        this.freeShippingChecker = checker;
    }

    public String getTrackingDetails(int orderId) {

        Order order = orderRepository.findById(orderId);

        if (order == null) {
            return "Order not found.";
        }

        if (order.getStatus() != OrderStatus.DISPATCHED
                && order.getStatus() != OrderStatus.WITH_COURIER) {
            return "Tracking details are not available for this status.";
        }

        return "Order ID: " + order.getOrderId()
                + ", Tracking Number: " + order.getTrackingNumber()
                + ", Status: " + order.getStatus();
    }

}
