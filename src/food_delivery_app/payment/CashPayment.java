package food_delivery_app.payment;

public class CashPayment implements IPaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Payment of ₹" + amount + " received in Cash.");
        return true;
    }
}
