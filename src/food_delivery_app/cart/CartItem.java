package food_delivery_app.cart;

import food_delivery_app.menu.MenuItem;

public class CartItem {
    private MenuItem cartItem;
    private int quantity;

    public MenuItem getItem() {
        return cartItem;
    }

    public void setItem(MenuItem item) {
        this.cartItem = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity)
    {
        this.quantity -= quantity;
    }
    public double getTotalPrice() {
        return cartItem.getPrice() * quantity;
    }

}
