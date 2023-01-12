package com.sergio.main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ItemsRootController implements Initializable {

	
	@FXML
	private FlowPane itemsRoot;
	@FXML
	private Button btnPreviousPage;
	@FXML
	private Button btnNextPage;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			
			
			
			for(int i = 0; i < 30; i++) {
				
				
				Pane itemPane = (Pane) FXMLLoader.load(getClass().getResource("../view/ItemView.fxml"));
				itemsRoot.getChildren().add(itemPane);
				
				
			}
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		
	}
	
	
	@FXML
	private void onPreviousPage() {}
	
	
	@FXML
	private void onNextPage() {}
	

}
