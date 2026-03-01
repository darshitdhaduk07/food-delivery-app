package food_delivery_app.model.discount;

public class Discount {
    private String name;
    private double discountRate;
    private double minAmount;
    private static Discount discount;
    private Discount() {
        this.name = "No Discount";
        this.discountRate = 0;
        this.minAmount = 0;
    }
    public static Discount getInstance()
    {
        if(discount == null)
        {
            discount = new Discount();
        }
        return discount;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }
}
