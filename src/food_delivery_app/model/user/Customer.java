package food_delivery_app.model.user;

import food_delivery_app.model.cart.Cart;
import food_delivery_app.model.Address;
import food_delivery_app.model.notification.Notifiable;
import food_delivery_app.model.notification.Notification;
import food_delivery_app.utility.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User implements Notifiable {
    private static int counter = 0;
    private String phoneNumber;
    List<Address> addresses;
    Cart cart;
    List<Notification> currNotifications;
    List<Notification> notificationsHistory;
    private boolean panelOpen = false;


    public Customer(String name, String email, String password, String phoneNumber) {
        super(name, email, password);
        this.id = ++counter;
        this.addresses = new ArrayList<>();
        this.phoneNumber = phoneNumber;
        this.currNotifications = new ArrayList<>();
        this.notificationsHistory = new ArrayList<>();
        this.cart = new Cart();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void removeAddress(int id) {
        addresses.remove(id);
    }

    public void editAddress(int id, String newAddress) {

        int index = id - 1;

        if (index < 0 || index >= addresses.size()) {
            System.out.println("Invalid address ID.");
            return;
        }

        addresses.get(index).setAddress(newAddress);

        System.out.println("Address updated.");
    }

    public Address getAddressById(int id) {

        // user sees addresses starting from 1
        int index = id - 1;

        if (index < 0 || index >= addresses.size()) {
            System.out.println("Invalid address ID.");
            return null;
        }

        return addresses.get(index);
    }
    public void showAllAddresses() {

        if (addresses.isEmpty()) {
            System.out.println("No addresses found.");
            return;
        }

        System.out.println("──────────── Your Addresses ────────────");
        System.out.printf("%-5s %-40s%n", "ID", "ADDRESS");
        System.out.println("────────────────────────────────────────");
        int index = 1;

        for (Address address : addresses) {
            System.out.printf("%-5d %-40s%n", index++, address.getAddress());
        }
    }

    @Override
    public int getId() {
        return super.getId();
    }

    public Cart getCart() {
        return cart;
    }

    public boolean isCartEmpty() {
        return cart.getItems().isEmpty();
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<Notification> getCurrNotifications() {
        return currNotifications;
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

        System.out.println("\n════════════════ NOTIFICATION HISTORY ════════════════");
        System.out.printf("%-5s %-20s %-50s%n", "No", "Time", "Message");
        System.out.println("──────────────────────────────────────────────────────");

        int index = 1;

        for (Notification n : notificationsHistory) {

            System.out.printf("%-5d %-20s %-50s%n", index++, n.getTime(), n.getMessage());
        }

        System.out.println("══════════════════════════════════════════════════════\n");
    }


    @Override
    public String toString() {

        return String.format("%-5d %-15s %-25s %-20s", id, getName(), getEmail(), addresses);
    }
}
