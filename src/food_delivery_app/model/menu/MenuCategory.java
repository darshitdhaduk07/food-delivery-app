package food_delivery_app.model.menu;

import java.util.*;

public class MenuCategory extends MenuComponent{
    LinkedHashMap<String, MenuComponent> components;
    private boolean available = true;
    public MenuCategory(String name) {
        super(name);
        components = new LinkedHashMap<>();
    }
    public boolean isAvailable() {
        return available;
    }

    public void setAvailability(boolean status) {
        this.available = status;
    }

    @Override
    public void add(MenuComponent component) {

        String key =
                component.getName().toLowerCase();

        if (components.containsKey(key)) {
            System.out.println("Duplicate not allowed.");
            return;
        }

        components.put(key, component);
        System.out.println("Category added");
    }

    @Override
    public void remove(MenuComponent component) {
        components.remove(component);
    }

    public List<MenuComponent> getComponents() {
        return components.values().stream().toList();
    }
    public MenuComponent findMenuComponentById(int id) {

        if (this.id == id)
            return this;

        for (MenuComponent comp : components.values()) {

            if (comp.getId() == id)
                return comp;

            if (comp instanceof MenuCategory cat) {
                MenuComponent found = cat.findMenuComponentById(id);
                if (found != null)
                    return found;
            }
        }

        return null;
    }

    @Override
    public void displayTable() {
        if(!available)
            return;
        System.out.println();
        System.out.println("--------------- [" + id + "] "
                + name.toUpperCase()
                + " ----------------");

        for (MenuComponent component : components.values()) {
            component.displayTable();
        }
    }
    @Override
    public void displayAllTable() {
        System.out.println();
        System.out.print("--------------- [" + id + "] "
                + name.toUpperCase()
                + " ----------------");
        if(!available)
            System.out.print("  N/A");
        System.out.println();
        for (MenuComponent component : components.values()) {
            component.displayAllTable();
        }

    }
}
