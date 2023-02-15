package com.sergio.main.model.datasource.notifications;

import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationCreator {

    public static void createNotification(Object owner,String title, String text, long seconds){

        Notifications notification = Notifications.create();
        notification.title(title);
        notification.text(text);
        notification.hideAfter(Duration.seconds(seconds));
        notification.darkStyle();
        notification.owner(owner);
        notification.show();

    }


}
