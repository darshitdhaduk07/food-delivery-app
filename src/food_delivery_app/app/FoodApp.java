package food_delivery_app.app;

import food_delivery_app.cart.Cart;
import food_delivery_app.menu.*;
import food_delivery_app.model.*;
import food_delivery_app.payment.*;
import food_delivery_app.services.*;
import food_delivery_app.utility.InputValidator;

import java.util.Scanner;

public class FoodApp {

    //all the services
    static AuthenticationService auth = new AuthenticationService();
    static CartService cartService = new CartService();
    static OrderService orderService = new OrderService();
    static DeliveryService deliveryService = new DeliveryService();
    static ManagerService managerService = new ManagerService();
    static CustomerService customerService = new CustomerService();

    // for now only one custoemr
    static Customer customer;
    static DeliveryBoy boy;

    public static void main(String[] args) {

        setupInitialData();

        while (true) {

            System.out.println("\n===== FOOD DELIVERY APP =====");
            System.out.println("1. Manager Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Customer Signup");
            System.out.println("4. Delivery Boy Login");
            System.out.println("0. Exit");

            int choice = InputValidator.readInt("Enter choice: ");

            switch (choice) {

                case 1 -> managerMenu();

                case 2 -> {
                    customer = auth.loginCustomer();
                    if(customer != null)
                    {
                        customerMenu();
                    }
                    else {
                        System.out.println("Invalid Email or Pass");
                    }
                }

                case 3 -> auth.registerCustomer();

                case 4 -> {
                    boy = auth.loginDelivery();
                    if(boy!= null)
                    {
                        deliveryMenu();
                    }
                    else {
                        System.out.println("Invalid Email or Pass");
                    }
                }

                case 0 -> System.exit(0);

                default -> System.out.println("Invalid choice");
            }
        }
    }

    //initial data
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

        DeliveryBoy d1 =
                new DeliveryBoy("Smit", "raj@mail.com", 123);
        DeliveryBoy d2 =
                new DeliveryBoy("Vivek", "amit@gmail.com", 123);

        managerService.addDeliveryBoy(d1);
        managerService.addDeliveryBoy(d2);

        customer =
                new Customer("Darshit", "darshit@mail.com", 111);

        customer.setCart(new Cart());
        customer.getAddresses().add(
                new Address("Rajkot")
        );

