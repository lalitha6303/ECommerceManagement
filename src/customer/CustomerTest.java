
package customer;

public class CustomerTest {

    public static void main(String[] args) {

        CustomerRepository repository = new CustomerRepository();
        CustomerService service = new CustomerService(repository);

        Customer c1 = new Customer(
                101, "Lalitha", "lalitha@gmail.com",
                "Rajahmundry", "9876543210");

        Customer c2 = new Customer(
                102, "Ravi", "ravi@gmail.com",
                "Vijayawada", "9123456780");

        Customer c3 = new Customer(
                103, "Lakshmi", "lakshmi@gmail.com",
                "Rajahmundry", "9988776655");

        System.out.println("ADD CUSTOMERS");
        System.out.println(service.addCustomer(c1));
        System.out.println(service.addCustomer(c2));
        System.out.println(service.addCustomer(c3));

        System.out.println("\nSEARCH BY NAME");
        for (Customer c : service.getCustomersByName("Lalitha")) {
            System.out.println(c.getName());
        }

        System.out.println("\nSEARCH BY CITY");
        for (Customer c : service.getCustomersByCity("Rajahmundry")) {
            System.out.println(c.getName());
        }

        System.out.println("\nSEARCH BY PHONE");
        Customer found = service.getCustomerByPhone("9123456780");
        System.out.println(found != null ? found.getName() : "Not found");

        System.out.println("\nTRY DUPLICATE EMAIL");
        Customer duplicate = new Customer(
                104, "Test", "lalitha@gmail.com",
                "Hyderabad", "9000011111");

        System.out.println(service.addCustomer(duplicate));

        System.out.println("\nDELETE CUSTOMER");
        System.out.println(service.deleteCustomer(102));

        System.out.println("\nREMAINING CUSTOMERS");
        for (Customer c : service.getAllCustomers()) {
            System.out.println(c.getCustomerId() + " - " + c.getName());
        }
    }
}
