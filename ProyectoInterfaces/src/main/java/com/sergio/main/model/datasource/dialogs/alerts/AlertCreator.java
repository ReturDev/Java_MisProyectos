package com.sergio.main.model.datasource.dialogs.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.util.Optional;

public class AlertCreator {

    private AlertCreator(){}

    public static Optional<ButtonType> createAndShowAlert(Alert.AlertType type, String title, String text){

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.initStyle(StageStyle.UNDECORATED);

        return alert.showAndWait();

    }

}