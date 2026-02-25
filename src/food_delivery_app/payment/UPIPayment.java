package food_delivery_app.payment;

public class UPIPayment implements PaymentStrategy{
    private String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Payment of ₹" + amount + " done via UPI: " + upiId);
        return true;
    }
}
