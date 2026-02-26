package food_delivery_app.model;


import java.util.ArrayList;
import java.util.List;

public class Order {

    private static int counter = 0;

    private final int id;
    private Customer customer;
    private List<OrderItem> items;
    private double totalAmount;
    private IOrderStatus status;
    private Address deliveryAddress;
    private DeliveryAgent deliveryAgent;

    public Order(Customer customer, List<OrderItem> items, Address deliveryAddress) {
        this.id = ++counter;
        this.customer = customer;
        this.items = new ArrayList<>(items);
        this.deliveryAddress = deliveryAddress;
        this.status = IOrderStatus.CREATED;

        calculateTotal();
    }

    private void calculateTotal() {
        this.totalAmount = items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public IOrderStatus getStatus() {
        return status;
    }

    public void moveToNextState() {
        this.status = this.status.next();
    }

    public void setDeliveryAgent(DeliveryAgent agent) {
        deliveryAgent = agent;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public DeliveryAgent getDeliveryBoy() {
        return deliveryAgent;
    }

    public void assignDeliveryBoy(DeliveryAgent deliveryAgent) {
        this.deliveryAgent = deliveryAgent;
    }

    @Override
    public String toString() {

        String deliveryName =
                (deliveryAgent == null)
                        ? "NOT_ASSIGNED"
                        : deliveryAgent.getName();

        return String.format(
                "%-5d %-15s %-15s %-18s ₹%-10.2f",
                id,
                customer.getName(),
                deliveryName,
                status,
                totalAmount
        );
    }
}