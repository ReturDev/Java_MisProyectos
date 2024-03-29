package com.sergio.main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sergio.main.controller.menu.MenuController;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;


/**
 * Controlador de la ventana principal donde se añadiras las diferentes ventanas.
 */
public class MainController implements Initializable{

	@FXML
	private HBox hbRoot;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {

			MenuController controller = MenuController.getInstance();

			FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/sergio/main/view/menu/menuView.fxml"));
			menuLoader.setController(controller);
			hbRoot.getChildren().add(0, menuLoader.load());
			controller.goToAnime();
			Platform.runLater(() -> hbRoot.requestFocus());


		} catch (IOException e) {

			e.printStackTrace();

		}

	}
	
	
	
}
