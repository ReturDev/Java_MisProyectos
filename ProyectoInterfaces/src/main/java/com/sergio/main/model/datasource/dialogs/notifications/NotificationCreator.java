package com.sergio.main.model.datasource.dialogs.notifications;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * Creador de notificaciones.
 */
public class NotificationCreator {

    /**
     * Crea una notificación.
     * @param owner Vista que actuará como propietaria de la notificación.
     * @param title Título que mostrará la notificación, puede ser null.
     * @param text Contenido de texto que se mostrará en la notificación.
     * @param seconds Segundos que durará la notificación en pantalla.
     * @param darkTheme Habilitar o deshabilitar el tema oscuro.
     * @param type Tipo de notificación que se mostrará.
     */
    public static void createAndShowNotification(Object owner, String title, String text, long seconds, boolean darkTheme, NotificationType type){

        Notifications notification = createBaseNotification(owner, title, text, seconds, darkTheme);

        showNotificaton(notification, type);

    }


    /**
     * Crea una notificación.
     * @param owner Vista que actuará como propietaria de la notificación.
     * @param title Título que mostrará la notificación, puede ser null.
     * @param text Contenido de texto que se mostrará en la notificación.
     * @param seconds Segundos que durará la notificación en pantalla.
     * @param darkTheme Habilitar o deshabilitar el tema oscuro.
     * @param type Tipo de notificación que se mostrará.
     * @param position Posición en la que se mostrará la notificación.
     */
    public static void createAndShowNotification(Object owner, String title, String text, long seconds, boolean darkTheme, NotificationType type, Pos position){

        Notifications notification = createBaseNotification(owner, title, text, seconds, darkTheme);
        notification.position(position);
        showNotificaton(notification, type);

    }

    /**
     * Crea una notificación básica.
     * @param owner Vista que actuará como propietaria de la notificación.
     * @param title Título que mostrará la notificación, puede ser null.
     * @param text Contenido de texto que se mostrará en la notificación.
     * @param seconds Segundos que durará la notificación en pantalla.
     * @param darkTheme Habilitar o deshabilitar el tema oscuro.
     * @return Notificación base.
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
