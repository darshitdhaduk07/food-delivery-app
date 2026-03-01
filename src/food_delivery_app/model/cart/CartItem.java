package food_delivery_app.model.cart;

import food_delivery_app.model.menu.MenuItem;

public class CartItem {
    private MenuItem cartItem;
    private int quantity;

    public CartItem(MenuItem item, int quantity) {
        this.cartItem = item;
        this.quantity = quantity;
    }

    public MenuItem getItem() {
        return cartItem;
    }

    public void setItem(MenuItem item) {
        this.cartItem = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return cartItem.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItem=" + cartItem +
                ", quantity=" + quantity +
                '}';
    }
}
