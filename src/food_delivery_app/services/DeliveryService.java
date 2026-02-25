package food_delivery_app.services;

import food_delivery_app.model.DeliveryBoy;
import food_delivery_app.model.Order;
import food_delivery_app.model.OrderStatus;
import food_delivery_app.repository.DeliveryRepository;

import java.util.List;

public class DeliveryService {

    private final DeliveryRepository deliveryRepo;

    // round robin pointer
    private int currentIndex = 0;

    public DeliveryService() {
        this.deliveryRepo = DeliveryRepository.getInstance();
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

        boy.assignOrder(order);
        order.assignDeliveryBoy(boy);

        order.setStatus(OrderStatus.OUT_FOR_DELIVERY);

        System.out.println(
                "Order " + order.getId()
                        + " assigned to " + boy.getName()
        );
    }

//    complete delivey
    public void completeDelivery(Order order) {

        DeliveryBoy boy = order.getDeliveryBoy();

        if (boy == null) return;

        boy.completeOrder(order);
        order.setStatus(OrderStatus.DELIVERED);

        System.out.println("Order delivered.");
    }
}