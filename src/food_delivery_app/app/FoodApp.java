package food_delivery_app.app;

import food_delivery_app.cart.Cart;
import food_delivery_app.menu.*;
import food_delivery_app.model.*;
import food_delivery_app.services.*;
import food_delivery_app.utility.InputValidator;

public class FoodApp {

    static AuthenticationService auth = new AuthenticationService();
    static CartService cartService = new CartService();
    static OrderService orderService = new OrderService();
    static DeliveryService deliveryService = new DeliveryService();
    static ManagerService managerService = new ManagerService();
    static CustomerService customerService = new CustomerService();

    static Customer customer;
    static DeliveryAgent boy;
    static Manager manager;

    public static void main(String[] args) {

        setupInitialData();


        while (true) {

            System.out.println("\n══════════════════════════════════════");
            System.out.println("        FOOD DELIVERY APP");
            System.out.println("══════════════════════════════════════");
            System.out.println("1. Manager Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Customer Signup");
            System.out.println("4. Delivery Agent Login");
            System.out.println("0. Exit");
            System.out.println("══════════════════════════════════════");

            int choice = InputValidator.readInt("Select option: ");

            switch (choice) {

                case 1 -> {
                    manager = auth.loginManager();
                    if (manager != null) managerMenu();
                    else System.out.println("\nInvalid Email or Password.\n");
                }

                case 2 -> {
                    customer = auth.loginCustomer();
                    if (customer != null) {
                        customerMenu();
                    } else {
                        System.out.println("\nInvalid Email or Password.\n");
                    }
                }

                case 3 -> auth.registerCustomer();

                case 4 -> {
                    boy = auth.loginDelivery();
                    if (boy != null) {
                        deliveryMenu(boy);
                    } else {
                        System.out.println("\nInvalid Email or Password.\n");
                    }
                }

                case 0 -> {
                    System.out.println("Exit...");
                    System.exit(0);
                }

                default -> System.out.println("\nInvalid choice.\n");
            }
        }
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

        DeliveryAgent d1 = new DeliveryAgent("Smit", "raj@mail.com", 123);
        DeliveryAgent d2 = new DeliveryAgent("Vivek", "amit@gmail.com", 123);

        managerService.addDeliveryBoy(d1);
        managerService.addDeliveryBoy(d2);

        customer = new Customer("Darshit", "darshit@mail.com", 111);
        customer.setCart(new Cart());
        customer.getAddresses().add(new Address("Rajkot"));

        customerService.registerCustomer(customer);
    }

