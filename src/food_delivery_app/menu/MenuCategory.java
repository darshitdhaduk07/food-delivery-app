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
    public MenuComponent findItemById(int id)
    {
        for(MenuComponent menuComponent : components)
        {
            if(menuComponent instanceof MenuItem item)
            {
                if(item.getId() == id)
                {
                    return menuComponent;
                }
            }
        }
        return null;
    }
    @Override
    public void display(int level) {
        System.out.println("   ".repeat(level)+ name);
        for (MenuComponent component : components) {
            component.display(level + 1);
        }
    }
}
