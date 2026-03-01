package food_delivery_app.model.user;

import food_delivery_app.model.notification.Notifiable;
import food_delivery_app.model.notification.Notification;
import food_delivery_app.utility.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAgent extends User implements Notifiable {
    private static int counter = 0;
    private boolean available;
    private String phoneNumber;
    private List<Notification> currNotifications;
    private List<Notification> notificationsHistory;
    private boolean panelOpen = false;


    public DeliveryAgent(String name, String email, String password,String phoneNumber) {
        super(name, email, password);
        this.id = ++counter;
        this.phoneNumber = phoneNumber;
        this.available = true;
        notificationsHistory = new ArrayList<>();
        currNotifications = new ArrayList<>();// default available
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {

        return String.format("%-5d %-15s %-12s", id, getName(), available ? "AVAILABLE" : "BUSY");
    }

    public synchronized void addNotification(Notification n) {
        currNotifications.add(n);
        notificationsHistory.add(n);
        notifyAll();
    }
    public void startNotificationListener() {

        while (panelOpen) {

            Notification n;

            synchronized (this) {

                try {
                    while (panelOpen && currNotifications.isEmpty()) {
                        wait();
                    }

                    if (!panelOpen) return;

                    n = currNotifications.remove(0);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            System.out.println("\n[NOTIFICATION] " + n);
            System.out.print("> ");
        }
    }
    public void openNotificationPanel() {

        if (panelOpen) {
            System.out.println("Panel already open.");
            return;
        }

        panelOpen = true;
        Thread listener = new Thread(this::startNotificationListener);

        listener.start();

        System.out.println("Notification panel opened.");
        System.out.println("Type EXIT to close.");

        while (true) {

            String input = InputValidator.readString("> ");

            if (input.equalsIgnoreCase("EXIT")) {
                closeNotificationPanel();
                break;
            }
        }
    }

    public synchronized void closeNotificationPanel() {

        panelOpen = false;
        notifyAll();
        System.out.println("Panel closed.");
    }

    public synchronized void showNotificationsHistory() {

        if (notificationsHistory == null || notificationsHistory.isEmpty()) {
            System.out.println("No Notifications.");
            return;
        }

        System.out.println("\n════════════ DELIVERY NOTIFICATION HISTORY ════════════");
        System.out.printf("%-5s %-20s %-50s%n",
                "No", "Time", "Message");
        System.out.println("──────────────────────────────────────────────────────");

        int index = 1;

        for (Notification n : notificationsHistory) {

            System.out.printf("%-5d %-20s %-50s%n",
                    index++,
                    n.getTime(),
                    n.getMessage());
        }

        System.out.println("══════════════════════════════════════════════════════\n");
    }
}
