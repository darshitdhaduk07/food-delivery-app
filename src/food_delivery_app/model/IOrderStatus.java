package food_delivery_app.model;

public enum IOrderStatus {

    CREATED,
    PAYMENT_PENDING,
    CONFIRMED,
    ON_THE_WAY,
    DELIVERED,
    CANCELLED;

    public IOrderStatus next() {

        return switch (this) {

            case CREATED -> PAYMENT_PENDING;
            case PAYMENT_PENDING -> CONFIRMED;
            case CONFIRMED -> ON_THE_WAY;
            case ON_THE_WAY -> DELIVERED;

            case DELIVERED, CANCELLED -> this;
        };
    }
}