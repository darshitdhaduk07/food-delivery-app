package food_delivery_app.discount;

public class NewYear extends DiscountStrategy{
    public NewYear(int percentage)
    {
        discountPercentage = percentage;
    }

    @Override
    public double applyDiscount(double amount) {
        if(amount > 500)
            return amount - (amount*discountPercentage/100);
        return amount;
    }
}
