package food_delivery_app.payment;

public class PaymentProcessor {

    public boolean process(PaymentMethod method, double amount) {

        PaymentStrategy strategy =
                PaymentFactory.getStrategy(method);

        return strategy.pay(amount);
    }
}
