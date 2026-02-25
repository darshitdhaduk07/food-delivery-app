package food_delivery_app.cart;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> Cartitems;

    public Cart() {
        this.Cartitems = new ArrayList<>();
    }

    public void addCartItem(CartItem item) {
        Cartitems.add(item);
    }

    public CartItem getCartItemById(int id) {
        for (CartItem item : Cartitems) {
            if (item.getItem().getId() == id)
                return item;
        }
        System.out.println("Item not Found in Cart.");
        return null;
    }

    public void removeItemById(int id) {
        Cartitems.removeIf(cartItem ->
                cartItem.getItem().getId() == id
        );
    }

    public void displayCart() {

        if (Cartitems.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        for (CartItem ci : Cartitems) {
            System.out.println(
                    "Id :" + ci.getItem().getId() +
                            "Name :" + ci.getItem().getName() +
                            "Quantity :" + ci.getQuantity() +
                            "TotalPrice = ₹" + ci.getTotalPrice()
            );
        }
    }

    public void clearCart() {
        Cartitems.clear();
    }

    public void increaseQuantity(int id, int quantity) {
        CartItem item = getCartItemById(id);

        if (item != null) {
            item.increaseQuantity(quantity);
            return;
        }

        System.out.println("Item not found in cart.");

    }

    public void decreaseQuantity(int id, int quantity) {
        CartItem item = getCartItemById(id);

        if (item != null) {
            item.decreaseQuantity(quantity);
            return;
        }
        System.out.println("Item not found in cart.");

    }
}
