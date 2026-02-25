package food_delivery_app.menu;

abstract public class MenuComponent {
    String name;

    public MenuComponent(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void add(MenuComponent component) {
        throw new UnsupportedOperationException();
    }

    public void remove(MenuComponent component) {
        throw new UnsupportedOperationException();
    }
    public abstract void display(int level);

}
