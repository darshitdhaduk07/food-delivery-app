package food_delivery_app.services;

import food_delivery_app.model.DeliveryAgent;
import food_delivery_app.model.Order;
import food_delivery_app.model.IOrderStatus;
import food_delivery_app.repository.DeliveryRepository;
import food_delivery_app.repository.OrderRepository;

import java.util.List;
import java.util.Map;

public class DeliveryService {

    private final DeliveryRepository deliveryRepo;
    private final OrderRepository orderRepo;

    // round robin pointer
    private int currentIndex = 0;

    public DeliveryService() {
        this.deliveryRepo = DeliveryRepository.getInstance();
        this.orderRepo = OrderRepository.getInstance();
    }

    public DeliveryAgent assignDeliveryAgent(Order order) {

        Map<Integer, DeliveryAgent> deliveryMap =
                deliveryRepo.getDeliveryMap();

        for (DeliveryAgent agent : deliveryMap.values()) {

            if (agent.isAvailable()) {

                agent.setAvailable(false);

                order.setDeliveryAgent(agent);
                order.moveToNextState();

                System.out.println(
                        "Order assigned to: " + agent.getName()
                );

                return agent;
            }
        }

        // NO AGENT AVAILABLE → ADD TO QUEUE
        System.out.println("No delivery agent available. Added to queue.");
        orderRepo.addOrder(order);

        return null;
    }


    public void markOutForDelivery(Order order) {

        if (order == null) return;
        if (order.getStatus() == IOrderStatus.ON_THE_WAY) {
            System.out.println("Already out for delivery");
            return;
        }

        order.moveToNextState();

        System.out.println("Order marked ON_THE_WAY.");
    }
    public List<Order> getOrdersByDeliveryBoy(DeliveryAgent boy) {
        return orderRepo.findAll()
                .stream()
                .filter(o ->
                        o.getDeliveryBoy() != null &&
                                o.getDeliveryBoy().getId() == boy.getId())
                .toList();
    }
    public Order getCurrentOrder(DeliveryAgent agent) {

        return orderRepo.findAll()
                .stream()
                .filter(o ->
                        o.getDeliveryBoy() == agent &&
                                o.getStatus() != IOrderStatus.DELIVERED &&
                                o.getStatus() != IOrderStatus.CANCELLED)
                .findFirst()
                .orElse(null);
    }

    //    complete delivey
    public void completeDelivery(Order order) {
        if (order.getStatus() != IOrderStatus.ON_THE_WAY) {
            System.out.println("Invalid Id");
            return;
        }

        DeliveryAgent agent = order.getDeliveryBoy();

        if (agent == null) return;

        // mark delivered
        order.moveToNextState();

        // check pending queue
        Order nextOrder = orderRepo.getPendingOrder();

        if (nextOrder != null) {

            nextOrder.setDeliveryAgent(agent);
            nextOrder.moveToNextState();

        } else {
            // no pending orders → agent free
            agent.setAvailable(true);
            System.out.println("Agent is now free.");
        }

        System.out.println("Order delivered.");
    }
}