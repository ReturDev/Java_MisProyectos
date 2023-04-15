package com.sergio.main.controller.windows.items;

import java.net.URL;
import java.util.ResourceBundle;

import com.sergio.main.model.datasource.exceptions.ActionFailedException;
import com.sergio.main.model.datasource.items.Anime;
import com.sergio.main.model.datasource.items.Manga;
import com.sergio.main.model.datasource.items.VisualWork;

import com.sergio.main.model.datasource.dialogs.notifications.NotificationCreator;
import com.sergio.main.model.datasource.user.User;
import com.sergio.main.model.datasource.user.UserState;
import com.sergio.main.model.util.StylesConstants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Clase controladora de la plantilla donde se mostrarán los elementos.
 */
public class ItemBlueprintController implements Initializable {
	
    @FXML
    private VBox blueprintRoot;

    @FXML
    private Button btnFavourites;

    @FXML
	private Region favRegion;

    @FXML
    private Button btnFollow;

    @FXML
	private Region followRegion;
    
    @FXML
    private ImageView imageVItem;

    @FXML
    private Label lblName;
    
	private VisualWork item;


	/**
	 * Realiza acciones en la inicialización de la clase.
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		blueprintRoot.setOnMouseClicked(clickOnItemEvent());
		btnFavourites.setOnAction(buttonsItemEvent());
		btnFollow.setOnAction(buttonsItemEvent());
		
	}
	
	/**
	 *  Manejador de evento de click de ratón que se asignará al elemento raiz del elemento para que cuando se produzca un click en
	 *  cualquier lugar de este, abra la ventana de detalles.
	 * @return
	 */
	private EventHandler<MouseEvent> clickOnItemEvent(){
		
		return (event) -> {
		
			System.out.println("Clicado");
			
		};
		
	}
	
	/**
	 * Manejador de evento de acción para los botones de Favoritos y Seguir de los elementos listados.
	 * @return 
	 */
	private EventHandler<ActionEvent> buttonsItemEvent(){
		
		
		return (event) -> {
			
			Button btnActtivated = (Button) event.getSource();

			if(UserState.isUserLogged()) {
			
				if(btnActtivated.equals(btnFavourites)) {

					try {

						btnFavouritesAction(UserState.getUserLoggedData());

					} catch (ActionFailedException e) {

						e.printStackTrace();

					}
				}else if(btnActtivated.equals(btnFollow)) {

					try {

						btnFollowingAction(UserState.getUserLoggedData());

					} catch (ActionFailedException e) {

						e.printStackTrace();

					}

				}
				
			}else {			
				
				System.out.println("El usuario no está logueado.");
				NotificationCreator.createAndShowNotification(blueprintRoot,"Usuario no logueado", "Debes loguearte para realizar esa acción", 3,true, null);
				
			}
			
			
		};
		
	}

	/**
	 * Establece los datos del elemento en la vista.
	 */
	private void blindInfoToBlueprint() {
		
		imageVItem.setImage(new Image(item.getImage(),imageVItem.getFitWidth(),imageVItem.getFitHeight(),false,true));
		lblName.setText(item.getName());

		if (UserState.isUserLogged()){

			checkFavouritesUser(UserState.getUserLoggedData());
			checkFollowingUser(UserState.getUserLoggedData());

		}else {

			setEmptyFollowing();
			setEmptyFavourite();

		}
		
	}

