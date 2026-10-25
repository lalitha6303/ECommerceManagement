
package payment;

public class PaymentRepositoryTest {

    public static void main(String[] args) {

        PaymentRepository repository = new PaymentRepository();

        Payment p1 = new Payment(
                1, 1001, 2500, PaymentMethod.UPI
        );

        Payment p2 = new Payment(
                2, 1002, 1500, PaymentMethod.DEBIT_CARD
        );

        System.out.println("Added p1: " + repository.addPayment(p1));
        System.out.println("Added p2: " + repository.addPayment(p2));

        System.out.println("Duplicate ID: " +
                repository.addPayment(p1));

        System.out.println("\nSearch payment ID 1:");

        Payment found = repository.findById(1);

        if (found != null) {
            found.displayPayment();
        } else {
            System.out.println("Payment not found");
        }

        System.out.println("\nPayments for order 1001:");

        for (Payment payment : repository.findByOrderId(1001)) {
            payment.displayPayment();
        }

        System.out.println("\nTotal payments: " +
                repository.getAllPayments().size());
    }
}
