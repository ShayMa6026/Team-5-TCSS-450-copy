package edu.uw.tcss450.chatphile.ui.home;

/**
 * View model for notifications
 * @author Edwin
 */
public class NotificationModel {

    String name;
    String message;

    public NotificationModel(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public NotificationModel setName(String name) {
        this.name = name;
        return this;
    }

    public NotificationModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
