package com.sergio.main.controller.windows.items;

import java.net.URL;
import java.util.ResourceBundle;

import com.sergio.main.model.datasource.items.Anime;
import com.sergio.main.model.datasource.items.Manga;
import com.sergio.main.model.datasource.items.VisualWork;

import com.sergio.main.model.datasource.notifications.NotificationCreator;
import com.sergio.main.model.datasource.user.User;
import com.sergio.main.model.datasource.user.UserState;
import com.sergio.main.model.repositories.database.DataBaseTransactions;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ItemBlueprintController implements Initializable {
	
    @FXML
    private VBox blueprintRoot;

    @FXML
    private Button btnFavourites;

    @FXML
	private ImageView ivFavourites;

    @FXML
    private Button btnFollow;

    @FXML
	private ImageView ivFollow;
    
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
	 * @return 
	 */
	private EventHandler<ActionEvent> buttonsItemEvent(){
		
		
		return (event) -> {
			
			Button btnActtivated = (Button) event.getSource();
			
			//TODO Cambiar este if a comprobar si el usuario está logueado.
			if(UserState.isUserLogged()) {
			
				if(btnActtivated.equals(btnFavourites)) {

					NotificationCreator.createNotification(blueprintRoot,null, "Se ha añadido a favoritos.",1);
					validateFavourites(UserState.getUserLoggedData(), item.getId());

				}else if(btnActtivated.equals(btnFollow)) {
					
					NotificationCreator.createNotification(blueprintRoot,null, "Se ha añadido a siguiendo.",1);
					validateFollowing(UserState.getUserLoggedData(), item.getId());

				}
				
				
			}else {			
				
				System.out.println("El usuario no está logueado.");
				NotificationCreator.createNotification(blueprintRoot,"Usuario no logueado", "Debes loguearte para realizar esa acción",3);
				
			}
			
			
		};
		
	}
	
	private void blindInfoToBlueprint() {
		
		imageVItem.setImage(item.getImage());
		lblName.setText(item.getName());
		if (UserState.isUserLogged()){
			validateFollowing(UserState.getUserLoggedData(), item.getId());
			validateFavourites(UserState.getUserLoggedData(), item.getId());
		}else {

			setEmptyFollowing();
			setEmptyFavourite();

		}
		
	}

	private void validateFavourites(User user, int id){

		if (item instanceof Anime){

			if (user.getAnimeFavourites().contains(id)){

				user.removeAnimeFavourite(id);
				setEmptyFavourite();

			}else {

				user.addAnimeFavourite(id);
				setFilledFavourite();

			}

		}else if (item instanceof Manga){

			if (user.getMangaFavourites().contains(id)){

				user.removeMangaFavourite(id);
				setEmptyFavourite();

			}else {

				user.addMangaFavourite(id);
				setFilledFavourite();

			}

		}

	}

	private void validateFollowing(User user, int id){

		if (item instanceof Anime){

			if (user.getAnimeFollowing().contains(id)){

				user.removeAnimeFollowing(id);
				setEmptyFollowing();

			}else {

				user.addAnimeFollowing(id);
				setFilledFollowing();

			}

		}else if (item instanceof Manga){

			if (user.getMangaFollowing().contains(id)){

				user.removeMangaFollowing(id);
				setEmptyFollowing();

			}else {

				user.addMangaFollowing(id);
				setFilledFollowing();

			}

		}

	}

	private void setFilledFavourite(){
		ivFavourites.setImage(new Image("/icons/content/items/heart-filled.png"));
	}
	private void setFilledFollowing(){
		ivFollow.setImage(new Image("/icons/content/items/follow-filled.png"));
	}
	private void setEmptyFavourite(){
		ivFavourites.setImage(new Image("/icons/content/items/heart-empty.png"));
	}
	private void setEmptyFollowing(){
		ivFollow.setImage(new Image("/icons/content/items/follow-empty.png"));
	}

	public VisualWork getItem() {
		
		return item;
		
	}

	public void setItem(VisualWork item) {
		
		this.item = item;
		blindInfoToBlueprint();
		
	}

}
