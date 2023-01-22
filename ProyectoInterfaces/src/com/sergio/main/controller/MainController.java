package com.sergio.main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController implements Initializable{

	@FXML
	private HBox hbRoot;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		try {
			
			
			AnchorPane menuRoot = (AnchorPane)FXMLLoader.load(getClass().getResource("/com/sergio/main/view/menu/MenuView.fxml"));
			hbRoot.getChildren().add(0, menuRoot);
			
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	
}
