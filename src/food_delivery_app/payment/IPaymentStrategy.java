package food_delivery_app.payment;

public interface IPaymentStrategy {
    boolean pay(double amount);
}