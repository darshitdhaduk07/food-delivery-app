package food_delivery_app.services;

import food_delivery_app.cart.Cart;
import food_delivery_app.cart.CartItem;
import food_delivery_app.menu.MenuComponent;
import food_delivery_app.menu.MenuStore;
import food_delivery_app.model.Customer;

import java.util.Optional;

public class CartService {
    private final MenuStore menuStore;

    public CartService(MenuStore menuStore) {
        this.menuStore = menuStore;
    }

    public void addItem(Customer customer, int menuItemId, int quantity) {
        MenuComponent item = menuStore.findById(menuItemId);
        if (item == null) {
            System.out.println("Menu item not found!");
            return;
        }

        Cart cart = customer.getCart();
        Optional<CartItem> existing = cart.getItems().stream()
                .filter(ci -> ci.getMenuItem().getId() == menuItemId)
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + quantity);
        } else {
            cart.getItems().add(new CartItem(item, quantity));
        }

        System.out.println(quantity + " x " + item.getName() + " added to cart.");
    }

}
