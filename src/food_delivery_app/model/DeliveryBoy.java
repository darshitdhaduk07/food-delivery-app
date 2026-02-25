package food_delivery_app.model;

import java.util.ArrayList;
import java.util.List;

public class DeliveryBoy extends User{
    private static int counter = 0;
    private boolean available;
    private List<Order> assignedOrders;

    public DeliveryBoy(String name, String email, int password) {
        super(name, email, password);
        this.id = ++counter;

        this.available = true; // default available
        this.assignedOrders = new ArrayList<>();
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<Order> getAssignedOrders() {
        return assignedOrders;
    }

    public void assignOrder(Order order) {
        this.assignedOrders.add(order);
        this.available = false;
    }

    public void completeOrder(Order order) {
        this.assignedOrders.remove(order);
        this.available = true;
    }

    @Override
    public String toString() {
        return "DeliveryBoy{" +
                "id='" + id + '\'' +
                ", name='" + getName() + '\'' +
                ", available=" + available +
                '}';
    }
}