    static void managerMenu() {

        while (true) {

            System.out.println("\n══════════════════════════════════════");
            System.out.println("            MANAGER PANEL");
            System.out.println("══════════════════════════════════════");
            System.out.println("1. Menu Management");
            System.out.println("2. Order Management");
            System.out.println("3. Customer Management");
            System.out.println("4. Delivery Management");
            System.out.println("0. Logout");
            System.out.println("══════════════════════════════════════");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> managerMenuSection();

                case 2 -> managerOrderSection();

                case 3 -> managerCustomerSection();

                case 4 -> managerDeliverySection();

                case 0 -> {
                    System.out.println("\nLog Out...\n");
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void managerMenuSection() {

        while (true) {

            System.out.println("\n──────── MENU MANAGEMENT ────────");
            System.out.println("1. Show Menu");
            System.out.println("2. Add Category");
            System.out.println("3. Add Item to Category");
            System.out.println("4. Update Item Price");
            System.out.println("5. Change Availability");
            System.out.println("0. Back");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> managerService.showMenu();

                case 2 -> {
                    String name = InputValidator.readStringOnlyLetters("Category Name: ");
                    managerService.addCategory(name);
                }

                case 3 -> {
                    managerService.showMenu();
                    int cid = InputValidator.readPositiveInt("Category ID: ");
                    String itemName = InputValidator.readString("Item Name: ");
                    double price = InputValidator.readDouble("Price: ");
                    managerService.addItemToCategory(cid, itemName, price);
                }

                case 4 -> {
                    managerService.showMenu();
                    int id = InputValidator.readPositiveInt("Item ID: ");
                    double price = InputValidator.readDouble("New Price: ");
                    managerService.updatePrice(id, price);
                }

                case 5 -> {
                    managerService.showMenu();
                    int id = InputValidator.readPositiveInt("Item ID: ");
                    boolean status = InputValidator.readBoolean("Available");
                    managerService.changeAvailability(id, status);
                }

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void managerOrderSection() {

        while (true) {

            System.out.println("\n──────── ORDER MANAGEMENT ────────");
            System.out.println("1. Show All Orders");
            System.out.println("0. Back");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> managerService.showAllOrders();

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void managerCustomerSection() {

        while (true) {

            System.out.println("\n──────── CUSTOMER MANAGEMENT ────────");
            System.out.println("1. Show Customers");
            System.out.println("2. Remove Customer");
            System.out.println("0. Back");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> managerService.showAllCustomers();

                case 2 -> {
                    int id = InputValidator.readInt("Customer ID: ");
                    managerService.removeCustomer(id);
                }

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void managerDeliverySection() {

        while (true) {

            System.out.println("\n──────── DELIVERY MANAGEMENT ────────");
            System.out.println("1. Show Delivery Boys");
            System.out.println("2. Add Delivery Boy");
            System.out.println("3. Remove Delivery Boy");
            System.out.println("0. Back");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> managerService.showAllDeliveryBoys();

                case 2 -> {
                    String name = InputValidator.readStringOnlyLetters("Name: ");
                    String email = InputValidator.readEmail("Email: ");
                    int pass = InputValidator.readPassword("Password: ");
                    managerService.addDeliveryBoy(new DeliveryAgent(name, email, pass));
                }

                case 3 -> {
                    int id = InputValidator.readInt("DeliveryAgent ID: ");
                    managerService.removeDeliveryBoy(id);
                }

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void customerMenu() {

        while (true) {

            System.out.println("\n══════════════════════════════════════");
            System.out.println("            CUSTOMER PANEL");
            System.out.println("══════════════════════════════════════");
            System.out.println("1. Show Menu");
            System.out.println("2. Cart Management");
            System.out.println("3. Place Order");
            System.out.println("4. Order History");
            System.out.println("0. Logout");
            System.out.println("══════════════════════════════════════");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> managerService.showMenu();

                case 2 -> customerCartMenu();

                case 3 -> {

                    if (customer.isCartEmpty()) {
                        System.out.println("Cart is Empty.");
                        break;
                    }

                    customerService.showAddresses(customer);
                    int id = InputValidator.readInt("Address ID: ", 1, customer.getAddresses().size());
                    Address address = customer.getAddressById(id);

                    try {
                        orderService.placeOrder(customer, address, managerService.getDiscountStrategy());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                case 4 -> {
                    System.out.printf("%-5s %-15s %-15s %-18s %-10s%n", "ID", "CUSTOMER", "DELIVERY AGENT", "STATUS", "TOTAL");

                    System.out.println("---------------------------------------------------------------");
                    customerService.getOrderHistory(customer).forEach(System.out::println);
                }

                case 0 -> {
                    customer = null;
                    System.out.println("Log Out...");
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void customerCartMenu() {

        while (true) {

            System.out.println("\n──────── CART MANAGEMENT ────────");
            System.out.println("1. Add Item");
            System.out.println("2. Show Cart");
            System.out.println("3. Remove Item");
            System.out.println("4. Update Quantity");
            System.out.println("0. Back");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> {

                    managerService.showMenu();

                    int id = InputValidator.readPositiveInt("Item ID: ");

                    MenuComponent comp = MenuStore.getMenu().findMenuComponentById(id);

                    if (comp instanceof MenuItem item) {

                        int qty = InputValidator.readInt("Quantity: ",1,100);

                        cartService.addItem(customer.getCart(), item, qty);
                    } else {
                        System.out.println("Invalid item.");
                    }
                }

                case 2 -> cartService.showCart(customer.getCart());

                case 3 -> {

                    if (customer.isCartEmpty()) {
                        System.out.println("Cart is Empty.");
                        break;
                    }

                    cartService.showCart(customer.getCart());

                    int id = InputValidator.readPositiveInt("Item ID: ");

                    cartService.removeItem(customer.getCart(), id);
                }

                case 4 -> {

                    if (customer.isCartEmpty()) {
                        System.out.println("Cart is Empty.");
                        break;
                    }

                    cartService.showCart(customer.getCart());

                    int id = InputValidator.readPositiveInt("Item ID: ");
                    int qty = InputValidator.readInt("New Quantity: ",1,100);

                    cartService.updateQuantity(customer.getCart(), id, qty);
                }

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void deliveryMenu(DeliveryAgent boy) {

        while (true) {

            System.out.println("\n══════════════════════════════════════");
            System.out.println("            DELIVERY PANEL");
            System.out.println("Logged in as: " + boy.getName());
            System.out.println("══════════════════════════════════════");
            System.out.println("1. View Current Order");
            System.out.println("2. Mark ON_THE_WAY");
            System.out.println("3. Mark DELIVERED");
            System.out.println("4. My Order History");
            System.out.println("0. Logout");
            System.out.println("══════════════════════════════════════");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {
                case 1 -> {

                    Order current =
                            deliveryService.getCurrentOrder(boy);

                    if (current == null) {
                        System.out.println("No active order.");
                    } else {
                        System.out.println("\n===== CURRENT ORDER =====");
                        System.out.printf(
                                "%-5s %-15s %-15s %-18s %-10s%n",
                                "ID", "CUSTOMER", "DELIVERY", "STATUS", "TOTAL"
                        );
                        System.out.println(
                                "-----------------------------------------------------"
                        );
                        System.out.println(current);
                    }
                }

                case 2 -> {

                    int id = InputValidator.readInt("Order ID: ");
                    Order order = orderService.getOrder(id);

                    if (order != null && order.getDeliveryBoy() == boy) {

                        deliveryService.markOutForDelivery(order);

                    } else {
                        System.out.println("Invalid order.");
                    }
                }

                case 3 -> {

                    int id = InputValidator.readInt("Order ID: ");
                    Order order = orderService.getOrder(id);

                    if (order != null && order.getDeliveryBoy() == boy) {

                        deliveryService.completeDelivery(order);

                    } else {
                        System.out.println("Invalid order.");
                    }
                }

                case 4 -> {
                    System.out.printf("%-5s %-15s %-15s %-18s %-10s%n", "ID", "CUSTOMER", "DELIVERY", "STATUS", "TOTAL");

                    System.out.println("---------------------------------------------------------------");

                    deliveryService.getOrdersByDeliveryBoy(boy).stream().filter(o -> o.getStatus() == IOrderStatus.DELIVERED).forEach(System.out::println);
                }

                case 0 -> {
                    System.out.println("Log Out...");
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }
}