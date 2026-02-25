package food_delivery_app.repository;

import food_delivery_app.model.DeliveryBoy;

import java.util.*;

public class DeliveryRepository {

    private static DeliveryRepository instance;
    private final Map<Integer, DeliveryBoy> deliveryMap;

    private DeliveryRepository() {
        deliveryMap = new HashMap<>();
    }

    public static DeliveryRepository getInstance() {
        if (instance == null) {
            instance = new DeliveryRepository();
        }
        return instance;
    }

    public void addDeliveryBoy(DeliveryBoy deliveryBoy) {
        deliveryMap.put(deliveryBoy.getId(), deliveryBoy);
    }

    public void removeDeliveryBoy(int id) {
        deliveryMap.remove(id);
    }

    public DeliveryBoy findById(int id) {
        return deliveryMap.get(id);
    }

    public List<DeliveryBoy> findAll() {
        return new ArrayList<>(deliveryMap.values());
    }
}