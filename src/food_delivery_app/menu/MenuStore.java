package food_delivery_app.menu;

public class MenuStore {
    private static final MenuCategory MAIN_MENU = new MenuCategory("Our Menu");

    private MenuStore() {}

    public static MenuCategory getMenu() {
        return MAIN_MENU;
    }

}
