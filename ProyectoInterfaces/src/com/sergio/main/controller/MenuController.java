package com.sergio.main.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class MenuController implements Initializable{

	
	@FXML
	private Pane menuRoot;
	@FXML
	private Button btnMenu;
	@FXML
	private Button btnAnimeMenu;
	@FXML
	private Button btnMangaMenu;
	@FXML
	private Button btnUserConfig;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		

		
	}
	
	@FXML
	private void openMenu() {
		
	}
	
	@FXML
	private void goToAnime() {
		
		try {
			
			ScrollPane animePane = (ScrollPane) FXMLLoader.load(getClass().getResource("../view/ItemsRootView.fxml"));
			
			
			Pane parent = (Pane) menuRoot.getParent();
			HBox.setHgrow(animePane, Priority.ALWAYS);
			parent.getChildren().set(1, animePane);
			
			
		}catch (IOException e) {

			e.printStackTrace();
			
		}
	}
	
	@FXML
	private void goToManga() {
		
	}
	
	@FXML
	private void goToUserConfig() {
		
	}
	
	

}
