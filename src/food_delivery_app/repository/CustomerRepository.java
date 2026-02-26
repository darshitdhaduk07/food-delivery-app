package food_delivery_app.repository;

import food_delivery_app.model.Customer;

import java.util.*;

public class CustomerRepository {

    private static CustomerRepository instance;
    private final Map<Integer, Customer> customerMap;
    private final Map<String, Customer> emailMap;

    private CustomerRepository() {
        customerMap = new HashMap<>();
        emailMap = new HashMap<>();
    }

    public static CustomerRepository getInstance() {
        if (instance == null) {
            instance = new CustomerRepository();
        }
        return instance;
    }

    // Add customer
    public void addCustomer(Customer customer) {

        String email =
                customer.getEmail().toLowerCase();

        if (emailMap.containsKey(email)) {
            System.out.println("Email already exists.");
            return;
        }

        customerMap.put(customer.getId(), customer);
        emailMap.put(email, customer);
    }
    // Remove customer
    public void removeCustomer(int id) {

        Customer customer = customerMap.remove(id);

        if (customer != null) {
            emailMap.remove(
                    customer.getEmail().toLowerCase()
            );
        }
    }
    // Find by id
    public Customer findById(int id) {
        return customerMap.get(id);
    }

    public Customer findByEmail(String email) {
        return emailMap.get(email.toLowerCase());
    }

    public List<Customer> findAll() {
        return new ArrayList<>(customerMap.values());
    }
}
