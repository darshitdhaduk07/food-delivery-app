package food_delivery_app.repository;

import food_delivery_app.model.DeliveryAgent;

import java.util.*;

public class DeliveryRepository {

    private static DeliveryRepository instance;
    private final Map<Integer, DeliveryAgent> deliveryMap;
    private final Map<String, DeliveryAgent> emailMap;

    private DeliveryRepository() {
        deliveryMap = new HashMap<>();
        emailMap = new HashMap<>();
    }

    public static DeliveryRepository getInstance() {
        if (instance == null) {
            instance = new DeliveryRepository();
        }
        return instance;
    }

    public Map<Integer, DeliveryAgent> getDeliveryMap() {
        return deliveryMap;
    }

    public void addDeliveryBoy(DeliveryAgent deliveryAgent) {

        String email =
                deliveryAgent.getEmail().toLowerCase();

        if (emailMap.containsKey(email)) {
            System.out.println("Email already exists.");
            return;
        }

        deliveryMap.put(deliveryAgent.getId(), deliveryAgent);
        emailMap.put(email, deliveryAgent);
    }

    public void removeDeliveryBoy(int id) {

        DeliveryAgent agent =
                deliveryMap.remove(id);

        if (agent != null) {
            emailMap.remove(
                    agent.getEmail().toLowerCase()
            );
        }
    }
    public DeliveryAgent findByEmail(String email) {
        return emailMap.get(email.toLowerCase());
    }
    public DeliveryAgent findById(int id) {
        return deliveryMap.get(id);
    }

    public List<DeliveryAgent> findAll() {
        return new ArrayList<>(deliveryMap.values());
    }
}