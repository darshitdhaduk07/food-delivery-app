package food_delivery_app.model.menu;

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
    public void displayTable() {

        if (!available) return;

        System.out.printf(
                "%-5d %-12s %-18s ₹%-10.2f%n",
                id,
                "ITEM",
                name,
                price
        );
    }
}