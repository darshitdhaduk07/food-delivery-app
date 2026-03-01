package food_delivery_app.payment;

public class UpiIPayment implements IPaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Paid via UPI: ₹" + amount);
        return true;
    }
}
