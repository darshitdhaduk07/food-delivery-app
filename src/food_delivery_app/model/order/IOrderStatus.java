package food_delivery_app.model.order;

public enum IOrderStatus {

    CREATED,
    PAYMENT_PENDING,
    CONFIRMED,
    PREPARING_YOUR_ORDER,
    ON_THE_WAY,
    DELIVERED,
    CANCELLED;

    public IOrderStatus next() {

        return switch (this) {

            case CREATED -> PAYMENT_PENDING;
            case PAYMENT_PENDING -> CONFIRMED;
            case CONFIRMED -> PREPARING_YOUR_ORDER;
            case PREPARING_YOUR_ORDER -> ON_THE_WAY;
            case ON_THE_WAY -> DELIVERED;

            case DELIVERED, CANCELLED -> this;
        };

    }
}