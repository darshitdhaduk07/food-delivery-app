package food_delivery_app.menu;

public class MenuItem extends MenuComponent {

    private double price;
    private boolean available;

    public MenuItem(String name, double price) {
        super(name);
        this.price = price;
        this.available = true;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailability(boolean status) {
        this.available = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void display(int level) {

        if (available) {
            System.out.println(
                    "   ".repeat(level)
                            + "[" + id + "] "
                            + name + " : ₹" + price
            );
        }
    }
}