package food_delivery_app.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cartItems;

    public Cart() {
        this.cartItems = new ArrayList<>();
    }

    public void addCartItem(CartItem item) {
        cartItems.add(item);
    }

    public CartItem getCartItemById(int id) {
        for (CartItem item : cartItems) {
            if (item.getItem().getId() == id)
                return item;
        }
        return null;
    }

    public void removeItemById(int id) {
        cartItems.removeIf(cartItem ->
                cartItem.getItem().getId() == id
        );
    }

    public void displayCart() {

        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.printf("%-5s %-15s %-10s %-10s%n",
                "ID", "NAME", "QTY", "PRICE");
        System.out.println("-------------------------------------------");

        for (CartItem ci : cartItems) {
            System.out.printf("%-5d %-15s %-10d ₹%-10.2f%n",
                    ci.getItem().getId(),
                    ci.getItem().getName(),
                    ci.getQuantity(),
                    ci.getTotalPrice());
        }
    }

    public void clearCart() {
        cartItems.clear();
    }

    public void updateQuantity(int id, int quantity) {
        CartItem item = getCartItemById(id);

        if (item != null) {
            item.setQuantity(quantity);
            return;
        }
        System.out.println("Item not found in cart.");

    }
    public List<CartItem> getItems() {
        return cartItems;
    }
}
