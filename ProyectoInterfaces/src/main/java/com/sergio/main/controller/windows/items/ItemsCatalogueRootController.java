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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ItemsCatalogueRootController extends ItemsRootController {

	private static ItemsCatalogueRootController instance;
	protected final int BLUEPRINTS_QUANTITY = 25;

	@FXML
	protected Button btnPreviousPage;
	@FXML
	protected Button btnNextPage;
	@FXML
	protected FlowPane itemsRoot;
	@FXML
	protected ScrollPane scrollPane;
	@FXML
	private TextField tfSearch;
	@FXML
	private Button btnSearch;

	private String searchText;
	private boolean searching;


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
		
		if (tfSearch.getText().isBlank()){

			resetButtons();
			loadData();

		}else {

			resetButtons();
			searchText = tfSearch.getText();
			searching = true;
			loadSearchData(searchText);

		}
		
	}
	
	
	/**
	 * Evento que volver� a una p�gina anteriror de elementos.
	 */
	@Override
	public void onPreviousPage() {

		IPCU.onPreviousPage(itemsRoot, scrollPane);

		if (searching){

			tfSearch.setText(searchText);
			loadSearchData(searchText);

		}else {

			loadData();

		}

	}
	
	
	/**
	 * Evento que avanzar� a una siguiente p�gina con elementos.
	 */
	@Override
	public void onNextPage() {

		IPCU.onNextPage(itemsRoot, scrollPane);

		if (searching){

			tfSearch.setText(searchText);
			loadSearchData(searchText);

		}else {

			loadData();

		}

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
				list = new ArrayList<>(dao.getPageAnime(IPCU.getPage()));
				IPCU.setHasNextPage(dao.hasNextPage());

			}else {

				MangaDAOImpl dao = new MangaDAOImpl();
				list = new ArrayList<>(dao.getPageManga(IPCU.getPage()));
				IPCU.setHasNextPage(dao.hasNextPage());

			}

			prepareElements(list, itemsRoot, btnNextPage, btnPreviousPage);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	private void loadSearchData(String stringSearched){

		List<VisualWork> list;

		try {

			if(IPCU.getShownItemType().equals(ItemsType.ANIME)){

				AnimeDAOImpl dao = new AnimeDAOImpl();
				list = new ArrayList<>(dao.getAnimeSearched(IPCU.getPage(), stringSearched));
				IPCU.setHasNextPage(dao.hasNextPage());

			}else {

				MangaDAOImpl dao = new MangaDAOImpl();
				list = new ArrayList<>(dao.getMangaSearched(IPCU.getPage(), stringSearched));
				IPCU.setHasNextPage(dao.hasNextPage());

			}

			prepareElements(list, itemsRoot, btnNextPage, btnPreviousPage);

		} catch (IOException e) {

			e.printStackTrace();

		}


	}

	@Override
	public void resetButtons() {

		searching = false;
		searchText = "";
		IPCU.resetButtons(btnNextPage, btnPreviousPage,itemsRoot, scrollPane);

	}


}
