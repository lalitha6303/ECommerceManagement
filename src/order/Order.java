
package order;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private final int orderId;
    private final int customerId;
    private final List<OrderItem> items;
    private final double productTotal;
    private final double shippingCost;
    private final double discount;
    private final double totalAmount;
    private OrderStatus status;
    private String trackingNumber;

    public Order(int orderId, int customerId, List<OrderItem> items,
                 double shippingCost, double discount) {

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order cannot be empty");
        }

        if (shippingCost < 0 || discount < 0) {
            throw new IllegalArgumentException("Invalid order amount");
        }

        this.orderId = orderId;
        this.customerId = customerId;
        this.items = new ArrayList<>(items);

        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }

        this.productTotal = total;
        this.shippingCost = shippingCost;
        this.discount = discount;
        this.totalAmount = Math.max(0, total + shippingCost - discount);
        this.status = OrderStatus.ORDER_RECEIVED;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getProductTotal() {
        return productTotal;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void displayOrder() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer ID: " + customerId);

        for (OrderItem item : items) {
            System.out.println(item.getProductName()
                    + " | Quantity: " + item.getQuantity()
                    + " | Subtotal: " + item.getSubtotal());
        }

        System.out.println("Product Total: " + productTotal);
        System.out.println("Shipping Cost: " + shippingCost);
        System.out.println("Discount: " + discount);
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("Status: " + status);
    }
}
