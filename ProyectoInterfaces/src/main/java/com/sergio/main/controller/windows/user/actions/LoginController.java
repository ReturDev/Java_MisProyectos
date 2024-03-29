package com.sergio.main.controller.windows.user.actions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sergio.main.controller.menu.MenuController;
import com.sergio.main.model.datasource.dialogs.notifications.NotificationCreator;
import com.sergio.main.model.datasource.dialogs.notifications.NotificationType;
import com.sergio.main.model.datasource.user.User;
import com.sergio.main.model.datasource.user.UserState;
import com.sergio.main.model.repository.database.dao.UserDAOImpl;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Controlador de la vista de inicio de sesión.
 */
public class LoginController{

    @FXML
    private VBox root;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblSignUp;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfUser;

    private static LoginController instance;

    private LoginController(){}

    /**
     * Devuelve la instancia de esta clase, si no existe la crea antes.
     * @return instancia de LoginController.
     */
    public static LoginController getInstance(){

        if (instance == null){

            instance = new LoginController();

        }

        return instance;

    }


    /**
     * Método que inicia la sesión del usuario si cuenta con los datos correctos.
     */
    @FXML
    private void toLogin() {

        UserDAOImpl userDAO = new UserDAOImpl();

        if (tfUser.getText().isBlank() || tfPassword.getText().isBlank()){

            NotificationCreator.createAndShowNotification(root.getParent(), "Campo vacío", "Debes rellenar ambos campos.", 2, true, NotificationType.INFORMATION, Pos.TOP_CENTER);

        }else{

            if (!userDAO.checkUsernameRegistered(tfUser.getText())){

                NotificationCreator.createAndShowNotification(root.getParent(), "Usuario invalido", "No existe ningún usuario con ese nombre.", 2, true, NotificationType.ERROR, Pos.TOP_CENTER);

            } else if (!userDAO.checkUserRegistered(tfUser.getText(), tfPassword.getText())){

                NotificationCreator.createAndShowNotification(root.getParent(), "Contraseña incorrecta", "La contraseña introducida es incorrecta.", 2, true, NotificationType.ERROR, Pos.TOP_CENTER);

            }else {

                userLoggedIn(userDAO.getUserByUsername(tfUser.getText()));

            }

        }

    }


    /**
     * Evento que se produce al pulsar el botón para ir a la ventana de registro.
     * @throws IOException
     */
    @FXML
    private void onSignUp() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sergio/main/view/windows/user/actions/signUpView.fxml"));
        Pane signUpRoot = loader.load();
        HBox.setHgrow(signUpRoot, Priority.ALWAYS);
        Pane parent = (Pane) root.getParent();
        parent.getChildren().set(1,signUpRoot);

    }


    /**
     * Inicia la sesión del usuario.
     * @param user Usuario que iniciará sesión.
     */
	public void userLoggedIn(User user){

        UserState.userLogIn(user);
        MenuController.getInstance().goToUserConfig();

    }

    /**
     * Establece el focus en el campo de nombre de usuario.
     */
    public void setDefaultFocus(){

        tfUser.requestFocus();

    }

    /**
     * Evento al pulsar enter en el último campo de login.
     * @param event Tecla que activa el evento.
     */
    @FXML
    private void onPressEnter(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {

            btnLogin.fire();

        }

    }

}
