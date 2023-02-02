package com.sergio.main.controller.windows.user.actions;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController implements Initializable {
	
    @FXML
    private Button btnLogin;

    @FXML
    private Label lblSignUp;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfUser;

    @FXML
    void toLogin(ActionEvent event) {

    }
    
    @FXML
    void onSignUp(MouseEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
