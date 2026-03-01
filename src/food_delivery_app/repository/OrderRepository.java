package food_delivery_app.repository;


import food_delivery_app.model.user.DeliveryAgent;
import food_delivery_app.model.order.Order;

import java.util.*;

public class OrderRepository {

    private static OrderRepository instance;
    //orderid & order
    private final Map<Integer, Order> orderHistory;
    private final Deque<Order> pendingOrders;

    private OrderRepository() {
        orderHistory = new HashMap<>();
        pendingOrders = new ArrayDeque<>();
    }

    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    public void addOrderToHistory(Order order) {
        orderHistory.put(order.getId(), order);
    }

    public void addOrderToPending(Order order)
    {
        pendingOrders.add(order);
    }
    public List<Order> getOrdersByDeliveryBoy(DeliveryAgent agent)
    {
        return orderHistory.values().stream().filter((o)->o.getDeliveryAgent() == agent).toList();
    }
    public Order getPendingOrder()
    {
        return pendingOrders.pollFirst();
    }

    public Order findById(int orderId) {
        return orderHistory.get(orderId);
    }

    public List<Order> findByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        for (Order o : orderHistory.values()) {
            if (o.getCustomer().getId() == customerId) {
                orders.add(o);
            }
        }
        return orders;
    }

    public List<Order> findByDeliveryBoyId(int deliveryBoyId) {
        List<Order> orders = new ArrayList<>();
        for (Order o : orderHistory.values()) {
            if (o.getDeliveryAgent() != null && o.getDeliveryAgent().getId() == deliveryBoyId) {
                orders.add(o);
            }
        }
        return orders;
    }

    public List<Order> findAll() {
        return new ArrayList<>(orderHistory.values());
    }
}
