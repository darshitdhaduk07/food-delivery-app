package food_delivery_app.services;

import food_delivery_app.model.DeliveryBoy;
import food_delivery_app.model.Order;
import food_delivery_app.model.IOrderStatus;
import food_delivery_app.repository.DeliveryRepository;
import food_delivery_app.repository.OrderRepository;

import java.util.List;

public class DeliveryService {

    private final DeliveryRepository deliveryRepo;
    private final OrderRepository orderRepo;

    // round robin pointer
    private int currentIndex = 0;

    public DeliveryService() {
        this.deliveryRepo = DeliveryRepository.getInstance();
        this.orderRepo = OrderRepository.getInstance();
    }

    // get boy in round robin
    public DeliveryBoy getNextDeliveryBoy() {

        List<DeliveryBoy> boys = deliveryRepo.findAll();

        if (boys.isEmpty()) {
            throw new RuntimeException("No delivery boys available.");
        }

        DeliveryBoy boy = boys.get(currentIndex);

        currentIndex = (currentIndex + 1) % boys.size();

        return boy;
    }

    //assign
    public void assignDeliveryBoy(Order order) {

        DeliveryBoy boy = getNextDeliveryBoy();

        if (boy.isAvailable()) {

            order.setStatus(IOrderStatus.OUT_FOR_DELIVERY);
        }
        boy.assignOrder(order);
        order.assignDeliveryBoy(boy);

        System.out.println("Order " + order.getId() + " assigned to " + boy.getName());
    }

    public List<Order> getOrdersByDeliveryBoy(DeliveryBoy boy) {
        return boy.getAssignedOrders();
    }

    public void markOutForDelivery(Order order) {

        if (order == null) return;
        if (order.getStatus() == IOrderStatus.OUT_FOR_DELIVERY) {
            System.out.println("Already out for delivery");
            return;
        }

        order.setStatus(IOrderStatus.OUT_FOR_DELIVERY);

        System.out.println("Order marked OUT_FOR_DELIVERY.");
    }

    //    complete delivey
    public void completeDelivery(Order order) {
        if (order.getStatus() != IOrderStatus.OUT_FOR_DELIVERY) {
            System.out.println("Please make order OUT FOR DELIVERY First");
            return;
        }
        DeliveryBoy boy = order.getDeliveryBoy();

        if (boy == null) return;

        boy.completeOrder(order);
        order.setStatus(IOrderStatus.DELIVERED);

        System.out.println("Order delivered.");
    }
}