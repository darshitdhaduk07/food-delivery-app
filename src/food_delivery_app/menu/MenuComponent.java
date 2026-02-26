package food_delivery_app.menu;

abstract public class MenuComponent {

    protected String name;
    protected int id;

    private static int counter = 0;

    public MenuComponent(String name) {
        this.name = name;
        this.id = ++counter;
    }

    public int getId() {
        return id;
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

    public abstract void displayTable();
}