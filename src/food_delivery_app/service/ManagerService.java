package food_delivery_app.service;

import food_delivery_app.model.discount.Discount;
import food_delivery_app.model.menu.MenuCategory;
import food_delivery_app.model.menu.MenuComponent;
import food_delivery_app.model.menu.MenuItem;
import food_delivery_app.model.menu.MenuStore;
import food_delivery_app.model.user.Customer;
import food_delivery_app.model.user.DeliveryAgent;
import food_delivery_app.model.order.Order;
import food_delivery_app.model.order.IOrderStatus;
import food_delivery_app.repository.CustomerRepository;
import food_delivery_app.repository.DeliveryRepository;
import food_delivery_app.repository.OrderRepository;
import food_delivery_app.utility.InputValidator;

import java.util.List;


public class ManagerService {

    private final OrderRepository orderRepo;
    private static Discount discount = Discount.getInstance();
    private final CustomerRepository customerRepo;
    private final DeliveryRepository deliveryRepo;
    private final DeliveryAgentService deliveryAgentService = new DeliveryAgentService();
    private final NotificationService notificationService;

    public ManagerService() {
        this.orderRepo = OrderRepository.getInstance();
        this.customerRepo = CustomerRepository.getInstance();
        this.deliveryRepo = DeliveryRepository.getInstance();
        this.notificationService = new NotificationService();
    }


    //for menu
    public void showMenu() {
        System.out.println("==============================================");
        System.out.printf("%-5s %-12s %-18s %-10s%n", "ID", "TYPE", "NAME", "PRICE");
        System.out.println("==============================================");

        MenuStore.getMenu().displayTable();
    }

    public void addCategory(String name) {

        MenuStore.getMenu().add(new MenuCategory(name));
    }

    public void addItemToCategory(int categoryId, String itemName, double price) {

        MenuComponent comp = MenuStore.getMenu().findMenuComponentById(categoryId);

        if (comp instanceof MenuCategory category) {

            category.add(new MenuItem(itemName, price));

        } else {
            System.out.println("Invalid category ID.");
        }
    }

    public void updatePrice(int itemId, double newPrice) {

        MenuComponent comp = MenuStore.getMenu().findMenuComponentById(itemId);

        if (comp instanceof MenuItem item) {

            item.setPrice(newPrice);
            System.out.println("Price updated.");

        } else {
            System.out.println("Invalid item ID.");
        }
    }

    public void changeAvailability(int itemId, boolean status) {

        MenuComponent comp = MenuStore.getMenu().findMenuComponentById(itemId);

        if (comp instanceof MenuItem item) {

            item.setAvailability(status);
            System.out.println("Availability updated.");

        } else {
            System.out.println("Invalid item ID.");
        }
    }
    //for discount

    public void updateDiscount() {
        String name = InputValidator.readString("Enter Discount Name: ");
        double rate = InputValidator.readPositiveDouble("Enter Discount Rate in (%): ");
        if(rate > 100)
            rate = 100;
        double minAmount = InputValidator.readPositiveDouble("Enter Minimum amount for apply Discount: ");

        discount.setDiscountRate(rate);
        discount.setName(name);
        discount.setMinAmount(minAmount);
        notifyAllCustomers("New Discount Available");
        System.out.println("Discount Updated");
    }

    public Discount getDiscount() {
        return discount;
    }
    public void showDiscount()
    {
        System.out.println("Discount Name: "+discount.getName());
        System.out.println("Discount Rate in(%) : "+discount.getDiscountRate());
        System.out.println("Minimum amount to apply discount: "+discount.getMinAmount());
    }

    //for order

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public void showAllOrders() {

        List<Order> orders = orderRepo.findAll();

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        for (Order o : orders) {
            System.out.println(o);
        }
    }

    //customer view
    public void showAllCustomers() {
        System.out.printf("%-5s %-15s %-25s %-20s%n",
                "ID", "NAME", "EMAIL", "ADDRESSES");
        System.out.println("─────────────────────────────────────────────────────────────");

        customerRepo.findAll().forEach(System.out::println);
    }

    public void removeCustomer(int customerId) {
        customerRepo.removeCustomer(customerId);
        System.out.println("Customer removed.");
    }
    public void notifyCustomer(int customerId,
                               String message) {

        Customer customer =
                customerRepo.findById(customerId);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        notificationService.sendNotification(
                customer,
                message
        );

        System.out.println("Notification sent.");
    }
    public void notifyAllCustomers(String message) {

        List<Customer> customers =
                customerRepo.findAll();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Customer c : customers) {
            notificationService.sendNotification(c, message);
        }

        System.out.println("Notification sent to all customers.");
    }

    //for delivery boy
    public void addDeliveryAgent(DeliveryAgent agent) {

        deliveryRepo.addDeliveryAgent(agent);
    }

    public void removeDeliveryAgent(int id) {

        DeliveryAgent agent = deliveryRepo.findById(id);

        if (agent == null) {
            System.out.println("Delivery Agent not found.");
            return;
        }

        if (deliveryRepo.findAll().size() <= 1) {
            System.out.println("Cannot remove the last delivery agent.");
            return;
        }

        // if busy -> complete current order first
        if (!agent.isAvailable()) {

            Order order = orderRepo.findAll().stream().filter(o -> o.getDeliveryAgent() != null && o.getDeliveryAgent().getId() == agent.getId() && o.getStatus() == IOrderStatus.ON_THE_WAY).findFirst().orElse(null);

            if (order != null) {
                deliveryAgentService.completeDelivery(order);
                System.out.println("Current order completed before removal.");
            }
        }

        deliveryRepo.removeDeliveryAgent(id);

        System.out.println("Delivery agent removed successfully.");
    }

    public void showAllDeliveryAgents() {

        List<DeliveryAgent> agents = deliveryRepo.findAll();

        if (agents.isEmpty()) {
            System.out.println("No delivery agents.");
            return;
        }
        System.out.printf("%-5s %-15s %-12s%n",
                "ID", "NAME", "STATUS");

        System.out.println("-----------------------------------");

        agents.forEach(System.out::println);
    }
    public void notifyDeliveryAgent(int id,
                                    String message) {

        DeliveryAgent agent =
                deliveryRepo.findById(id);

        if (agent == null) {
            System.out.println("Delivery agent not found.");
            return;
        }

        notificationService.sendNotification(
                agent,
                message
        );

        System.out.println("Notification sent.");
    }
    public void notifyAllDeliveryAgents(String message) {

        List<DeliveryAgent> agents =
                deliveryRepo.findAll();

        if (agents.isEmpty()) {
            System.out.println("No delivery agents.");
            return;
        }

        for (DeliveryAgent a : agents) {
            notificationService.sendNotification(a, message);
        }

        System.out.println("Notification sent to all delivery agents.");
    }
}