        customerService.registerCustomer(customer);
    }

    //manager menu
    static void managerMenu() {

        while (true) {

            System.out.println("\n===== MANAGER MENU =====");
            System.out.println("1. Show Menu");
            System.out.println("2. Add Category");
            System.out.println("3. Add Item to Category");
            System.out.println("4. Update Item Price");
            System.out.println("5. Change Availability");
            System.out.println("6. Show Orders");
            System.out.println("7. Show Customers");
            System.out.println("8. Remove Customer");
            System.out.println("9. Add Delivery Boy");
            System.out.println("10. Remove Delivery Boy");
            System.out.println("11. Show Delivery Boys");
            System.out.println("0. LogOut");

            int ch = InputValidator.readInt("Enter choice: ");

            switch (ch) {

                case 1 -> managerService.showMenu();

                case 2 -> {
                    String name =
                            InputValidator.readStringOnlyLetters("Category Name: ");
                    managerService.addCategory(name);
                }

                case 3 -> {

                    managerService.showMenu();

                    int cid =
                            InputValidator.readInt("Category ID: ");

                    String itemName =
                            InputValidator.readString("Item Name: ");

                    double price =
                            InputValidator.readDouble("Price: ");

                    managerService.addItemToCategory(cid, itemName, price);
                }

                case 4 -> {

                    managerService.showMenu();

                    int id =
                            InputValidator.readInt("Item ID: ");

                    double price =
                            InputValidator.readDouble("New Price: ");

                    managerService.updatePrice(id, price);
                }

                case 5 -> {

                    managerService.showMenu();

                    int id =
                            InputValidator.readInt("Item ID: ");

                    boolean status =
                            InputValidator.readBoolean("Available");

                    managerService.changeAvailability(id, status);
                }

                case 6 -> managerService.showAllOrders();

                case 7 -> managerService.showAllCustomers();

                case 8 -> {
                    int id =
                            InputValidator.readInt("Customer ID: ");
                    managerService.removeCustomer(id);
                }

                case 9 -> {

                    String name =
                            InputValidator.readStringOnlyLetters("Name: ");

                    String email =
                            InputValidator.readEmail("Email: ");

                    int pass =
                            InputValidator.readInt("Password (number): ");

                    managerService.addDeliveryBoy(
                            new DeliveryBoy(name, email, pass)
                    );
                }

                case 10 -> {
                    int id =
                            InputValidator.readInt("DeliveryBoy ID: ");
                    managerService.removeDeliveryBoy(id);
                }

                case 11 -> managerService.showAllDeliveryBoys();

                case 0 -> {
                    System.out.println("Log Out...");
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }

    //customer menu
    static void customerMenu() {

        while (true) {

            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. Show Menu");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. Show Cart");
            System.out.println("4. Remove Item from Cart");
            System.out.println("5. Update Quantity");
            System.out.println("6. Place Order");
            System.out.println("7. Order History");
            System.out.println("0. LogOut");

            int ch = InputValidator.readInt("Enter choice: ");

            switch (ch) {

                case 1 -> managerService.showMenu();


                case 2 -> {

                    managerService.showMenu();

                    int id =
                            InputValidator.readInt("Item ID: ");

                    MenuComponent comp =
                            MenuStore.getMenu().findMenuComponentById(id);

                    if (comp instanceof MenuItem item) {

                        int qty =
                                InputValidator.readInt("Quantity: ");

                        cartService.addItem(
                                customer.getCart(),
                                item,
                                qty
                        );
                    } else {
                        System.out.println("Invalid item.");
                    }
                }

                case 3 -> cartService.showCart(customer.getCart());

                case 4 -> {
                    if (customer.isCartEmpty()) {
                        System.out.println("Cart is Empty");
                        break;
                    }
                    cartService.showCart(customer.getCart());
                    int id =
                            InputValidator.readInt("Item ID to remove: ");

                    cartService.removeItem(
                            customer.getCart(),
                            id
                    );
                }


                case 5 -> {
                    if (customer.isCartEmpty()) {
                        System.out.println("Cart is Empty");
                        break;
                    }
                    cartService.showCart(customer.getCart());
                    int id =
                            InputValidator.readInt("Item ID: ");

                    int qty =
                            InputValidator.readInt("New Quantity: ");

                    cartService.updateQuantity(
                            customer.getCart(),
                            id,
                            qty
                    );
                }

                case 6 -> {
                    if (customer.isCartEmpty()) {
                        System.out.println("Cart is Empty");
                        break;
                    }
                    PaymentContext payment =
                            new PaymentContext();
                    System.out.println("1. Cash");
                    System.out.println("2. UPI");

                    int choice = InputValidator.readInt("Select Payment Method: ", 1, 2);
                    if (choice == 1) {
                        payment.setPaymentStrategy(
                                new CashPayment()
                        );
                    } else {
                        String upi = InputValidator.readUPI("Enter UPI ID: ");
                        payment.setPaymentStrategy(
                                new UPIPayment(upi)
                        );
                    }

                    customerService.showAddresses(customer);
                    int id = InputValidator.readInt("Enter Address Id: ", 1, customer.getAddresses().size());
                    Address address = customer.getAddressById(id);
                    try {
                        orderService.placeOrder(
                                customer,
                                address,
                                payment,
                                managerService.getDiscountStrategy()
                        );
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                }

                case 7 -> {

                    customerService
                            .getOrderHistory(customer)
                            .forEach(System.out::println);
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


    static void deliveryMenu() {

        while (true) {

            System.out.println("\n===== DELIVERY MENU =====");
            System.out.println("Logged in as: " + boy.getName());

            System.out.println("1. Show My Orders");
            System.out.println("2. Mark OUT_FOR_DELIVERY");
            System.out.println("3. Mark DELIVERED");
            System.out.println("4. My Order History");
            System.out.println("0. LogOut");

            int ch = InputValidator.readInt("Choice: ");

            switch (ch) {

                case 1 -> {

                    deliveryService
                            .getOrdersByDeliveryBoy(boy)
                            .forEach(System.out::println);
                }

                case 2 -> {

                    int id =
                            InputValidator.readInt("Order ID: ");

                    Order order =
                            orderService.getOrder(id);

                    if (order != null &&
                            order.getDeliveryBoy() == boy) {

                        deliveryService.markOutForDelivery(order);
                    }
                }

                case 3 -> {

                    int id =
                            InputValidator.readInt("Order ID: ");

                    Order order =
                            orderService.getOrder(id);

                    if (order != null &&
                            order.getDeliveryBoy() == boy) {

                        deliveryService.completeDelivery(order);
                    }
                }

                case 4 -> {

                    deliveryService
                            .getOrdersByDeliveryBoy(boy)
                            .stream()
                            .filter(o ->
                                    o.getStatus() ==
                                            IOrderStatus.DELIVERED)
                            .forEach((System.out::println));
                }

                case 0 -> {
                    boy = null;
                    System.out.println("Log Out...");
                    return;
                }
            }
        }
    }
}
