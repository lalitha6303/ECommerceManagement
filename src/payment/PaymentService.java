
package payment;

import order.Order;
import order.OrderService;

import java.util.List;

public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private int nextPaymentId = 1;

    public PaymentService(PaymentRepository paymentRepository,
                          OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    public Payment processPayment(int orderId,
                                  PaymentMethod paymentMethod) {

        Order order = orderService.getOrderById(orderId);

        if (order == null) {
            System.out.println("Order not found.");
            return null;
        }

        if (paymentMethod == null) {
            System.out.println("Invalid payment method.");
            return null;
        }

        List<Payment> existingPayments =
                paymentRepository.findByOrderId(orderId);

        for (Payment existing : existingPayments) {
            if (existing.getPaymentStatus() == PaymentStatus.SUCCESS) {
                System.out.println("This order is already paid.");
                return null;
            }
        }

        Payment payment = new Payment(
                nextPaymentId++,
                orderId,
                order.getTotalAmount(),
                paymentMethod
        );

        if (paymentMethod == PaymentMethod.CASH_ON_DELIVERY) {
            payment.setPaymentStatus(PaymentStatus.PENDING);
            System.out.println("Order placed with Cash on Delivery.");
        } else {
            // Simulated payment for this Core Java project.
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            System.out.println("Payment successful.");
        }

        boolean added = paymentRepository.addPayment(payment);

        if (!added) {
            System.out.println("Unable to save payment.");
            return null;
        }

        payment.displayPayment();
        return payment;
    }

    public Payment getPaymentById(int paymentId) {
        return paymentRepository.findById(paymentId);
    }

    public List<Payment> getPaymentsByOrderId(int orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }

    public boolean markPaymentSuccessful(int paymentId) {

        Payment payment = paymentRepository.findById(paymentId);

        if (payment == null) {
            System.out.println("Payment not found.");
            return false;
        }

        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            System.out.println("Only pending payments can be completed.");
            return false;
        }

        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        System.out.println("Payment marked as successful.");
        return true;
    }

    public boolean refundPayment(int paymentId) {

        Payment payment = paymentRepository.findById(paymentId);

        if (payment == null) {
            System.out.println("Payment not found.");
            return false;
        }

        if (payment.getPaymentStatus() != PaymentStatus.SUCCESS) {
            System.out.println("Only successful payments can be refunded.");
            return false;
        }

        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        System.out.println("Payment refunded successfully.");
        return true;
    }

}
