package food_delivery_app.payment;

public class PaymentContext {
    private IPaymentStrategy paymentStrategy;

    public void setPaymentStrategy(IPaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean executePayment(double amount) {

        if (paymentStrategy == null) {
            System.out.println("No payment method selected.");
            return false;
        }

        return paymentStrategy.pay(amount);
    }
}
