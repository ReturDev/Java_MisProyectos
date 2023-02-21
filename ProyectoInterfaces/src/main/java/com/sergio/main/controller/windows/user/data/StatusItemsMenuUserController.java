package com.sergio.main.controller.windows.user.data;

import com.sergio.main.controller.windows.user.ItemsUserRootController;
import com.sergio.main.model.datasource.enums.ActionSelected;
import com.sergio.main.model.datasource.enums.ItemsType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class StatusItemsMenuUserController{
	

	@FXML
	private GridPane gridRoot;

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

	private static StatusItemsMenuUserController instance;

	private ActionSelected actionSelected;
	
	private StatusItemsMenuUserController(){}

	public static StatusItemsMenuUserController getInstance() {

		if (instance == null){

			instance = new StatusItemsMenuUserController();

		}

		return instance;

	}

	
	@FXML
	private void onFavourites() {

		ItemsUserRootController.getInstance().resetButtons();
		actionSelected = ActionSelected.FAVOURITE;
		ItemsUserRootController.getInstance().loadData();
		
	}
	
	@FXML
	private void onFollowing() {

		ItemsUserRootController.getInstance().resetButtons();
		actionSelected = ActionSelected.FOLLOWING;
		ItemsUserRootController.getInstance().loadData();
		
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

	private void insertItemsRoot(){



	}



	public Button getBtnFavourites() {
		return btnFavourites;
	}

	public ActionSelected getActionSelected() {
		return actionSelected;
	}
}
