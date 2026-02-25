package food_delivery_app.menu;

public class MenuItem extends MenuComponent{

    private double price;
    private boolean available;
    private int id;
    public static int counter = 0;

    public MenuItem(String name, double price) {
        super(name);
        this.price = price;
        this.available = true;
        this.id = ++counter;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailability(boolean status) {
        this.available = status;
    }

    @Override
    public void display(int level) {
        if (available) {
            System.out.println("   ".repeat(level)+ id + "- " + name + " : ₹" + price);
        }
    }
}
