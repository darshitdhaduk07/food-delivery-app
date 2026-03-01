package food_delivery_app.service;

import food_delivery_app.model.user.DeliveryAgent;
import food_delivery_app.model.order.Order;
import food_delivery_app.model.order.IOrderStatus;
import food_delivery_app.repository.DeliveryRepository;
import food_delivery_app.repository.OrderRepository;

import java.util.List;
import java.util.Map;


public class DeliveryAgentService {

    private final DeliveryRepository deliveryRepo;
    private final OrderRepository orderRepo;
    private static OrderService orderService;
    private NotificationService notificationService;

    public DeliveryAgentService() {
        this.deliveryRepo = DeliveryRepository.getInstance();
        this.orderRepo = OrderRepository.getInstance();
        this.notificationService = new NotificationService();

    }
    public static void setOrderService(OrderService o) {
        orderService = o;
    }
    public static OrderService getOrderService()
    {
        return orderService;
    }
    public DeliveryAgent assignDeliveryAgent(Order order) {

        Map<Integer, DeliveryAgent> deliveryMap =
                deliveryRepo.getDeliveryMap();

        for (DeliveryAgent agent : deliveryMap.values()) {

            if (agent.isAvailable()) {

                agent.setAvailable(false);
                orderService.nextStatusAsync(order,10);
                order.assignDeliveryAgent(agent);
                notificationService.sendNotification(
                        agent,"New Order Assigned → "+ order.getOrderAbstract()
                );

                System.out.println(
                        "Order assigned to: " + agent.getName()
                );

                return agent;
            }
        }

        System.out.println("No delivery agent available. Added to queue.");
        orderRepo.addOrderToPending(order);

        return null;
    }


    public void markOutForDelivery(Order order) {

        if (order == null) return;
        if(order.getStatus() == IOrderStatus.DELIVERED)
        {
            System.out.println("Order is already Delevered");
        }
        if (order.getStatus() == IOrderStatus.ON_THE_WAY) {
            System.out.println("Already out for delivery");
            return;
        }

        orderService.nextStatusAsync(order,0);

        System.out.println("Order marked ON_THE_WAY.");
    }
    public List<Order> getOrdersByDeliveryBoy(DeliveryAgent boy) {
        return orderRepo.findAll()
                .stream()
                .filter(o ->
                        o.getDeliveryAgent() != null &&
                                o.getDeliveryAgent().getId() == boy.getId())
                .toList();
    }
    public Order getCurrentOrder(DeliveryAgent agent) {

        return orderRepo.findAll()
                .stream()
                .filter(o ->
                        o.getDeliveryAgent() == agent &&
                                o.getStatus() != IOrderStatus.DELIVERED &&
                                o.getStatus() != IOrderStatus.CANCELLED)
                .findFirst()
                .orElse(null);
    }

    //    complete delivey
    public void completeDelivery(Order order) {
        if(order.getStatus() == IOrderStatus.DELIVERED)
        {
            System.out.println("Order is already Delivered");
            return;
        }
        if (order.getStatus() != IOrderStatus.ON_THE_WAY) {
            System.out.println("Invalid Id");
            return;
        }

        DeliveryAgent agent = order.getDeliveryAgent();

        if (agent == null) return;

        // mark delivered
        orderService.nextStatusAsync(order,0);
        // check pending queue
        Order nextOrder = orderRepo.getPendingOrder();

        if (nextOrder != null) {
            agent.setAvailable(false);
            nextOrder.assignDeliveryAgent(agent);
            orderService.nextStatusAsync(nextOrder,0);
        } else {
            // no pending orders → agent free
            agent.setAvailable(true);
            System.out.println("Agent is now free.");
        }

        System.out.println("Order delivered.");
    }
}