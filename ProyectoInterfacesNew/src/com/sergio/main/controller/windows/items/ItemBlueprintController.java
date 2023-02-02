package com.sergio.main.controller.windows.items;

import java.net.URL;
import java.util.ResourceBundle;

import com.sergio.main.model.datasources.items.VisualWork;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ItemBlueprintController implements Initializable {
	
    @FXML
    private VBox blueprintRoot;

    @FXML
    private Button btnFavourites;

    @FXML
    private Button btnFollow;
    
    @FXML
    private ImageView imageVItem;

    @FXML
    private Label lblName;
    
	private VisualWork item;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		blueprintRoot.setOnMouseClicked(clickOnItemEvent());
		btnFavourites.setOnAction(buttonsItemEvent());
		btnFollow.setOnAction(buttonsItemEvent());
		
		
	}
	
	/**
	 *  Manejador de evento de click de rat�n que se asignar� al elemento raiz del elemento para que cuando se produzca un click en 
	 *  cualquier lugar de este, abra la ventana de detalles.
	 * @return
	 */
	private EventHandler<MouseEvent> clickOnItemEvent(){
		
		return (event) -> {
		
			System.out.println("Clicado");
			
		};
		
	}
	
	/**
	 * Manejador de evento de acci�n para los botones de Favoritos y Seguir de los elementos listados.
	 * @param btnFavourite Bot�n Favorito
	 * @param btnFollow Bot�n Siguiendo
	 * @return 
	 */
	private EventHandler<ActionEvent> buttonsItemEvent(){
		
		
		return (event) -> {
			
			Button btnActtivated = (Button) event.getSource();
			
			//TODO Cambiar este if a comprobar si el usuario está logueado.
			if(item != null) {
			
				if(btnActtivated.equals(btnFavourites)) {
					
					//TODO Acci�n al pulsar el bot�n de like.
					System.out.println("Like");
					
				}else if(btnActtivated.equals(btnFollow)) {
					
					//TODO Acci�n al pulsar el bot�n de Follow.
					System.out.println("Follow");
					
				}
				
				
			}else {			
				
				System.out.println("El usuario no está logueado.");
				//TODO Notificar que el usuario no esta logueado.
				
			}
			
			
		};
		
	}
	
	private void blindInfoToBlueprint() {
		
		imageVItem.setImage(item.getImage());
		lblName.setText(item.getName());
		
	}
	
	

	public VisualWork getItem() {
		
		return item;
		
	}

	public void setItem(VisualWork item) {
		
		this.item = item;
		blindInfoToBlueprint();
		
	}
	
	
	
	
}
