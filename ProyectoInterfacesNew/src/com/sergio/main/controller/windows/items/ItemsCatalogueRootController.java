package com.sergio.main.controller.windows.items;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sergio.main.model.datasources.enums.ItemsType;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ItemsCatalogueRootController {

	
	@FXML
	private FlowPane itemsRoot;
	@FXML
	private TextField tfSearch;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnPreviousPage;
	@FXML
	private Button btnNextPage;
	
	
	private List<Pane> itemsBlueprints;
	
	private ItemsType  shownItemType;
	
	private static ItemsCatalogueRootController instance;
	
	private ItemsCatalogueRootController() {
		
		itemsBlueprints = new ArrayList<>();
		shownItemType = ItemsType.ANIME;
		loadItemBlueprints();
		
	}
	
	public static ItemsCatalogueRootController getInstance() {
		
		if(instance == null) {
			
			instance = new ItemsCatalogueRootController();
			
		}
		
		return instance;
		
	}
	
	
	@FXML
	private void onSearch() {
		
		//TODO Filtrar por el texto de busqueda y obtener todos los elementos con el nombre;
		System.out.println("Buscando");
		
	}
	
	
	/**
	 * Evento que volver� a una p�gina anteriror de elementos.
	 */
	@FXML
	private void onPreviousPage() {}
	
	
	/**
	 * Evento que avanzar� a una siguiente p�gina con elementos.
	 */
	@FXML
	private void onNextPage() {}
	
	
	/**
	 * Con el foco en el campo "Buscar", disparará un evento que activaá el botón al pulsar enter.
	 * @param event
	 */
	@FXML
	private void onPressEnter(KeyEvent event) {
		
		if (event.getCode() == KeyCode.ENTER) {
			
			btnSearch.fire();
			
		}
		
	}
	
	private void loadItemBlueprints() {
		
		try {
			
			if (itemsBlueprints.isEmpty()) {
				
				System.out.println("Carga de elementos");

				for(int i = 0; i < 30; i++) {
				
				
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sergio/main/view/windows/items/ItemBlueprintView.fxml"));
					loader.setController(new ItemBlueprintController());
					
					Pane itemPane = (Pane) loader.load();
					itemsBlueprints.add(itemPane);
					
				}
				
				System.out.println("Fin carga de elementos");
				
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	


	public ItemsType getShownItemType() {
		return shownItemType;
	}

	public void setShownItemType(ItemsType shownItemType) {
		this.shownItemType = shownItemType;
	}

	public List<Pane> getItemsBlueprints() {
		return itemsBlueprints;
	}
	
	
	
	
	
	
}
