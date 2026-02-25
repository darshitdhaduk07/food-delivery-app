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
//        this.cart = new Cart(this);
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Cart getCart() {
        return cart;
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
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", addresses=" + addresses +
                ", cart=" + cart +
                ", notifications=" + notifications +
                '}';
    }
}
