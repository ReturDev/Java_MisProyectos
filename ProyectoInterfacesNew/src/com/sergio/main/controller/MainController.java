package com.sergio.main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sergio.main.controller.menu.MenuController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController implements Initializable{

	@FXML
	private HBox hbRoot;
	
	@FXML
	private MenuController menuRootController;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	
		try {
			
			menuRootController.goToAnime();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	
}
