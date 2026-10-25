
package order;

import java.util.ArrayList;
import java.util.List;
import product.Product;

public class OrderTest {

    public static void main(String[] args) {

        Product laptop = new Product(
                201, "Laptop", 50000, "Dell", 5);

        Product mouse = new Product(
                202, "Mouse", 500, "Logitech", 10);

        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(laptop, 2));
        items.add(new OrderItem(mouse, 3));

        Order order = new Order(
                1001, 101, items, 100, 500);

        order.displayOrder();

        System.out.println("\nUPDATE STATUS");
        order.setStatus(OrderStatus.DISPATCHED);
        System.out.println("Status: " + order.getStatus());

        order.setTrackingNumber("TRK1001");
        System.out.println("Tracking: " + order.getTrackingNumber());
    }
}
