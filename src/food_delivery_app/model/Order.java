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
    private DeliveryBoy deliveryBoy;

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

    public void setStatus(IOrderStatus status) {
        this.status = status;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public DeliveryBoy getDeliveryBoy() {
        return deliveryBoy;
    }

    public void assignDeliveryBoy(DeliveryBoy deliveryBoy) {
        this.deliveryBoy = deliveryBoy;
    }

    @Override
    public String toString() {

        return String.format("| %-3d | %-14s | %-20s | ₹%-9.2f |",
                id, customer.getName(), status, totalAmount
        );
    }
}