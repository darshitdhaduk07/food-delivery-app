package food_delivery_app.payment;

public class PaymentFactory {

    public static IPaymentStrategy getStrategy(PaymentMethod method) {

        return switch (method) {
            case UPI -> new UpiIPayment();
            case CASH -> new CashIPayment();
        };
    }
}