package food_delivery_app;

import food_delivery_app.model.cart.Cart;
import food_delivery_app.controller.AuthController;
import food_delivery_app.model.menu.*;
import food_delivery_app.model.*;
import food_delivery_app.model.user.Customer;
import food_delivery_app.model.user.DeliveryAgent;
import food_delivery_app.service.*;

public class FoodApp {

    static ManagerService managerService = new ManagerService();
    static CustomerService customerService = new CustomerService();
    static final OrderService orderService =
            new OrderService();

    static final DeliveryAgentService deliveryAgentService =
            new DeliveryAgentService();

    static {
        OrderService.setDeliveryAgentService(deliveryAgentService);
        DeliveryAgentService.setOrderService(orderService);
    }
    public static void main(String[] args) {

        setupInitialData();

        AuthController authController = new AuthController();
        authController.start();
    }

    static void setupInitialData() {

        MenuCategory root = MenuStore.getMenu();

        MenuCategory pizza = new MenuCategory("Pizza");
        MenuCategory drinks = new MenuCategory("Drinks");

        pizza.add(new MenuItem("Margherita", 250));
        pizza.add(new MenuItem("Farmhouse", 300));

        drinks.add(new MenuItem("Coke", 50));
        drinks.add(new MenuItem("Pepsi", 50));

        root.add(pizza);
        root.add(drinks);

        DeliveryAgent d1 =
                new DeliveryAgent("Smit","raj@mail.com","Dd@123","9723138456");

        DeliveryAgent d2 =
                new DeliveryAgent("Vivek","amit@gmail.com","Dd@123","8000824129");

        managerService.addDeliveryAgent(d1);
        managerService.addDeliveryAgent(d2);

        Customer customer =
                new Customer("Darshit","darshit@mail.com","Dd@111","9723521513");

        customer.setCart(new Cart());
        customer.getAddresses().add(new Address("Rajkot"));

        customerService.registerCustomer(customer);
    }
}