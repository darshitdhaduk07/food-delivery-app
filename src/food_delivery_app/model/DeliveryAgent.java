package food_delivery_app.model;

public class DeliveryAgent extends User{
    private static int counter = 0;
    private boolean available;

    public DeliveryAgent(String name, String email, int password) {
        super(name, email, password);
        this.id = ++counter;

        this.available = true; // default available
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


    @Override
    public String toString() {

        return String.format(
                "%-5d %-15s %-12s",
                id,
                getName(),
                available ? "AVAILABLE" : "BUSY"
        );
    }
}
