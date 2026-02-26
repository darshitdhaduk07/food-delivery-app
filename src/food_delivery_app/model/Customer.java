package food_delivery_app.model;

import food_delivery_app.cart.Cart;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User{
    private static int counter = 0;
    List<Address> addresses;
    Cart cart;
    List<Notification> notifications;

    public Customer(String name, String email, int password) {
        super(name,email,password);
        this.id = ++counter;
        this.addresses = new ArrayList<>();

        this.notifications = new ArrayList<>();
        this.cart = new Cart();
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
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

        System.out.println("Your Addresses:");

        int index = 1;

        for (Address address : addresses) {
            System.out.println(index + ". " + address.getAddress());
            index++;
        }
    }
    public Cart getCart() {
        return cart;
    }
    public boolean isCartEmpty()
    {
        return cart.getItems().isEmpty();
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }



    @Override
    public String toString() {

        return String.format(
                "%-5d %-15s %-25s %-20s",
                id,
                getName(),
                getEmail(),
                addresses
        );
    }
}
