
package payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {

    private final List<Payment> payments = new ArrayList<>();

    public boolean addPayment(Payment payment) {

        if (payment == null || findById(payment.getPaymentId()) != null) {
            return false;
        }

        payments.add(payment);
        return true;
    }

    public Payment findById(int paymentId) {

        for (Payment payment : payments) {
            if (payment.getPaymentId() == paymentId) {
                return payment;
            }
        }

        return null;
    }

    public List<Payment> findByOrderId(int orderId) {

        List<Payment> result = new ArrayList<>();

        for (Payment payment : payments) {
            if (payment.getOrderId() == orderId) {
                result.add(payment);
            }
        }

        return result;
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(payments);
    }
}
