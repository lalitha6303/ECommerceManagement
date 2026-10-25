import java.util.List;
import java.util.Scanner;

import customer.Customer;
import customer.CustomerRepository;
import customer.CustomerService;

import product.Product;
import product.ProductRepository;
import product.ProductService;

import cart.Cart;
import cart.CartItem;
import cart.CartService;

import order.Order;
import order.OrderRepository;
import order.OrderService;
import order.OrderStatus;

import payment.Payment;
import payment.PaymentMethod;
import payment.PaymentRepository;
import payment.PaymentService;

import subscription.Subscription;
import subscription.SubscriptionPlan;
import subscription.SubscriptionRepository;
import subscription.SubscriptionService;

import discount.Discount;
import discount.DiscountType;
import discount.DiscountRepository;
import discount.DiscountService;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final CustomerRepository customerRepository =
            new CustomerRepository();
    private static final CustomerService customerService =
            new CustomerService(customerRepository);

    private static final ProductRepository productRepository =
            new ProductRepository();
    private static final ProductService productService =
            new ProductService(productRepository);

    private static final CartService cartService =
            new CartService(productService);

    private static final OrderRepository orderRepository =
            new OrderRepository();
    private static final OrderService orderService =
            new OrderService(
                    cartService,
                    productService,
                    orderRepository
            );

    private static final PaymentRepository paymentRepository =
            new PaymentRepository();
    private static final PaymentService paymentService =
            new PaymentService(paymentRepository, orderService);

    private static final SubscriptionRepository subscriptionRepository =
            new SubscriptionRepository();
    private static final SubscriptionService subscriptionService =
            new SubscriptionService(subscriptionRepository);

    static {
        orderService.setFreeShippingChecker(subscriptionService::hasFreeShipping);
    }
    private static final DiscountRepository discountRepository =
            new DiscountRepository();

    private static final DiscountService discountService =
            new DiscountService(discountRepository);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== E-COMMERCE MANAGEMENT SYSTEM =====");
            System.out.println("1. Customer Management");
            System.out.println("2. Product Management");
            System.out.println("3. Shopping Cart");
            System.out.println("4. Order Management");
            System.out.println("5. Payment Management");
            System.out.println("6. Subscription Management");
            System.out.println("7. View All Customers");
            System.out.println("8. View All Products");
            System.out.println("9. View All Orders");
            System.out.println("10.Discount Management");
            System.out.println("0. Exit");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    customerMenu();
                    break;
                case 2:
                    productMenu();
                    break;
                case 3:
                    cartMenu();
                    break;
                case 4:
                    orderMenu();
                    break;
                case 5:
                    paymentMenu();
                    break;
                case 6:
                    subscriptionMenu();
                    break;
                case 7:
                    viewAllCustomers();
                    break;
                case 8:
                    viewAllProducts();
                    break;
                case 9:
                    viewAllOrders();
                    break;
                case 10:
                    discountMenu();
                    break;
                case 0:
                    System.out.println("Application closed.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void customerMenu() {

        while (true) {
            System.out.println("\n===== CUSTOMER MANAGEMENT =====");
            System.out.println("1. Add Customer");
            System.out.println("2. Search Customer by ID");
            System.out.println("3. Search Customer by Name");
            System.out.println("4. Search Customer by City");
            System.out.println("5. Search Customer by Email");
            System.out.println("6. Search Customer by Phone");
            System.out.println("7. Update Customer");
            System.out.println("8. Delete Customer");
            System.out.println("0. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    int id = readInt("Customer ID: ");
                    String name = readText("Name: ");
                    String email = readText("Email: ");
                    String address = readText("Address/City: ");
                    String phone = readText("Phone: ");

                    customerService.addCustomer(
                            new Customer(id, name, email, address, phone)
                    );
                    break;

                case 2:
                    Customer customer = customerService.getCustomerById(
                            readInt("Customer ID: "));
                    showCustomer(customer);
                    break;

                case 3:
                    List<Customer> byName =
                            customerService.getCustomersByName(
                                    readText("Customer name: "));
                    showCustomers(byName);
                    break;

                case 4:
                    List<Customer> byCity =
                            customerService.getCustomersByCity(
                                    readText("City: "));
                    showCustomers(byCity);
                    break;

                case 5:
                    showCustomer(customerService.getCustomerByEmail(
                            readText("Email: ")));
                    break;

                case 6:
                    showCustomer(customerService.getCustomerByPhone(
                            readText("Phone: ")));
                    break;

                case 7:
                    updateCustomer();
                    break;

                case 8:
                    customerService.deleteCustomer(
                            readInt("Customer ID to delete: "));
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void updateCustomer() {

        int id = readInt("Customer ID to update: ");
        Customer customer = customerService.getCustomerById(id);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("1. Update Name");
        System.out.println("2. Update Email");
        System.out.println("3. Update Address/City");
        System.out.println("4. Update Phone");

        int choice = readInt("Enter choice: ");

        switch (choice) {
            case 1:
                customer.setName(readText("New name: "));
                break;
            case 2:
                customer.setEmail(readText("New email: "));
                break;
            case 3:
                customer.setAddress(readText("New address/city: "));
                break;
            case 4:
                customer.setPhone(readText("New phone: "));
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        System.out.println("Customer updated.");
        customer.displayCustomer();
    }

    private static void productMenu() {

        while (true) {
            System.out.println("\n===== PRODUCT MANAGEMENT =====");
            System.out.println("1. Add Product");
            System.out.println("2. Search Product by ID");
            System.out.println("3. Search Product by Name");
            System.out.println("4. Search by Company");
            System.out.println("5. Search by Name and Exact Price");
            System.out.println("6. Search by Name and Price Range");
            System.out.println("7. Update Product Price");
            System.out.println("8. Update Product Quantity");
            System.out.println("9. Delete Product");
            System.out.println("0. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    int id = readInt("Product ID: ");
                    String name = readText("Product name: ");
                    double price = readDouble("Price: ");
                    String company = readText("Company: ");
                    int quantity = readInt("Stock quantity: ");

                    productService.addProduct(
                            new Product(id, name, price, company, quantity)
                    );
                    break;

                case 2:
                    showProduct(productService.getProductById(
                            readInt("Product ID: ")));
                    break;

                case 3:
                    String productName = readText("Product name: ");
                    for (Product p : productService.getAllProducts()) {
                        if (p.getProductName().equalsIgnoreCase(productName)) {
                            p.displayProduct();
                        }
                    }
                    break;

                case 4:
                    String companyName = readText("Company name: ");
                    for (Product p : productService.getAllProducts()) {
                        if (p.getCompanyName().equalsIgnoreCase(companyName)) {
                            p.displayProduct();
                        }
                    }
                    break;

                case 5:
                    String exactName = readText("Product name: ");
                    double exactPrice = readDouble("Exact price: ");
                    for (Product p : productService.getAllProducts()) {
                        if (p.getProductName().equalsIgnoreCase(exactName)
                                && p.getPrice() == exactPrice) {
                            p.displayProduct();
                        }
                    }
                    break;

                case 6:
                    String rangeName = readText("Product name: ");
                    double min = readDouble("Minimum price: ");
                    double max = readDouble("Maximum price: ");

                    for (Product p : productService.getAllProducts()) {
                        if (p.getProductName().equalsIgnoreCase(rangeName)
                                && p.getPrice() >= min
                                && p.getPrice() <= max) {
                            p.displayProduct();
                        }
                    }
                    break;

                case 7:
                    productService.updatePrice(
                            readInt("Product ID: "),
                            readDouble("New price: "));
                    break;

                case 8:
                    productService.updateQuantity(
                            readInt("Product ID: "),
                            readInt("New stock quantity: "));
                    break;

                case 9:
                    productService.deleteProduct(
                            readInt("Product ID to delete: "));
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void cartMenu() {

        while (true) {
            System.out.println("\n===== SHOPPING CART =====");
            System.out.println("1. Add Product to Cart");
            System.out.println("2. View Cart");
            System.out.println("3. Update Cart Quantity");
            System.out.println("4. Remove Product from Cart");
            System.out.println("5. View Cart Subtotal");
            System.out.println("0. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    cartService.addProduct(
                            readInt("Customer ID: "),
                            readInt("Product ID: "),
                            readInt("Quantity: "));
                    break;

                case 2:
                    viewCart(readInt("Customer ID: "));
                    break;

                case 3:
                    cartService.updateQuantity(
                            readInt("Customer ID: "),
                            readInt("Product ID: "),
                            readInt("New quantity: "));
                    break;

                case 4:
                    cartService.removeProduct(
                            readInt("Customer ID: "),
                            readInt("Product ID: "));
                    break;

                case 5:
                    System.out.println("Subtotal: Rs. "
                            + cartService.getSubtotal(
                            readInt("Customer ID: ")));
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void orderMenu() {

        while (true) {
            System.out.println("\n===== ORDER MANAGEMENT =====");
            System.out.println("1. Place Order");
            System.out.println("2. Search Order by ID");
            System.out.println("3. View Customer Order History");
            System.out.println("4. Update Order Status");
            System.out.println("5. Track Order");
            System.out.println("0. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    orderService.placeOrder(
                            readInt("Customer ID: "));

                    break;

                case 2:
                    Order order = orderService.getOrderById(
                            readInt("Order ID: "));
                    if (order != null) {
                        order.displayOrder();
                    } else {
                        System.out.println("Order not found.");
                    }
                    break;

                case 3:
                    List<Order> orders =
                            orderService.getOrdersByCustomer(
                                    readInt("Customer ID: "));
                    if (orders.isEmpty()) {
                        System.out.println("No orders found.");
                    } else {
                        for (Order o : orders) {
                            o.displayOrder();
                        }
                    }
                    break;

                case 4:
                    int orderId = readInt("Order ID: ");
                    System.out.println("1. DISPATCHED");
                    System.out.println("2. WITH_COURIER");
                    System.out.println("3. DELIVERED");
                    System.out.println("4. RETURNED");
                    System.out.println("5. RETURN_RECEIVED");

                    int statusChoice = readInt("Select new status: ");
                    OrderStatus status = null;

                    switch (statusChoice) {
                        case 1:
                            status = OrderStatus.DISPATCHED;
                            break;
                        case 2:
                            status = OrderStatus.WITH_COURIER;
                            break;
                        case 3:
                            status = OrderStatus.DELIVERED;
                            break;
                        case 4:
                            status = OrderStatus.RETURNED;
                            break;
                        case 5:
                            status = OrderStatus.RETURN_RECEIVED;
                            break;
                        default:
                            System.out.println("Invalid status choice.");
                    }

                    if (status != null) {
                        orderService.updateOrderStatus(orderId, status);
                    }
                    break;

                case 5:
                    System.out.println(
                            orderService.getTrackingDetails(
                                    readInt("Order ID: ")));
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    private static void paymentMenu() {

        while (true) {
            System.out.println("\n===== PAYMENT MANAGEMENT =====");
            System.out.println("1. Make Payment for Order");
            System.out.println("2. Search Payment by ID");
            System.out.println("3. View Payments for an Order");
            System.out.println("4. View All Payments");
            System.out.println("5. Mark Pending Payment as Successful");
            System.out.println("6. Refund Successful Payment");
            System.out.println("0. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    int orderId = readInt("Order ID: ");
                    System.out.println("1. Debit Card");
                    System.out.println("2. Credit Card");
                    System.out.println("3. Cash on Delivery");
                    System.out.println("4. UPI");
                    System.out.println("5. Gift Card");

                    int methodChoice = readInt("Select payment method: ");
                    PaymentMethod method = null;

                    switch (methodChoice) {
                        case 1:
                            method = PaymentMethod.DEBIT_CARD;
                            break;
                        case 2:
                            method = PaymentMethod.CREDIT_CARD;
                            break;
                        case 3:
                            method = PaymentMethod.CASH_ON_DELIVERY;
                            break;
                        case 4:
                            method = PaymentMethod.UPI;
                            break;
                        case 5:
                            method = PaymentMethod.GIFT_CARD;
                            break;
                        default:
                            System.out.println("Invalid payment method.");
                    }

                    if (method != null) {
                        paymentService.processPayment(orderId, method);
                    }
                    break;

                case 2:
                    Payment payment = paymentService.getPaymentById(
                            readInt("Payment ID: "));
                    if (payment == null) {
                        System.out.println("Payment not found.");
                    } else {
                        payment.displayPayment();
                    }
                    break;

                case 3:
                    int searchOrderId = readInt("Order ID: ");
                    List<Payment> orderPayments =
                            paymentService.getPaymentsByOrderId(searchOrderId);
                    if (orderPayments.isEmpty()) {
                        System.out.println("No payments found for this order.");
                    } else {
                        for (Payment p : orderPayments) {
                            p.displayPayment();
                            System.out.println();
                        }
                    }
                    break;

                case 4:
                    List<Payment> payments = paymentService.getAllPayments();
                    if (payments.isEmpty()) {
                        System.out.println("No payments found.");
                    } else {
                        for (Payment p : payments) {
                            p.displayPayment();
                            System.out.println();
                        }
                    }
                    break;

                case 5:
                    paymentService.markPaymentSuccessful(
                            readInt("Payment ID: "));
                    break;

                case 6:
                    paymentService.refundPayment(
                            readInt("Payment ID: "));
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void subscriptionMenu() {

        while (true) {
            System.out.println("\n===== SUBSCRIPTION MANAGEMENT =====");
            System.out.println("1. Create Subscription");
            System.out.println("2. Search Subscription by ID");
            System.out.println("3. View Customer Subscriptions");
            System.out.println("4. Cancel Subscription");
            System.out.println("5. Check Free-Shipping Eligibility");
            System.out.println("6. View All Subscriptions");
            System.out.println("0. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    int customerId = readInt("Customer ID: ");
                    System.out.println("1. Monthly");
                    System.out.println("2. Quarterly");
                    System.out.println("3. Half-Yearly");
                    System.out.println("4. Yearly");

                    int planChoice = readInt("Select plan: ");
                    SubscriptionPlan plan = null;

                    switch (planChoice) {
                        case 1:
                            plan = SubscriptionPlan.MONTHLY;
                            break;
                        case 2:
                            plan = SubscriptionPlan.QUARTERLY;
                            break;
                        case 3:
                            plan = SubscriptionPlan.HALF_YEARLY;
                            break;
                        case 4:
                            plan = SubscriptionPlan.YEARLY;
                            break;
                        default:
                            System.out.println("Invalid subscription plan.");
                    }

                    if (plan != null) {
                        subscriptionService.createSubscription(customerId, plan);
                    }
                    break;

                case 2:
                    Subscription subscription =
                            subscriptionService.getSubscriptionById(
                                    readInt("Subscription ID: "));
                    if (subscription == null) {
                        System.out.println("Subscription not found.");
                    } else {
                        subscription.displaySubscription();
                    }
                    break;

                case 3:
                    int searchCustomerId = readInt("Customer ID: ");
                    List<Subscription> customerSubscriptions =
                            subscriptionService.getSubscriptionsByCustomer(
                                    searchCustomerId);
                    if (customerSubscriptions.isEmpty()) {
                        System.out.println("No subscriptions found.");
                    } else {
                        for (Subscription s : customerSubscriptions) {
                            s.displaySubscription();
                            System.out.println();
                        }
                    }
                    break;

                case 4:
                    subscriptionService.cancelSubscription(
                            readInt("Subscription ID: "));
                    break;

                case 5:
                    int shippingCustomerId =
                            readInt("Customer ID: ");
                    System.out.println("Free shipping eligible: "
                            + subscriptionService.hasFreeShipping(
                            shippingCustomerId));
                    break;

                case 6:
                    List<Subscription> subscriptions =
                            subscriptionService.getAllSubscriptions();
                    if (subscriptions.isEmpty()) {
                        System.out.println("No subscriptions found.");
                    } else {
                        for (Subscription s : subscriptions) {
                            s.displaySubscription();
                            System.out.println();
                        }
                    }
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void discountMenu() {
        while (true) {
            System.out.println("\n===== DISCOUNT MANAGEMENT =====");
            System.out.println("1. Create Discount");
            System.out.println("2. View Discount by ID");
            System.out.println("3. View All Discounts");
            System.out.println("4. Delete Discount");
            System.out.println("5. Deactivate Discount");
            System.out.println("6. Activate Discount");
            System.out.println("7. Calculate Discount");
            System.out.println("0. Back to Main Menu");

            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1:
                        String name = readText("Discount name: ");

                        System.out.println("1. Percentage");
                        System.out.println("2. Fixed Amount");

                        int typeChoice = readInt("Choose discount type: ");

                        DiscountType type;

                        if (typeChoice == 1) {
                            type = DiscountType.PERCENTAGE;
                        } else if (typeChoice == 2) {
                            type = DiscountType.FIXED_AMOUNT;
                        } else {
                            System.out.println("Invalid discount type.");
                            break;
                        }

                        double value = readDouble("Discount value: ");
                        double minimum = readDouble("Minimum order amount: ");

                        Discount discount = discountService.createDiscount(
                                name, type, value, minimum
                        );

                        System.out.println("Discount created successfully.");
                        discount.displayDiscount();
                        break;

                    case 2:
                        int searchId = readInt("Discount ID: ");
                        Discount found = discountService.getDiscountById(searchId);

                        if (found != null) {
                            found.displayDiscount();
                        } else {
                            System.out.println("Discount not found.");
                        }
                        break;

                    case 3:
                        if (discountService.getAllDiscounts().isEmpty()) {
                            System.out.println("No discounts available.");
                        } else {
                            for (Discount d : discountService.getAllDiscounts()) {
                                d.displayDiscount();
                                System.out.println("--------------------");
                            }
                        }
                        break;

                    case 4:
                        int deleteId = readInt("Discount ID to delete: ");

                        if (discountService.deleteDiscount(deleteId)) {
                            System.out.println("Discount deleted successfully.");
                        } else {
                            System.out.println("Discount not found.");
                        }
                        break;

                    case 5:
                        discountService.deactivateDiscount(
                                readInt("Discount ID: ")
                        );
                        System.out.println("Discount deactivated.");
                        break;

                    case 6:
                        discountService.activateDiscount(
                                readInt("Discount ID: ")
                        );
                        System.out.println("Discount activated.");
                        break;

                    case 7:
                        int calculateId = readInt("Discount ID: ");
                        double amount = readDouble("Order amount: ");

                        double discountAmount =
                                discountService.calculateDiscount(
                                        calculateId, amount
                                );

                        System.out.println(
                                "Discount amount: ₹" + discountAmount
                        );
                        System.out.println(
                                "Amount after discount: ₹" +
                                        (amount - discountAmount)
                        );
                        break;

                    case 0:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }


    private static void viewCart(int customerId) {
        Cart cart = cartService.getCart(customerId);

        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        for (CartItem item : cart.getItems()) {
            System.out.println(
                    item.getProduct().getProductName()
                            + " | Quantity: " + item.getQuantity()
                            + " | Subtotal: Rs. " + item.getSubtotal()
            );
        }

        System.out.println("Cart subtotal: Rs. " + cart.getSubtotal());
    }

    private static void viewAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        showCustomers(customers);
    }

    private static void viewAllProducts() {
        List<Product> products = productService.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        for (Product p : products) {
            p.displayProduct();
        }
    }

    private static void viewAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        for (Order order : orders) {
            order.displayOrder();
        }
    }

    private static void showCustomer(Customer customer) {
        if (customer == null) {
            System.out.println("Customer not found.");
        } else {
            customer.displayCustomer();
        }
    }

    private static void showCustomers(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Customer customer : customers) {
            customer.displayCustomer();
        }
    }

    private static void showProduct(Product product) {
        if (product == null) {
            System.out.println("Product not found.");
        } else {
            product.displayProduct();
        }
    }

    private static String readText(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }

    private static int readInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(readText(message));
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            try {
                return Double.parseDouble(readText(message));
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }
}
