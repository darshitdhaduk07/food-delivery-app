package food_delivery_app.repository;


import food_delivery_app.model.Order;

import java.util.*;

public class OrderRepository {

    private static OrderRepository instance;
    private final Map<Integer, Order> orderMap;

    private OrderRepository() {
        orderMap = new HashMap<>();
    }

    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    public void addOrder(Order order) {
        orderMap.put(order.getId(), order);
    }

    public void removeOrder(int orderId) {
        orderMap.remove(orderId);
    }

    public Order findById(int orderId) {
        return orderMap.get(orderId);
    }

    public List<Order> findByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        for (Order o : orderMap.values()) {
            if (o.getCustomer().getId() == customerId) {
                orders.add(o);
            }
        }
        return orders;
    }

    public List<Order> findByDeliveryBoyId(int deliveryBoyId) {
        List<Order> orders = new ArrayList<>();
        for (Order o : orderMap.values()) {
            if (o.getDeliveryBoy() != null && o.getDeliveryBoy().getId() == deliveryBoyId) {
                orders.add(o);
            }
        }
        return orders;
    }

    public List<Order> findAll() {
        return new ArrayList<>(orderMap.values());
    }
}
