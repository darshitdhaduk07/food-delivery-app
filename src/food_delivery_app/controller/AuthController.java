package food_delivery_app.controller;

import food_delivery_app.model.user.Customer;
import food_delivery_app.model.user.DeliveryAgent;
import food_delivery_app.model.user.Manager;
import food_delivery_app.service.AuthenticationService;
import food_delivery_app.utility.InputValidator;

public class AuthController {

    private final AuthenticationService auth = new AuthenticationService();

    private final ManagerController managerController = new ManagerController();

    private final CustomerController customerController = new CustomerController();

    private final DeliveryController deliveryAgentController = new DeliveryController();
    private Customer customer = null;
    private DeliveryAgent agent = null;
    private Manager manager = null;

    public void start() {

        while (true) {

            System.out.println("\n══════════════════════════════════════");
            System.out.println("        FOOD DELIVERY APP");
            System.out.println("══════════════════════════════════════");
            System.out.println("1. Manager ");
            System.out.println("2. Customer ");
            if (agent == null)
                System.out.println("3. Delivery Agent Login");
            else
                System.out.println("3. Delivery Agent Panel");
            System.out.println("0. Exit");
            System.out.println("══════════════════════════════════════");

            int choice = InputValidator.readInt("Select option: ");

            switch (choice) {

                case 1 -> managerMenu();
                case 2 -> {
                    if (Manager.getInstance() == null) {
                        System.out.println("Please Create Manager First");
                        break;
                    }
                    customerMenu();
                }
                case 3 -> {
                    if (Manager.getInstance() == null) {
                        System.out.println("Please Create Manager First");
                        break;
                    }
                    if (agent == null)
                    {
                        agent = auth.loginDeliveryAgent();
                        if(agent == null)
                            break;
                    }

                    boolean isLogout = deliveryAgentController.start(agent);
                    if (isLogout)
                        agent = null;


                }

                case 0 -> {
                    System.out.println("Exit...");
                    System.exit(0);
                }

                default -> System.out.println("\nInvalid choice.\n");
            }
        }

    }

    public void managerMenu() {
        while (true) {
            System.out.println("\n══════════════════════════════════════");
            System.out.println("              MANAGER");
            System.out.println("══════════════════════════════════════");
            System.out.println("1. Manager Register");
            if (manager == null)
                System.out.println("2. Manager Login");
            else
                System.out.println("2. Manager Panel");
            System.out.println("0. Back");
            System.out.println("══════════════════════════════════════");

            int choice = InputValidator.readInt("Select Option: ");

            switch (choice) {
                case 1 -> auth.registerManager();
                case 2 -> {
                    if (manager == null)
                    {
                        manager = auth.loginManager();
                        if(manager == null)
                            break;
                    }

                    boolean isLogout = managerController.start(manager);
                    if (isLogout)
                        manager = null;


                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("\nInvalid choice.\n");
            }
        }

    }

    public void customerMenu() {
        while (true) {
            System.out.println("\n══════════════════════════════════════");
            System.out.println("              CUSTOMER");
            System.out.println("══════════════════════════════════════");
            System.out.println("1. Customer Register");
            if (customer == null)
                System.out.println("2. Customer Login");
            else
                System.out.println("2. Customer Panel");
            System.out.println("0. Back");
            System.out.println("══════════════════════════════════════");

            int choice = InputValidator.readInt("Select Option: ");

            switch (choice) {
                case 1 -> auth.registerCustomer();
                case 2 -> {
                    if (customer == null)
                    {
                        customer = auth.loginCustomer();
                        if(customer == null)
                            break;
                    }

                    boolean isLogout = customerController.start(customer);
                    if (isLogout)
                        customer = null;

                }

                case 0 -> {
                    return;
                }
                default -> System.out.println("\nInvalid choice.\n");
            }
        }
    }
}