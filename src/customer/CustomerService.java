
package customer;

import java.util.List;

public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public boolean addCustomer(Customer customer) {
        if (customer == null) {
            return false;
        }

        if (customer.getName() == null || customer.getName().isBlank()
                || customer.getEmail() == null || customer.getEmail().isBlank()
                || customer.getAddress() == null || customer.getAddress().isBlank()
                || customer.getPhone() == null || customer.getPhone().isBlank()) {
            return false;
        }

        if (!customer.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            return false;
        }

        if (repository.findById(customer.getCustomerId()) != null
                || repository.findByEmail(customer.getEmail()) != null
                || repository.findByPhone(customer.getPhone()) != null) {
            return false;
        }

        return repository.addCustomer(customer);
    }

    public boolean deleteCustomer(int customerId) {
        return repository.deleteCustomer(customerId);
    }

    public Customer getCustomerById(int customerId) {
        return repository.findById(customerId);
    }

    public Customer getCustomerByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Customer getCustomerByPhone(String phone) {
        return repository.findByPhone(phone);
    }

    public List<Customer> getCustomersByName(String name) {
        return repository.findByName(name);
    }

    public List<Customer> getCustomersByCity(String city) {
        return repository.findByCity(city);
    }

    public List<Customer> getAllCustomers() {
        return repository.getAllCustomers();
    }
}
