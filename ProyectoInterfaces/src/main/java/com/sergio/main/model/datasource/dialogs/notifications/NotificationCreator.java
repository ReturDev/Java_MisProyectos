package com.sergio.main.model.datasource.dialogs.notifications;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationCreator {

    /**
     *
     * @param owner
     * @param title
     * @param text
     * @param seconds
     * @param darkTheme
     * @param type
     */
    public static void createAndShowNotification(Object owner, String title, String text, long seconds, boolean darkTheme, NotificationType type){

        Notifications notification = createBaseNotification(owner, title, text, seconds, darkTheme);

        showNotificaton(notification, type);

    }


    /**
     *
     * @param owner
     * @param title
     * @param text
     * @param seconds
     * @param darkTheme
     * @param type
     * @param position
     */
    public static void createAndShowNotification(Object owner, String title, String text, long seconds, boolean darkTheme, NotificationType type, Pos position){

        Notifications notification = createBaseNotification(owner, title, text, seconds, darkTheme);
        notification.position(position);
        showNotificaton(notification, type);

    }

    /**
     *
     * @param owner
     * @param title
     * @param text
     * @param seconds
     * @param darkTheme
     * @return
     */
    private static Notifications createBaseNotification(Object owner, String title, String text, long seconds, boolean darkTheme){

        Notifications notification = Notifications.create();
        notification.title(title);
        notification.text(text);
        notification.hideAfter(Duration.seconds(seconds));
        notification.owner(owner);

        if (darkTheme){

            notification.darkStyle();

        }

        return notification;

    }

    /**
     *
     * @param notification
     * @param type
     */
    private static void showNotificaton(Notifications notification, NotificationType type){

        if(type == null){

            notification.show();

        }else if (type.equals(NotificationType.CONFIRM)){

            notification.showConfirm();

        }else if(type.equals(NotificationType.ERROR)){

            notification.showError();

        }else if(type.equals(NotificationType.INFORMATION)){

            notification.showInformation();

        }else if(type.equals(NotificationType.WARNING)){

            notification.showWarning();

        }

    }


}
