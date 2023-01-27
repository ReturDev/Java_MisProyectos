package com.sergio.main.controller.user;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class StatusItemsMenuUserController implements Initializable {
	
	
	@FXML
	private Button btnFavourites;
	
	@FXML
	private Button btnFollowing;
	
	@FXML
	private Button btnPending;
	
	@FXML
	private Button btnFinished;
	
	@FXML
	private Button btnAbandoned;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	
	
	@FXML
	private void onFavourites() {
		
		System.out.println("Favoritos Usuario");
		
	}
	
	@FXML
	private void onFollowing() {
		
		System.out.println("Siguiendo Usuario");
		
	}
	
	@FXML
	private void onPending() {
		
		System.out.println("Pendiente Usuario");
		
	}
	
	@FXML
	private void onFinished() {
		
		System.out.println("Finalizado Usuario");
		
	}
	
	@FXML
	private void onAbandoned() {
		
		System.out.println("Abandonado Usuario");
		
	}


	
	
	
	

}
