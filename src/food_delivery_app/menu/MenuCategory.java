package food_delivery_app.menu;

import java.util.*;

public class MenuCategory extends MenuComponent{
    LinkedHashMap<String, MenuComponent> components;
    public MenuCategory(String name) {
        super(name);
        components = new LinkedHashMap<>();
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
        System.out.println("Item added");
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

        System.out.println();
        System.out.println("----------- [" + id + "] "
                + name.toUpperCase()
                + " -----------");

        for (MenuComponent component : components.values()) {
            component.displayTable();
        }
    }
}
