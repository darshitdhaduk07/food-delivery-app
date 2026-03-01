package food_delivery_app.service;

import food_delivery_app.model.Address;
import food_delivery_app.model.user.Customer;
import food_delivery_app.model.notification.Notification;
import food_delivery_app.model.order.Order;
import food_delivery_app.repository.CustomerRepository;
import food_delivery_app.repository.OrderRepository;

import java.util.List;

public class CustomerService {

    private final CustomerRepository customerRepo;
    private final OrderRepository orderRepo;

    public CustomerService() {
        this.customerRepo = CustomerRepository.getInstance();
        this.orderRepo = OrderRepository.getInstance();
    }


    public void registerCustomer(Customer customer) {
        customerRepo.addCustomer(customer);
    }

    public void removeCustomer(int customerId) {
        customerRepo.removeCustomer(customerId);
    }


    public Customer findById(int id) {
        return customerRepo.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public void addAddress(Customer customer, Address address) {
        customer.getAddresses().add(address);
    }

    //show all the address
    public void showAddresses(Customer customer) {

    }
    public List<Order> getOrderHistory(Customer customer) {
        return orderRepo.findByCustomerId(customer.getId());
    }

    public void showNotifications(Customer customer) {

        List<Notification> notifications = customer.getCurrNotifications();

        if (notifications.isEmpty()) {
            System.out.println("No notifications.");
            return;
        }

        for (Notification n : notifications) {
            System.out.println(n);
        }
    }
}