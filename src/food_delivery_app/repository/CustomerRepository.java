package food_delivery_app.repository;

import food_delivery_app.model.Customer;

import java.util.*;

public class CustomerRepository {

    private static CustomerRepository instance;
    private final Map<Integer, Customer> customerMap;

    private CustomerRepository() {
        customerMap = new HashMap<>();
    }

    public static CustomerRepository getInstance() {
        if (instance == null) {
            instance = new CustomerRepository();
        }
        return instance;
    }

    // Add customer
    public void addCustomer(Customer customer) {
        customerMap.put(customer.getId(), customer);
    }

    // Remove customer
    public void removeCustomer(int id) {
        customerMap.remove(id);
    }

    // Find by id
    public Customer findById(int id) {
        return customerMap.get(id);
    }

    public Customer findByEmail(String email) {
        return customerMap.values().stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public List<Customer> findAll() {
        return new ArrayList<>(customerMap.values());
    }
}
