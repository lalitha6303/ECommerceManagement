
package payment;

import customer.Customer;
import customer.CustomerRepository;
import customer.CustomerService;
import product.Product;
import product.ProductRepository;
import product.ProductService;
import cart.CartService;
import order.OrderRepository;
import order.OrderService;

public class PaymentServiceTest {

    public static void main(String[] args) {

        CustomerRepository customerRepository =
                new CustomerRepository();

        CustomerService customerService =
                new CustomerService(customerRepository);

        customerService.addCustomer(
                new Customer(1, "Lalitha", "lalitha@example.com",
                        "Chebrolu", "9876543210")
        );

        ProductRepository productRepository =
                new ProductRepository();

        ProductService productService =
                new ProductService(productRepository);

        productService.addProduct(
                new Product(101, "Laptop", 50000,
                        "Dell", 5)
        );

        CartService cartService =
                new CartService(productService);

        cartService.addProduct(1, 101, 1);

        OrderRepository orderRepository =
                new OrderRepository();

        OrderService orderService =
                new OrderService(cartService, productService,
                        orderRepository);

        orderService.placeOrder(1);

        PaymentRepository paymentRepository =
                new PaymentRepository();

        PaymentService paymentService =
                new PaymentService(paymentRepository, orderService);

        System.out.println("\n===== UPI PAYMENT =====");

        Payment payment = paymentService.processPayment(
                1001, PaymentMethod.UPI
        );

        System.out.println("\n===== SEARCH PAYMENT =====");

        Payment found = paymentService.getPaymentById(1);

        if (found != null) {
            found.displayPayment();
        }

        System.out.println("\n===== ALL PAYMENTS =====");

        for (Payment p : paymentService.getAllPayments()) {
            p.displayPayment();
        }

        System.out.println("\n===== COD PAYMENT =====");

        Payment codPayment = paymentService.processPayment(
                1001, PaymentMethod.CASH_ON_DELIVERY
        );

        if (codPayment != null) {
            paymentService.markPaymentSuccessful(
                    codPayment.getPaymentId()
            );
            codPayment.displayPayment();
        }

        System.out.println("\n===== REFUND TEST =====");

        paymentService.refundPayment(1);

        Payment refunded = paymentService.getPaymentById(1);

        if (refunded != null) {
            refunded.displayPayment();
        }

    }
}
