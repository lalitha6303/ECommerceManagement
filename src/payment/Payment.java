
package payment;

public class Payment {

    private final int paymentId;
    private final int orderId;
    private final double amount;
    private final PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

    public Payment(int paymentId, int orderId, double amount,
                   PaymentMethod paymentMethod) {

        if (paymentId <= 0) {
            throw new IllegalArgumentException("Invalid payment ID");
        }

        if (orderId <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method is required");
        }

        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        if (paymentStatus == null) {
            throw new IllegalArgumentException("Payment status is required");
        }

        this.paymentStatus = paymentStatus;
    }

    public void displayPayment() {
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Order ID: " + orderId);
        System.out.println("Amount: Rs. " + amount);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Payment Status: " + paymentStatus);
    }
}
