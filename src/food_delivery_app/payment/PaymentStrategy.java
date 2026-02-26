package food_delivery_app.payment;

public interface PaymentStrategy {
    boolean pay(double amount);
}
