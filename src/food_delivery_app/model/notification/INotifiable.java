package food_delivery_app.model.notification;


public interface INotifiable {

    void addNotification(Notification n);
    int getId();
}