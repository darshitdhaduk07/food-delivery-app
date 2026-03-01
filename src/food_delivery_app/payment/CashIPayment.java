package food_delivery_app.payment;

public class CashIPayment implements IPaymentStrategy {

    @Override
    public boolean pay(double amount) {
        System.out.println("Paid via Cash: ₹" + amount);
        return true;
    }
}
