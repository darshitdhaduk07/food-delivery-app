package food_delivery_app.model;


import java.util.ArrayList;
import java.util.List;

public class Order {

    private static int counter = 0;

    private final int id;
    private Customer customer;
    private List<OrderItem> items;
    private double totalAmount;
    private OrderStatus status;
    private Address deliveryAddress;
    private DeliveryBoy deliveryBoy;

    public Order(Customer customer, List<OrderItem> items, Address deliveryAddress) {
        this.id = ++counter;
        this.customer = customer;
        this.items = new ArrayList<>(items);
        this.deliveryAddress = deliveryAddress;
        this.status = OrderStatus.CREATED;

        calculateTotal();
    }

    private void calculateTotal() {
        this.totalAmount = items.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
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
        return "Order{" +
                "id='" + id + '\'' +
                ", customer=" + customer.getName() +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                '}';
    }
}