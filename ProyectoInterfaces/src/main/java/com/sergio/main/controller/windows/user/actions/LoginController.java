package com.sergio.main.controller.windows.user.actions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sergio.main.controller.menu.MenuController;
import com.sergio.main.model.datasource.user.User;
import com.sergio.main.model.datasource.user.UserState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LoginController implements Initializable {

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

    public static LoginController getInstance(){

        if (instance == null){

            instance = new LoginController();

        }

        return instance;

    }

    @FXML
    private void toLogin(ActionEvent event) {

    }
    
    @FXML
    private void onSignUp(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sergio/main/view/windows/user/actions/signUpView.fxml"));
        Pane signUpRoot = loader.load();
        HBox.setHgrow(signUpRoot, Priority.ALWAYS);
        Pane parent = (Pane) root.getParent();
        parent.getChildren().set(1,signUpRoot);

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	public void userLoggedIn(User user){

        UserState.userLogIn(user);
        new MenuController().goToUserConfig();

    }
	
	
	

}
