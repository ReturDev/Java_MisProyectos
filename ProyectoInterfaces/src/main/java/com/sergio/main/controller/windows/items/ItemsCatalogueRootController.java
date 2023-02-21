package com.sergio.main.controller.windows.items;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sergio.main.controller.windows.ItemsRootController;
import com.sergio.main.model.datasource.enums.ItemsType;

import com.sergio.main.model.datasource.items.VisualWork;
import com.sergio.main.model.repository.api.dao.anime.AnimeDAOImpl;
import com.sergio.main.model.repository.api.dao.manga.MangaDAOImpl;
import com.sergio.main.model.util.ItemsPaginationControllerUtilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class ItemsCatalogueRootController extends ItemsRootController {

	private static ItemsCatalogueRootController instance;
	protected final int BLUEPRINTS_QUANTITY = 25;

	@FXML
	private TextField tfSearch;
	@FXML
	private Button btnSearch;


	private ItemsCatalogueRootController() {

		super(new ItemsPaginationControllerUtilities());
		itemsBlueprints = new ArrayList<>();
		setShownItemType(ItemsType.ANIME);
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
	@Override
	public void onPreviousPage() {

		IPCU.onPreviousPage(itemsRoot, scrollPane);
		loadData();

	}
	
	
	/**
	 * Evento que avanzar� a una siguiente p�gina con elementos.
	 */
	@Override
	public void onNextPage() {

		IPCU.onNextPage(itemsRoot, scrollPane);
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

	@Override
	protected void loadItemBlueprints() {
		
		try {
			
			if (itemsBlueprints.isEmpty()) {

				for(int i = 0; i < BLUEPRINTS_QUANTITY; i++) {
				
				
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sergio/main/view/windows/items/itemBlueprintView.fxml"));
					ItemBlueprintController controller = new ItemBlueprintController();

					loader.setController(controller);
					
					Pane itemPane = loader.load();
					itemPane.getProperties().put("controller", controller);
					itemsBlueprints.add(itemPane);
					
				}

			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

	@Override
	public void loadData(){

		List<VisualWork> list;

		try {

			if(IPCU.getShownItemType().equals(ItemsType.ANIME)){

				AnimeDAOImpl dao = new AnimeDAOImpl();
				list = new ArrayList(dao.getPageAnime(IPCU.getPage()));
				IPCU.setHasNextPage(dao.hasNextPage());

			}else {

				MangaDAOImpl dao = new MangaDAOImpl();
				list = new ArrayList(dao.getPageManga(IPCU.getPage()));
				IPCU.setHasNextPage(dao.hasNextPage());

			}

			prepareElements(list);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}


	
}
