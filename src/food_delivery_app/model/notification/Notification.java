package food_delivery_app.model.notification;

import java.time.LocalDateTime;

public class Notification {

    private final String message;
    private final LocalDateTime time;

    public Notification(String message) {
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "[" + time + "] " + message;
    }
}