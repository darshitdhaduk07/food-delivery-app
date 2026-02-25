package food_delivery_app.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuCategory extends MenuComponent{
    private List<MenuComponent> components;

    public MenuCategory(String name) {
        super(name);
        this.components = new ArrayList<>();
    }

    @Override
    public void add(MenuComponent component) {
        components.add(component);
    }

    @Override
    public void remove(MenuComponent component) {
        components.remove(component);
    }

    public List<MenuComponent> getComponents() {
        return components;
    }
    public MenuComponent findById(int id) {

        if (this.id == id)
            return this;

        for (MenuComponent comp : components) {

            if (comp.getId() == id)
                return comp;

            if (comp instanceof MenuCategory cat) {
                MenuComponent found = cat.findById(id);
                if (found != null)
                    return found;
            }
        }

        return null;
    }

    @Override
    public void display(int level) {

        System.out.println(
                "   ".repeat(level)
                        + "[" + id + "] "
                        + name
        );

        for (MenuComponent component : components) {
            component.display(level + 1);
        }
    }
}
