
package payment;

public class PaymentTest {

    public static void main(String[] args) {

        Payment payment = new Payment(
                1,
                1001,
                100100.0,
                PaymentMethod.UPI
        );

        payment.displayPayment();

        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        System.out.println("\nAfter payment:");

        payment.displayPayment();

    }
}
