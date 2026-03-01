package food_delivery_app.payment;

public class PaymentProcessor {

    public boolean process(PaymentMethod method, double amount) {

        IPaymentStrategy strategy =
                PaymentFactory.getStrategy(method);

        return strategy.pay(amount);
    }
}
