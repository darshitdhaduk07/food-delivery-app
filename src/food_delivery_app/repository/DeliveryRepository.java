package food_delivery_app.repository;

import food_delivery_app.model.user.DeliveryAgent;

import java.util.*;

public class DeliveryRepository {

    private static DeliveryRepository instance;
    private final Map<Integer, DeliveryAgent> deliveryMap;
    private final Map<String, DeliveryAgent> emailMap;
    private static final Set<String> phoneNumber = new HashSet<>();

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
    public Set<String> getPhoneNumber()
    {
        return phoneNumber;
    }
    public boolean isPhoneNumberExist(String phone)
    {
        return phoneNumber.contains(phone);
    }
    public boolean isEmailExist(String email)
    {
        return emailMap.containsKey(email);
    }
    public Map<Integer, DeliveryAgent> getDeliveryMap() {
        return deliveryMap;
    }

    public void addDeliveryAgent(DeliveryAgent deliveryAgent) {
        phoneNumber.add(deliveryAgent.getPhoneNumber());
        deliveryMap.put(deliveryAgent.getId(), deliveryAgent);
        emailMap.put(deliveryAgent.getEmail(), deliveryAgent);
    }

    public void removeDeliveryAgent(int id) {

        DeliveryAgent agent =
                deliveryMap.remove(id);

        if (agent != null) {
            emailMap.remove(
                    agent.getEmail().toLowerCase()
            );
            phoneNumber.remove(agent.getPhoneNumber());
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