package com.sergio.main.controller.items;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sergio.main.model.savers.DataSaver;
import com.sergio.main.model.savers.UserDataSaver;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ItemsRootController implements Initializable {

	
	
	
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
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		

		try {
			
			ArrayList<Pane> itemsBlueprints = DataSaver.ITEMS_BLUEPRINTS_LIST;
			
			if (itemsBlueprints.isEmpty()) {
				
				System.out.println("Carga de elementos");

				for(int i = 0; i < 30; i++) {
					
					
					Pane itemPane = (Pane) FXMLLoader.load(getClass().getResource("/com/sergio/main/view/items/ItemView.fxml"));
					Pane buttonsParent = (Pane) itemPane.getChildren().get(2);
					Button fav = (Button) buttonsParent.getChildren().get(0);
					Button fol = (Button) buttonsParent.getChildren().get(1);
					
					
					itemPane.setOnMouseClicked(clickOnItemEvent());
					fav.setOnAction(buttonsItemEvent(fav, fol));
					fol.setOnAction(buttonsItemEvent(fav, fol));
					
					itemsBlueprints.add(itemPane);
					
				}
				
			}
			
			
			
			for(Pane itemPane : itemsBlueprints) {
				
				ImageView iv = (ImageView) itemPane.getChildren().get(0);
				Label name = (Label) ((Pane)itemPane.getChildren().get(1)).getChildren().get(0);
				
				itemsRoot.getChildren().add(itemPane);
				
			}
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	@FXML
	private void onSearch() {
		
		//TODO Filtrar por el texto de busqueda y obtener todos los elementos con el nombre;
		System.out.println("Buscando");
		
	}
	
	
	/**
	 * Evento que volverá a una página anteriror de elementos.
	 */
	@FXML
	private void onPreviousPage() {}
	
	
	/**
	 * Evento que avanzará a una siguiente página con elementos.
	 */
	@FXML
	private void onNextPage() {}
	
	
	/**
	 * Con el foco en el campo "Buscar" disparará el evento de buscar al pulsar enter.
	 * @param event
	 */
	@FXML
	private void onPressEnter(KeyEvent event) {
		
		if (event.getCode() == KeyCode.ENTER) {
			
			btnSearch.fire();
			
		}
		
	}
	

	/**
	 * Manejador de evento de acción para los botones de Favoritos y Seguir de los elementos listados.
	 * @param btnFavourite Botón Favorito
	 * @param btnFollow Botón Siguiendo
	 * @return 
	 */
	private EventHandler<ActionEvent> buttonsItemEvent(Button btnFavourite, Button btnFollow){
		
		
		return (event) -> {
			
			Button btnActtivated = (Button) event.getSource();
			
			if(UserDataSaver.isUserLoged()) {
				
				if(btnActtivated.equals(btnFavourite)) {
					
					
					//TODO Acción al pulsar el botón de like.
					System.out.println("Like");
					
				}else if(btnActtivated.equals(btnFollow)) {
					
					//TODO Acción al pulsar el botón de Follow.
					System.out.println("Follow");
					
				}
				
				
			}else {
				
				System.out.println("El usuario no está logueado.");
				//TODO Notificar que el usuario no esta logueado.
				
			}
			
			
		};
		
	}
	
	/**
	 *  Manejador de evento de click de ratón que se asignará al elemento raiz del elemento para que cuando se produzca un click en 
	 *  cualquier lugar de este, abra la ventana de detalles.
	 * @return
	 */
	private EventHandler<MouseEvent> clickOnItemEvent(){
		
		return (event) -> {
			
			
			//TODO Ir a los detalles del elemento clicado.
			System.out.println("Clicado");
			
		};
		
		
		
	}
	
	
}
