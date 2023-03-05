package com.sergio.main.controller.menu;


import java.io.IOException;

import com.sergio.main.controller.windows.items.ItemsCatalogueRootController;
import com.sergio.main.controller.windows.user.actions.LoginController;
import com.sergio.main.model.datasource.enums.ItemsType;
import com.sergio.main.model.datasource.user.UserState;

import com.sergio.main.model.util.StylesConstants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class MenuController{
	
	@FXML
	private Pane menuRoot;
	@FXML
	private HBox rootBtnMenu;
	@FXML
	private Button btnMenu;
	@FXML
	private Button btnAnimeMenu;
	@FXML
	private Button btnMangaMenu;
	@FXML
	private Button btnUserConfig;
	
	private boolean menuOpened;

	private static MenuController instance;

	public static MenuController getInstance() {

		if (instance == null){

			instance = new MenuController();

		}

		return instance;

	}

	private MenuController(){}

	@FXML
	private void openMenu() {
		
		if(menuOpened) {
			
			menuOpened = false;
			
			menuRoot.setPrefWidth(Region.USE_COMPUTED_SIZE);
			btnMenu.setMaxWidth(Double.MAX_VALUE);
			btnAnimeMenu.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			btnMangaMenu.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			btnUserConfig.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			btnAnimeMenu.setAlignment(Pos.CENTER);
			btnMangaMenu.setAlignment(Pos.CENTER);
			btnUserConfig.setAlignment(Pos.CENTER);
			
			
			
		}else {
			
			menuOpened = true;
			
			menuRoot.setPrefWidth(200);
			btnMenu.setMaxWidth(Region.USE_COMPUTED_SIZE);
			btnAnimeMenu.setContentDisplay(ContentDisplay.LEFT);
			btnMangaMenu.setContentDisplay(ContentDisplay.LEFT);
			btnUserConfig.setContentDisplay(ContentDisplay.LEFT);
			btnAnimeMenu.setAlignment(Pos.CENTER_LEFT);
			btnMangaMenu.setAlignment(Pos.CENTER_LEFT);
			btnUserConfig.setAlignment(Pos.CENTER_LEFT);
			
		}

	}
	
	@FXML
	public void goToAnime() throws IOException {
		

		ItemsCatalogueRootController controller = ItemsCatalogueRootController.getInstance();
		controller.setShownItemType(ItemsType.ANIME);
		goToElementView(controller);

		btnAnimeMenu.getStyleClass().add(StylesConstants.ITEM_MENU_SELECTED);
		
	}
	
	@FXML
	private void goToManga() throws IOException {
		

		ItemsCatalogueRootController controller = ItemsCatalogueRootController.getInstance();
		controller.setShownItemType(ItemsType.MANGA);
		goToElementView(controller);

		btnMangaMenu.getStyleClass().add(StylesConstants.ITEM_MENU_SELECTED);

	}
	
	@FXML
	public void goToUserConfig() {
		
		if (UserState.isUserLogged()) {
			
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sergio/main/view/windows/user/data/userView.fxml"));
				Pane userPane = loader.load();
				Pane parent = (Pane) menuRoot.getParent();
				HBox.setHgrow(userPane, Priority.ALWAYS);
				parent.getChildren().set(1, userPane);

			}catch(IOException e) {
				
				e.printStackTrace();
				
			}
			
			
		}else {
			
			try {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sergio/main/view/windows/user/actions/loginView.fxml"));
				LoginController controller = LoginController.getInstance();
				loader.setController(controller);
				Pane loginPane = loader.load();
				Pane parent = (Pane) menuRoot.getParent();
				HBox.setHgrow(loginPane, Priority.ALWAYS);
				parent.getChildren().set(1, loginPane);
				controller.setDefaultFocus();
				
				
			}catch(IOException e) {
			
				e.printStackTrace();
				
			}
			
		}

		removeStyleClass();
		btnUserConfig.getStyleClass().add(StylesConstants.ITEM_MENU_SELECTED);

	}
	
	private void goToElementView(ItemsCatalogueRootController controller) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sergio/main/view/windows/items/itemsCatalogueRootView.fxml"));
		loader.setController(ItemsCatalogueRootController.getInstance());
		ScrollPane itemsPane = loader.load();
		Pane parent = (Pane) menuRoot.getParent();
		HBox.setHgrow(itemsPane, Priority.ALWAYS);
		parent.getChildren().set(1, itemsPane);

		controller.resetButtons();
		controller.loadData();
		removeStyleClass();

	}
	
	private void removeStyleClass(){

		btnAnimeMenu.getStyleClass().remove(StylesConstants.ITEM_MENU_SELECTED);
		btnMangaMenu.getStyleClass().remove(StylesConstants.ITEM_MENU_SELECTED);
		btnUserConfig.getStyleClass().remove(StylesConstants.ITEM_MENU_SELECTED);

	}

}
