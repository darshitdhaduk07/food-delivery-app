package food_delivery_app.controller;

import food_delivery_app.exception.PaymentFailedException;
import food_delivery_app.model.menu.*;
import food_delivery_app.model.*;
import food_delivery_app.model.user.Customer;
import food_delivery_app.service.*;
import food_delivery_app.utility.InputValidator;

public class CustomerController {

    private final CartService cartService = new CartService();

    private final ManagerService managerService = new ManagerService();
    private final CustomerService customerService = new CustomerService();
    private final OrderService orderService = DeliveryAgentService.getOrderService();
    public boolean start(Customer customer) {

        while (true) {

            System.out.println("\n══════════════════════════════════════");
            System.out.println("            CUSTOMER PANEL");
            System.out.println("══════════════════════════════════════");
            System.out.println("1. Show Menu");
            System.out.println("2. Cart Management");
            System.out.println("3. Place Order");
            System.out.println("4. Order History");
            System.out.println("5. Address Management");
            System.out.println("6. Notification Center");
            System.out.println("7. Back to Previous Menu");
            System.out.println("0. Logout");
            System.out.println("══════════════════════════════════════");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> managerService.showMenu();

                case 2 -> cartMenu(customer);

                case 3 -> {
                    if (cartService.isCartEmpty(customer.getCart())) {
                        System.out.println("Cart is empty.");
                        break;
                    }
                    boolean choice = InputValidator.readBoolean("If you want to Place Order");
                    if (!choice) break;

                    customer.showAllAddresses();
                    int id = InputValidator.readInt("Address ID: ", 1, customer.getAddresses().size());
                    Address address = customer.getAddressById(id);

                    try {
                        orderService.placeOrder(customer, address, managerService.getDiscount());
                    } catch (PaymentFailedException e) {
                        System.out.println(e.getMessage());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                case 4 -> {
                    System.out.printf("%-5s %-15s %-20s %-15s %-12s %-10s%n", "ID", "CUSTOMER", "ADDRESS", "DELIVERY_AGENT", "STATUS", "TOTAL");
                    System.out.println("──────────────────────────────────────────────────────────────────────────");
                    customerService.getOrderHistory(customer).forEach(System.out::println);
                }
                case 5 -> addressMenu(customer);


                case 6 -> notificationMenu(customer);
                case 7 -> {
                    return false;
                }
                case 0 -> {
                    System.out.println("Log Out...");
                    return true;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    /* ===== ADDRESS MENU ===== */

    private void addressMenu(Customer customer) {

        while (true) {

            System.out.println("\n──────── ADDRESS MANAGEMENT ────────");
            System.out.println("1. Show Addresses");
            System.out.println("2. Add Address");
            System.out.println("3. Edit Address");
            System.out.println("4. Remove Address");
            System.out.println("0. Back");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> customer.showAllAddresses();

                case 2 -> {
                    String addr = InputValidator.readString("New Address: ");
                    Address address = new Address(addr);
                    customer.addAddress(address);
                }

                case 3 -> {
                    customer.showAllAddresses();
                    int id = InputValidator.readPositiveInt("Address ID: ");
                    String address = InputValidator.readString("New Address: ");
                    customer.editAddress(id, address);
                }

                case 4 -> {
                    customer.showAllAddresses();
                    int id = InputValidator.readPositiveInt("Address ID: ");
                    customer.removeAddress(id - 1);
                }

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    /* ===== NOTIFICATION ===== */

    private void notificationMenu(Customer customer) {

        while (true) {

            System.out.println("\n──────── NOTIFICATION CENTER ────────");
            System.out.println("1. Live Notifications");
            System.out.println("2. Notification History");
            System.out.println("0. Back");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> customer.openNotificationPanel();

                case 2 -> customer.showNotificationsHistory();

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    /* ===== CART ===== */

    private void cartMenu(Customer customer) {
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

                        int qty = InputValidator.readInt("Quantity: ", 1, 100);

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
                    int qty = InputValidator.readInt("New Quantity: ", 1, 100);

                    cartService.updateQuantity(customer.getCart(), id, qty);
                }

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

}