	/**
	 * Acción del botón de favoritos que añadirá o eliminará el elemento que representa de los favoritos
	 * del usuario.
	 * @param user Usuario logueado.
	 * @throws ActionFailedException
	 */
	private void btnFavouritesAction(User user) throws ActionFailedException {

		if (item instanceof Anime){

			if (user.getAnimeFavourites().contains(item.getId())){

				user.removeAnimeFavourite(item.getId());
				setEmptyFavourite();
				NotificationCreator.createAndShowNotification(blueprintRoot,null, "Se ha eliminado de favoritos.",1,true, null);


			}else {

				user.addAnimeFavourite(item.getId());
				setFilledFavourite();
				NotificationCreator.createAndShowNotification(blueprintRoot,null, "Se ha añadido a favoritos.",1,true, null);

			}

		}else if (item instanceof Manga){

			if (user.getMangaFavourites().contains(item.getId())){

				user.removeMangaFavourite(item.getId());
				setEmptyFavourite();
				NotificationCreator.createAndShowNotification(blueprintRoot,null, "Se ha eliminado de favoritos.",1,true, null);


			}else {

				user.addMangaFavourite(item.getId());
				setFilledFavourite();
				NotificationCreator.createAndShowNotification(blueprintRoot,null, "Se ha añadido a favoritos.",1,true, null);

			}

		}

	}

	/**
	 * Acción del botón de seguir que añadirá o eliminará el elemento que representa de los elementos seguidos
	 * del usuario.
	 * @param user Usuario logueado.
	 * @throws ActionFailedException
	 */
	private void btnFollowingAction(User user) throws ActionFailedException {

		if (item instanceof Anime){

			if (user.getAnimeFollowing().contains(item.getId())){

				user.removeAnimeFollowing(item.getId());
				setEmptyFollowing();
				NotificationCreator.createAndShowNotification(blueprintRoot,null, "Se ha dejado de seguir.", 1, true,null);

			}else {

				user.addAnimeFollowing(item.getId());
				setFilledFollowing();
				NotificationCreator.createAndShowNotification(blueprintRoot,null, "Se ha añadido a siguiendo.", 1, true,null);

			}

		}else if (item instanceof Manga){

			if (user.getMangaFollowing().contains(item.getId())){

				user.removeMangaFollowing(item.getId());
				setEmptyFollowing();
				NotificationCreator.createAndShowNotification(blueprintRoot,null, "Se ha dejado de seguir.", 1, true,null);

			}else {

				user.addMangaFollowing(item.getId());
				setFilledFollowing();
				NotificationCreator.createAndShowNotification(blueprintRoot,null, "Se ha añadido a siguiendo.", 1, true,null);

			}

		}

	}

	/**
	 * Comprueba los elementos que el usuario ya tiene en sus favoritos para cambiar como se muestra en la vista.
	 * @param user Usuario logueado
	 */
	private void checkFavouritesUser(User user){

		if (item instanceof Anime){

			if (user.getAnimeFavourites().contains(item.getId())){

				setFilledFavourite();

			}else {

				setEmptyFavourite();

			}

		}else if (item instanceof Manga){

			if (user.getMangaFavourites().contains(item.getId())){

				setFilledFavourite();

			}else {

				setEmptyFavourite();

			}

		}

	}

	/**
	 * Comprueba los elementos que el usuario ya tiene en sus seguidos para cambiar como se muestra en la vista.
	 * @param user Usuario logueado
	 */
	private void checkFollowingUser(User user){

		if (item instanceof Anime){

			if (user.getAnimeFollowing().contains(item.getId())){

				setFilledFollowing();

			}else {

				setEmptyFollowing();

			}

		}else if (item instanceof Manga){

			if (user.getMangaFollowing().contains(item.getId())){

				setFilledFollowing();

			}else {

				setEmptyFollowing();

			}

		}

	}


	private void setFilledFavourite(){
		favRegion.getStyleClass().add(StylesConstants.FAV_SELECTED);
	}
	private void setFilledFollowing(){
		followRegion.getStyleClass().add(StylesConstants.FOLLOW_SELECTED);
	}
	private void setEmptyFavourite(){
		favRegion.getStyleClass().remove(StylesConstants.FAV_SELECTED);
	}
	private void setEmptyFollowing(){
		followRegion.getStyleClass().remove(StylesConstants.FOLLOW_SELECTED);
	}

	/**
	 * Asigna el elemento que va a representar.
	 * @param item Elemento a representar
	 */
	public void setItem(VisualWork item) {
		
		this.item = item;
		blindInfoToBlueprint();
		
	}

}
