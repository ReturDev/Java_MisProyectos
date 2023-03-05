package com.sergio.main.controller.windows.user.data;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


import com.sergio.main.controller.menu.MenuController;
import com.sergio.main.controller.windows.user.ItemsUserRootController;
import com.sergio.main.model.datasource.enums.ItemsType;
import com.sergio.main.model.datasource.user.User;
import com.sergio.main.model.datasource.user.UserState;
import com.sergio.main.model.util.StylesConstants;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class UserController implements Initializable {

	@FXML
	private VBox rootUserView;

	@FXML
    private Circle userImageFrame;

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
    private ScrollPane rootItemsUser;
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		tabRoot.getSelectionModel().selectedItemProperty().addListener(changeTabEvent());

		try {

			FXMLLoader loaderItemsRoot = new FXMLLoader(getClass().getResource("/com/sergio/main/view/windows/user/items/itemsUserRootView.fxml"));
			loaderItemsRoot.setController(ItemsUserRootController.getInstance());
			rootItemsUser = loaderItemsRoot.load();
            VBox.setVgrow(rootItemsUser, Priority.ALWAYS);
			FXMLLoader loaderStatus = new FXMLLoader(getClass().getResource("/com/sergio/main/view/windows/user/data/statusItemsMenuUserView.fxml"));
			loaderStatus.setController(StatusItemsMenuUserController.getInstance());
			rootStatusItemsMenu = loaderStatus.load();

			onAnimeTab();

		} catch (IOException e) {

			e.printStackTrace();

		}

		User userData = UserState.getUserLoggedData();
		String username = userData.getUsername().toUpperCase(Locale.ROOT).charAt(0) + userData.getUsername().substring(1).toLowerCase();
		lblUserName.setText(username);

		String dir;
		File userImgDir = new File(userData.getImage());
		if (userImgDir.exists()){

			dir = userImgDir.toURI().toString();

		}else{

			dir = "/images/user-image-empty.png";

		}

		Image image = new Image(dir);

		userImageFrame.setFill(new ImagePattern(image));


		tabRoot.widthProperty().addListener((observableValue, oldNumber, newNumber) -> {

			if (!oldNumber.equals(newNumber)){

				double size = tabRoot.getWidth()/2-30;
				tabRoot.setTabMaxWidth(size);
				tabRoot.setTabMinWidth(size);

			}

		});

		Platform.runLater(()-> rootStatusItemsMenu.requestFocus());

	}

    @FXML
    void onLogout() {

		UserState.userLogOut();
		MenuController.getInstance().goToUserConfig();
    	
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

    private void onLoadTab(ItemsType tag) {

		ItemsUserRootController.getInstance().clearItemsRoot();
		ItemsUserRootController.getInstance().setShownItemType(tag);
		StatusItemsMenuUserController.getInstance().getBtnFavourites().fire();

    }
    
    private void onAnimeTab() {

    	onLoadTab(ItemsType.ANIME);
    	tabAnime.getStyleClass().add(StylesConstants.TAB_SELECTED);
    	tabManga.getStyleClass().remove(StylesConstants.TAB_SELECTED);
    	rootTabAnime.getChildren().add(rootStatusItemsMenu);
    	rootTabAnime.getChildren().add(rootItemsUser);
    	
    }
    
    private void onMangaTab() {

		tabManga.getStyleClass().add(StylesConstants.TAB_SELECTED);
		tabAnime.getStyleClass().remove(StylesConstants.TAB_SELECTED);
    	onLoadTab(ItemsType.MANGA);
    	rootTabManga.getChildren().add(rootStatusItemsMenu);
    	rootTabManga.getChildren().add(rootItemsUser);
    	
    }
	
}
