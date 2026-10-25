
package order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private final List<Order> orders = new ArrayList<>();

    public boolean addOrder(Order order) {
        if (findById(order.getOrderId()) != null) {
            return false;
        }

        orders.add(order);
        return true;
    }

    public Order findById(int orderId) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public List<Order> findByCustomerId(int customerId) {
        List<Order> result = new ArrayList<>();

        for (Order order : orders) {
            if (order.getCustomerId() == customerId) {
                result.add(order);
            }
        }

        return result;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }
}
