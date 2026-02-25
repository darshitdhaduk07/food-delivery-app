package food_delivery_app.services;

import food_delivery_app.discount.DiscountStrategy;
import food_delivery_app.menu.MenuCategory;
import food_delivery_app.menu.MenuComponent;
import food_delivery_app.menu.MenuItem;
import food_delivery_app.menu.MenuStore;
import food_delivery_app.model.DeliveryBoy;
import food_delivery_app.model.Order;
import food_delivery_app.model.OrderStatus;
import food_delivery_app.repository.CustomerRepository;
import food_delivery_app.repository.DeliveryRepository;
import food_delivery_app.repository.OrderRepository;

import java.util.List;

public class ManagerService {

    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private final DeliveryRepository deliveryRepo;
    private DiscountStrategy discountStrategy;

    public ManagerService() {
        this.orderRepo = OrderRepository.getInstance();
        this.customerRepo = CustomerRepository.getInstance();
        this.deliveryRepo = DeliveryRepository.getInstance();
    }


    //for menu
    public void showMenu() {
        MenuStore.getMenu().display(0);
    }

    public void addCategory(String name) {

        MenuStore.getMenu()
                .add(new MenuCategory(name));

        System.out.println("Category added.");
    }
    public void addItemToCategory(int categoryId,
                                  String itemName,
                                  double price) {

        MenuComponent comp =
                MenuStore.getMenu().findById(categoryId);

        if (comp instanceof MenuCategory category) {

            category.add(new MenuItem(itemName, price));
            System.out.println("Item added.");

        } else {
            System.out.println("Invalid category ID.");
        }
    }
    public void updatePrice(int itemId, double newPrice) {

        MenuComponent comp =
                MenuStore.getMenu().findById(itemId);

        if (comp instanceof MenuItem item) {

            item.setPrice(newPrice);
            System.out.println("Price updated.");

        } else {
            System.out.println("Invalid item ID.");
        }
    }
    public void changeAvailability(int itemId,
                                   boolean status) {

        MenuComponent comp =
                MenuStore.getMenu().findById(itemId);

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

            if (order.getStatus() == OrderStatus.DELIVERED) {
                total += order.getTotalAmount();
            }
        }

        return total;
    }

    //customer view
    public void showAllCustomers() {
        customerRepo.findAll()
                .forEach(System.out::println);
    }
    public void removeCustomer(int customerId) {
        customerRepo.removeCustomer(customerId);
        System.out.println("Customer removed.");
    }


    //for delivery boy
    public void addDeliveryBoy(DeliveryBoy boy) {
        deliveryRepo.addDeliveryBoy(boy);
        System.out.println("Delivery boy added.");
    }

    private void reassignOrders(DeliveryBoy removedBoy) {

        List<Order> orders = removedBoy.getAssignedOrders();

        if (orders.isEmpty()) return;

        List<DeliveryBoy> others =
                deliveryRepo.findAll()
                        .stream()
                        .filter(b -> b.getId() != removedBoy.getId())
                        .toList();

        if (others.isEmpty()) {
            System.out.println("No other delivery boys available.");
            return;
        }

        int index = 0;

        for (Order order : orders) {

            DeliveryBoy newBoy =
                    others.get(index % others.size());

            // assign to new boy
            newBoy.assignOrder(order);

            // update order reference
            order.assignDeliveryBoy(newBoy);

            index++;
        }

        orders.clear(); // remove from old boy
    }

    public void removeDeliveryBoy(int id) {

        DeliveryBoy boy = deliveryRepo.findById(id);

        if (boy == null) {
            System.out.println("Delivery boy not found.");
            return;
        }
        //prevent remvoe last delivery boy
        if (deliveryRepo.findAll().size() <= 1) {
            System.out.println("Cannot remove the last delivery boy.");
            return;
        }

        // reassign active orders
        reassignOrders(boy);

        // remove from repository
        deliveryRepo.removeDeliveryBoy(id);

        System.out.println("Delivery boy removed and orders reassigned.");
    }

    public void showAllDeliveryBoys() {

        List<DeliveryBoy> boys = deliveryRepo.findAll();

        if (boys.isEmpty()) {
            System.out.println("No delivery boys.");
            return;
        }

        boys.forEach(System.out::println);
    }
}