package food_delivery_app.model.order;


import food_delivery_app.model.*;
import food_delivery_app.model.user.Customer;
import food_delivery_app.model.user.DeliveryAgent;
import food_delivery_app.service.NotificationService;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private static int counter = 0;

    private final int id;
    private Customer customer;
    private List<OrderItem> items;
    private double totalAmount;
    private double finalAmount;
    private IOrderStatus status;
    private Address deliveryAddress;
    private DeliveryAgent deliveryAgent;
    private static final NotificationService notificationService = new NotificationService();
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
        this.finalAmount = totalAmount;
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

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public IOrderStatus getStatus() {
        return status;
    }

    public void moveToNextState() {
        IOrderStatus old = status;

        IOrderStatus next = status.next();

        if (old == next) {
            System.out.println("No next state.");
            return;
        }

        status = next;


        notificationService.sendNotification(customer, "Order #" +
                id +
                " moved from " +
                old +
                " → " +
                status);

    }

    public void assignDeliveryAgent(DeliveryAgent agent) {
        deliveryAgent = agent;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public DeliveryAgent getDeliveryAgent() {
        return deliveryAgent;
    }

    public String getOrderAbstract() {

        return String.format(
                "Order #%d | Customer: %s | Address: %s | Total: ₹%.2f | Status: %s",
                id,
                customer.getName(),
                deliveryAddress.getAddress(),
                finalAmount,
                status
        );
    }

    @Override
    public String toString() {
        return String.format(
                "%-5d %-15s %-20s %-15s %-12s ₹%-10.2f",
                id,
                customer.getName(),
                deliveryAddress.getAddress(),
                deliveryAgent != null ? deliveryAgent.getName() : "NOT_ASSIGNED",
                status,
                finalAmount
        );
    }
}