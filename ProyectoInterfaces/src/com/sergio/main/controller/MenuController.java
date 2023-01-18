package com.sergio.main.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sergio.main.model.DataSaver;
import com.sergio.main.model.ElementsTags;
import com.sergio.main.model.UserDataSaver;

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
			DataSaver.setDataType(ElementsTags.ANIME);
			
		}catch (IOException e) {

			e.printStackTrace();
			
		}
		
	}
	
	@FXML
	private void goToManga() {
		
		try {
			
			ScrollPane mangaPane = (ScrollPane) FXMLLoader.load(getClass().getResource("../view/ItemsRootView.fxml"));
			
			
			Pane parent = (Pane) menuRoot.getParent();
			HBox.setHgrow(mangaPane, Priority.ALWAYS);
			parent.getChildren().set(1, mangaPane);
			DataSaver.setDataType(ElementsTags.MANGA);
			
			
		}catch (IOException e) {

			e.printStackTrace();
			
		}
		
	}
	
	@FXML
	private void goToUserConfig() {
		
		if (UserDataSaver.isUserLoged()) {
			
			try {
				
				Pane userPane = (Pane) FXMLLoader.load(getClass().getResource("../view/UserView.fxml"));
				
				Pane parent = (Pane) menuRoot.getParent();
				HBox.setHgrow(userPane, Priority.ALWAYS);
				parent.getChildren().set(1, userPane);
				
				
			}catch(IOException e) {
				
				e.printStackTrace();
				
			}
			
			
		}else {
			
			try {
				
				Pane loginPane = (Pane) FXMLLoader.load(getClass().getResource("../view/LoginView.fxml"));
				
				Pane parent = (Pane) menuRoot.getParent();
				HBox.setHgrow(loginPane, Priority.ALWAYS);
				parent.getChildren().set(1, loginPane);
				
				
			}catch(IOException e) {
				
				e.printStackTrace();
				
			}
			
			
		}
		
		
	}
	
	

}
