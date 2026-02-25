package food_delivery_app.discount;

abstract public class DiscountStrategy {
    protected int discountPercentage;

    public abstract double applyDiscount(double amount);
}
