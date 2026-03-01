package food_delivery_app.repository;

import food_delivery_app.model.user.Customer;

import java.util.*;

public class CustomerRepository {

    private static CustomerRepository instance;
//    customerid & customer
    private final Map<Integer, Customer> customerMap;
    //email & customer
    private final Map<String, Customer> emailMap;
    private final Set<String> phoneNumbers = new HashSet<>();

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
    public Set<String> getPhoneNumber()
    {
        return phoneNumbers;
    }
    public boolean isCustomerExist(String email)
    {
        return emailMap.containsKey(email);
    }
    public boolean isPhoneNumberExist(String phone)
    {
        return phoneNumbers.contains(phone);
    }
    // Add customer
    public void addCustomer(Customer customer) {

        customerMap.put(customer.getId(), customer);
        phoneNumbers.add(customer.getPhoneNumber());
        emailMap.put(customer.getEmail(), customer);
    }
    // Remove customer
    public void removeCustomer(int id) {

        Customer customer = customerMap.remove(id);

        if (customer != null) {
            emailMap.remove(
                    customer.getEmail().toLowerCase()
            );
            phoneNumbers.remove(customer.getPhoneNumber());
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
