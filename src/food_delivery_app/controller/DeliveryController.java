package food_delivery_app.controller;

import food_delivery_app.model.user.DeliveryAgent;
import food_delivery_app.model.order.IOrderStatus;
import food_delivery_app.model.order.Order;
import food_delivery_app.service.*;
import food_delivery_app.utility.InputValidator;

public class DeliveryController {

    private final DeliveryAgentService deliveryAgentService = new DeliveryAgentService();

    private final OrderService orderService = new OrderService();

    public boolean start(DeliveryAgent agent) {

        while (true) {

            System.out.println("\n══════════════════════════════════════");
            System.out.println("            DELIVERY PANEL");
            System.out.println("Logged in as: " + agent.getName());
            System.out.println("══════════════════════════════════════");
            System.out.println("1. View Current Order");
            System.out.println("2. Mark ON_THE_WAY");
            System.out.println("3. Mark DELIVERED");
            System.out.println("4. My Order History");
            System.out.println("5. Notification Center");
            System.out.println("6. Back to Previous Menu");
            System.out.println("0. Logout");
            System.out.println("══════════════════════════════════════");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {
                case 1 -> {

                    Order current = deliveryAgentService.getCurrentOrder(agent);

                    if (current == null) {
                        System.out.println("No active order.");
                    } else {

                        System.out.println("\n──────────────── CURRENT ORDER ────────────────");

                        System.out.printf("%-5s %-15s %-20s %-15s %-12s %-10s%n", "ID", "CUSTOMER", "ADDRESS", "DELIVERY", "STATUS", "TOTAL");

                        System.out.println("──────────────────────────────────────────────────────────────────────────");

                        System.out.printf("%-5d %-15s %-20s %-15s %-12s ₹%-10.2f%n", current.getId(), current.getCustomer().getName(), current.getDeliveryAddress().getAddress(), current.getDeliveryAgent().getName(), current.getStatus(), current.getTotalAmount());
                    }
                }

                case 2 -> {

                    int id = InputValidator.readInt("Order ID: ");
                    Order order = orderService.getOrder(id);

                    if (order != null && order.getDeliveryAgent() == agent) {

                        deliveryAgentService.markOutForDelivery(order);

                    } else {
                        System.out.println("Invalid order.");
                    }
                }

                case 3 -> {

                    int id = InputValidator.readInt("Order ID: ");
                    Order order = orderService.getOrder(id);

                    if (order != null && order.getDeliveryAgent() == agent) {

                        deliveryAgentService.completeDelivery(order);

                    } else {
                        System.out.println("Invalid order.");
                    }
                }

                case 4 -> {
                    System.out.printf("%-5s %-15s %-15s %-18s %-10s%n", "ID", "CUSTOMER", "DELIVERY", "STATUS", "TOTAL");
                    System.out.println("────────────────────────────────────────────────────────────────────");

                    deliveryAgentService.getOrdersByDeliveryBoy(agent).stream().filter(o -> o.getStatus() == IOrderStatus.DELIVERED).forEach(System.out::println);
                }
                case 5 -> notificationMenu(agent);


                case 6 -> {
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

    private void notificationMenu(DeliveryAgent agent) {

        while (true) {

            System.out.println("\n──────── NOTIFICATION CENTER ────────");
            System.out.println("1. Live Notifications");
            System.out.println("2. Notification History");
            System.out.println("0. Back");

            int ch = InputValidator.readInt("Select option: ");

            switch (ch) {

                case 1 -> agent.openNotificationPanel();

                case 2 -> agent.showNotificationsHistory();

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }

    }
}