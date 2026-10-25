
package customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    private final List<Customer> customers = new ArrayList<>();

    public boolean addCustomer(Customer customer) {
        if (customer == null || findById(customer.getCustomerId()) != null) {
            return false;
        }

        customers.add(customer);
        return true;
    }

    public boolean deleteCustomer(int customerId) {
        Customer customer = findById(customerId);

        if (customer == null) {
            return false;
        }

        customers.remove(customer);
        return true;
    }

    public Customer findById(int customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    public List<Customer> findByName(String name) {
        List<Customer> result = new ArrayList<>();

        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                result.add(customer);
            }
        }

        return result;
    }

    public List<Customer> findByCity(String city) {
        List<Customer> result = new ArrayList<>();

        for (Customer customer : customers) {
            if (customer.getAddress().equalsIgnoreCase(city)) {
                result.add(customer);
            }
        }

        return result;
    }

    public Customer findByEmail(String email) {
        for (Customer customer : customers) {
            if (customer.getEmail().equalsIgnoreCase(email)) {
                return customer;
            }
        }
        return null;
    }

    public Customer findByPhone(String phone) {
        for (Customer customer : customers) {
            if (customer.getPhone().equals(phone)) {
                return customer;
            }
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }
}
