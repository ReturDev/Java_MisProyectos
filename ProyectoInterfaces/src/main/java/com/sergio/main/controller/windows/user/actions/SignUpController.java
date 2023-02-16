package com.sergio.main.controller.windows.user.actions;

import com.sergio.main.model.datasource.dialogs.alerts.AlertCreator;
import com.sergio.main.model.datasource.exceptions.FillFieldException;
import com.sergio.main.model.datasource.dialogs.notifications.NotificationCreator;
import com.sergio.main.model.datasource.dialogs.notifications.NotificationType;
import com.sergio.main.model.datasource.user.User;
import com.sergio.main.model.datasource.user.UserState;
import com.sergio.main.model.repositories.database.dao.UserDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;

public class SignUpController {

    @FXML
    private VBox root;

    @FXML
    private TextField tfConfirmPassword;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField imageDirText;

    @FXML
    private void addImage(ActionEvent event) {

    }

    @FXML
    private void signUpUser(){

        try {

            checkFields();
            if (toRegisterUser()){

                loginRegisteredUser(getUserData());

            }

        } catch (FillFieldException e) {

            NotificationCreator.createAndShowNotification(root, null, e.getMessage(), 2, true, NotificationType.ERROR);

        }


    }

    @FXML
    void onLogin() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sergio/main/view/windows/user/actions/loginView.fxml"));
        Pane loginRoot = loader.load();
        HBox.setHgrow(loginRoot, Priority.ALWAYS);
        Pane parent = (Pane) root.getParent();
        parent.getChildren().set(1,loginRoot);

    }

    private void checkFields() throws FillFieldException {

        if (tfUsername.getText().isBlank()){

            throw new FillFieldException("Debes introducir un nombre de usuario.");

        }

        if(tfEmail.getText().isBlank()){

            throw new FillFieldException("Debes introducir un email.");

        }

        if (tfPassword.getText().isBlank()){

            throw new FillFieldException("Debes introducir una contraseña.");

        }

        if (tfConfirmPassword.getText().isBlank()){

            throw new FillFieldException("Debes confirmar la contraseña.");

        }

        if (tfPassword.getText().equals(tfConfirmPassword.getText())){

            throw new FillFieldException("Ambas contraseñas deben de ser la misma.");

        }

    }

    private User getUserData(){

        User user = new User();
        user.setName(tfUsername.getText());
        user.setEmail(tfEmail.getText());
        user.setPassword(tfPassword.getText());
        user.setImage(imageDirText.getText());

        return user;

    }

    private  boolean toRegisterUser() throws FillFieldException {

        User user = getUserData();
        UserDAOImpl userDAO = new UserDAOImpl();

        if (userDAO.checkUsernameRegistered(user.getName())){

            throw new FillFieldException("Ya existe un usario con ese username.");

        }

        if (userDAO.checkEmailRegistered(user.getEmail())){

            throw new FillFieldException("Ya existe un usario con ese email.");

        }

        Optional<ButtonType> result = AlertCreator.createAndShowAlert(
                Alert.AlertType.CONFIRMATION,
                "Imagen de perfil",
                "La imagen de perfil seleccionada luego no podrña ser modificada. ¿Estas seguro?."
        );

        boolean registered = false;

        if (result.equals(ButtonType.OK)){

            registered = userDAO.registerUser(user);

        }

        return registered;

    }

    private void loginRegisteredUser(User user){

        UserState.userLogIn(user);
        LoginController.getInstance().userLoggedIn();

    }


}
