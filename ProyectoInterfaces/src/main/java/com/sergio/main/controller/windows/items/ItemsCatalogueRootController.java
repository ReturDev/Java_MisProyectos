package com.sergio.main.controller.windows.items;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sergio.main.model.datasources.enums.ItemsType;

import com.sergio.main.model.datasources.items.Anime;
import com.sergio.main.model.datasources.items.VisualWork;
import com.sergio.main.model.repositories.api.dao.anime.AnimeDAOImpl;
import com.sergio.main.model.repositories.api.dao.manga.MangaDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ItemsCatalogueRootController {

	private final int BLUEPRINTS_QUANTITY = 25;

	@FXML
	private ScrollPane scrollPane;
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

	private int page;

	private boolean hasNextPage;

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
	private void onPreviousPage() {

		page--;
		cleanRoot();
		loadData();


	}
	
	
	/**
	 * Evento que avanzar� a una siguiente p�gina con elementos.
	 */
	@FXML
	private void onNextPage() {

		page++;
		cleanRoot();
		loadData();

	}
	
	
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

				for(int i = 0; i < BLUEPRINTS_QUANTITY; i++) {
				
				
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sergio/main/view/windows/items/itemBlueprintView.fxml"));
					ItemBlueprintController controller = new ItemBlueprintController();

					loader.setController(controller);
					
					Pane itemPane = (Pane) loader.load();
					itemPane.getProperties().put("controller", controller);
					itemsBlueprints.add(itemPane);
					
				}

			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

	public void loadData(){

		List<VisualWork> list;

		try {

			if(shownItemType.equals(ItemsType.ANIME)){

				AnimeDAOImpl dao = new AnimeDAOImpl();
				list = new ArrayList(dao.getPageAnime(page));
				hasNextPage = dao.hasNextPage();

			}else {

				MangaDAOImpl dao = new MangaDAOImpl();
				list = new ArrayList(dao.getPageManga(page));
				hasNextPage = dao.hasNextPage();

			}

			prepareElements(list);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public void prepareElements(List<VisualWork> list){

		for(int i = 0; i < list.size(); i++){

			Pane itemBlueprint = itemsBlueprints.get(i);
			ItemBlueprintController controller = (ItemBlueprintController) itemBlueprint.getProperties().get("controller");
			controller.setItem(list.get(i));
			itemsRoot.getChildren().add(itemBlueprint);

		}

		if (page == 1){

			btnPreviousPage.setDisable(true);

		}else {

			btnPreviousPage.setDisable(false);

		}

		if (hasNextPage){

			btnNextPage.setDisable(false);

		}else {

			btnNextPage.setDisable(true);

		}

	}

	public void resetButtons(){

		page = 1;

		btnNextPage.setDisable(true);

		btnPreviousPage.setDisable(true);


	}

	private void cleanRoot(){

		itemsRoot.getChildren().clear();

		scrollPane.vvalueProperty().set(scrollPane.getVmin());

	}

	public void setShownItemType(ItemsType shownItemType) {
		this.shownItemType = shownItemType;
	}
	
}
