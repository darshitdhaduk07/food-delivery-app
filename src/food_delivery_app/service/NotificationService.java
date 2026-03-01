package food_delivery_app.service;

import food_delivery_app.model.notification.INotifiable;
import food_delivery_app.model.notification.Notification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationService {

    private final ExecutorService executor;

    public NotificationService() {

        // fixed thread pool (2 worker threads)
        executor = Executors.newFixedThreadPool(2);
    }

    public void sendNotification(INotifiable user,
                                 String message) {

        executor.submit(() ->
                user.addNotification(
                        new Notification(message)
                )
        );
    }

    // shutdown when app exits
    public void shutdown() {
        executor.shutdown();
    }
}