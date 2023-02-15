package com.sergio.main.controller.windows.user.data;

import com.sergio.main.model.datasource.enums.ItemsType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StatusItemsMenuUserController{
	
	
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

	private ItemsType itemsType;
	
	private StatusItemsMenuUserController(){

		itemsType = ItemsType.ANIME;

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

	public static StatusItemsMenuUserController getInstance() {

		if (instance == null){

			instance = new StatusItemsMenuUserController();

		}

		return instance;

	}

	public ItemsType getItemsType() {
		return itemsType;
	}

	public void setItemsType(ItemsType itemsType) {
		this.itemsType = itemsType;
	}

	public Button getBtnFavourites() {
		return btnFavourites;
	}

	public Button getBtnFollowing() {
		return btnFollowing;
	}

	public Button getBtnPending() {
		return btnPending;
	}

	public Button getBtnFinished() {
		return btnFinished;
	}

	public Button getBtnAbandoned() {
		return btnAbandoned;
	}
}
