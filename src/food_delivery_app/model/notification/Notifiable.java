package food_delivery_app.model.notification;


public interface Notifiable {

    void addNotification(Notification n);
    int getId();
}