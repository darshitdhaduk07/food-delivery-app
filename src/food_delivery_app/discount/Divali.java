package food_delivery_app.discount;

public class Divali extends DiscountStrategy{
    public Divali(int percentage)
    {
        discountPercentage = percentage;
    }

    @Override
    public double applyDiscount(double amount) {
        return amount - (amount*discountPercentage/100);
    }
}

