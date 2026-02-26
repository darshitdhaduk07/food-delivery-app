package food_delivery_app.services;

import food_delivery_app.discount.DiscountStrategy;
import food_delivery_app.menu.MenuCategory;
import food_delivery_app.menu.MenuComponent;
import food_delivery_app.menu.MenuItem;
import food_delivery_app.menu.MenuStore;
import food_delivery_app.model.DeliveryAgent;
import food_delivery_app.model.Order;
import food_delivery_app.model.IOrderStatus;
import food_delivery_app.repository.CustomerRepository;
import food_delivery_app.repository.DeliveryRepository;
import food_delivery_app.repository.OrderRepository;

import java.util.List;


public class ManagerService {

    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private final DeliveryRepository deliveryRepo;
    private DiscountStrategy discountStrategy;
    private final DeliveryService deliveryService = new DeliveryService();

    public ManagerService() {
        this.orderRepo = OrderRepository.getInstance();
        this.customerRepo = CustomerRepository.getInstance();
        this.deliveryRepo = DeliveryRepository.getInstance();
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

    public void setDiscountStrategy(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
        System.out.println("Discount strategy updated.");
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
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

    //revenue
    public double calculateRevenue() {

        double total = 0;

        for (Order order : orderRepo.findAll()) {

            if (order.getStatus() == IOrderStatus.DELIVERED) {
                total += order.getTotalAmount();
            }
        }

        return total;
    }

    //customer view
    public void showAllCustomers() {
        System.out.printf("%-5s %-15s %-25s %-20s%n",
                "ID", "NAME", "EMAIL", "ADDRESSES");
        customerRepo.findAll().forEach(System.out::println);
    }

    public void removeCustomer(int customerId) {
        customerRepo.removeCustomer(customerId);
        System.out.println("Customer removed.");
    }


    //for delivery boy
    public void addDeliveryBoy(DeliveryAgent boy) {
        deliveryRepo.addDeliveryBoy(boy);
        System.out.println("Delivery boy added.");
    }

    public void removeDeliveryBoy(int id) {

        DeliveryAgent boy = deliveryRepo.findById(id);

        if (boy == null) {
            System.out.println("Delivery boy not found.");
            return;
        }

        if (deliveryRepo.findAll().size() <= 1) {
            System.out.println("Cannot remove the last delivery boy.");
            return;
        }

        // if busy -> complete current order first
        if (!boy.isAvailable()) {

            Order order = orderRepo.findAll().stream().filter(o -> o.getDeliveryBoy() != null && o.getDeliveryBoy().getId() == boy.getId() && o.getStatus() == IOrderStatus.ON_THE_WAY).findFirst().orElse(null);

            if (order != null) {
                deliveryService.completeDelivery(order);
                System.out.println("Current order completed before removal.");
            }
        }

        deliveryRepo.removeDeliveryBoy(id);

        System.out.println("Delivery boy removed successfully.");
    }

    public void showAllDeliveryBoys() {

        List<DeliveryAgent> boys = deliveryRepo.findAll();

        if (boys.isEmpty()) {
            System.out.println("No delivery boys.");
            return;
        }
        System.out.printf("%-5s %-15s %-12s%n",
                "ID", "NAME", "STATUS");

        System.out.println("-----------------------------------");

        boys.forEach(System.out::println);
    }
}