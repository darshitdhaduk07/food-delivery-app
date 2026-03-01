package food_delivery_app.service;

import food_delivery_app.model.cart.Cart;
import food_delivery_app.model.cart.CartItem;
import food_delivery_app.model.menu.MenuItem;
import food_delivery_app.model.order.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class CartService {

    public void addItem(Cart cart, MenuItem menuItem, int quantity) {

        if (!menuItem.isAvailable()) {
            System.out.println("Item not available.");
            return;
        }

        CartItem existing = cart.getCartItemById(menuItem.getId());


        if (existing != null) {
            int q = existing.getQuantity();
            existing.setQuantity(q+quantity);
        } else {
            cart.addCartItem(new CartItem(menuItem, quantity));
        }
        System.out.println("Item is Added");
    }
    public void removeItem(Cart cart, int itemId) {
        cart.removeItemById(itemId);
    }


    public void updateQuantity(Cart cart, int itemId, int quantity) {

        CartItem item = cart.getCartItemById(itemId);

        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        if (quantity <= 0) {
            cart.removeItemById(itemId);
            return;
        }

        item.setQuantity(quantity);

    }

    public double calculateTotal(Cart cart) {

        double total = 0;

        for (CartItem item : cart.getItems()) {
            total += item.getTotalPrice();
        }

        return total;
    }


    public boolean isCartEmpty(Cart cart) {
        return cart.getItems().isEmpty();
    }

    public void clearCart(Cart cart) {
        cart.clearCart();
    }


    public List<OrderItem> convertToOrderItems(Cart cart) {

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem ci : cart.getItems()) {

            orderItems.add(
                    new OrderItem(
                            ci.getItem().getName(),
                            ci.getQuantity(),
                            ci.getItem().getPrice()
                    )
            );
        }

        return orderItems;
    }

    public void showCart(Cart cart) {
        cart.displayCart();
        System.out.println("Total = ₹" + calculateTotal(cart));
    }
}
