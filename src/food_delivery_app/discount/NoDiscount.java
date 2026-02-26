package food_delivery_app.discount;

public class NoDiscount extends DiscountStrategy{
    public NoDiscount(int percentage)
    {
        discountPercentage = percentage;
    }

    @Override
    public double applyDiscount(double amount) {
        return 0;
    }
}
