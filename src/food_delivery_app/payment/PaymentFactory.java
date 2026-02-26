package food_delivery_app.payment;

public class PaymentFactory {

    public static PaymentStrategy getStrategy(PaymentMethod method) {

        return switch (method) {
            case UPI -> new UpiPayment();
            case CASH -> new CashPayment();
        };
    }
}