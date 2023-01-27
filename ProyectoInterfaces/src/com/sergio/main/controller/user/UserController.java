package com.sergio.main.controller.user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sergio.main.model.ElementsTags;
import com.sergio.main.model.savers.UserDataSaver;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class UserController implements Initializable {
	
	@FXML
    private ImageView ivUserImage;

    @FXML
    private Label lblUserName;

    @FXML
    private Tab tabAnime;

    @FXML
    private Tab tabManga;
    
    @FXML
    private VBox rootTabAnime;

    @FXML
    private VBox rootTabManga;

    @FXML
    private TabPane tabRoot;
    
    private GridPane rootStatusItemsMenu;
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		tabRoot.getSelectionModel().selectedItemProperty().addListener(changeTabEvent());
		
		try {
			
			rootStatusItemsMenu = (GridPane) FXMLLoader.load(getClass().getResource("/com/sergio/main/view/user/StatusItemsMenuUserView.fxml"));

			onAnimeTab();
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

    @FXML
    void onLogout(ActionEvent event) {

    	Button btn = (Button) event.getSource();
    	Pane appRoot = (Pane) btn.getScene().getRoot();
    	Button btnToUserView = (Button) appRoot.lookup("#btnUserConfig");
    	
    	
    	UserDataSaver.setUserLoged(false);
    	btnToUserView.fire();
    	
    }
    

    private ChangeListener<Tab> changeTabEvent() {
    	
    	return (ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) -> {
				
				if (!oldTab.equals(newTab)) {
					
					if (newTab.equals(tabAnime)) {
						
						onAnimeTab();
						
					}else if (newTab.equals(tabManga)) {
						
						onMangaTab();
						
					}
				}

		};
		
    }
    
    private void onLoadTab(ElementsTags tag) {
    	
    	Button btnFav = (Button)rootStatusItemsMenu.lookup("#btnFavourites");
    	btnFav.fire();
    	
    }
    
    private void onAnimeTab() {
    	

		System.out.println("Anime");
    	onLoadTab(ElementsTags.ANIME);
    	rootTabAnime.getChildren().add(rootStatusItemsMenu);
    	
    }
    
    private void onMangaTab() {
    	

		System.out.println("MAnga");
    	onLoadTab(ElementsTags.MANGA);
    	rootTabManga.getChildren().add(rootStatusItemsMenu);
    	
    }

	
    
    
	
}
