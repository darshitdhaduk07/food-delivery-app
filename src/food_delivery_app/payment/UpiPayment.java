package food_delivery_app.payment;

public class UpiPayment implements PaymentStrategy{
    @Override
    public boolean pay(double amount) {
        System.out.println("Paid via UPI: ₹" + amount);
        return true;
    }
}
