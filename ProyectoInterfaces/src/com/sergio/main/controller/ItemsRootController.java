package com.sergio.main.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.sergio.main.model.DataSaver;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class ItemsRootController implements Initializable {

	
	
	
	@FXML
	private FlowPane itemsRoot;
	@FXML
	private Button btnPreviousPage;
	@FXML
	private Button btnNextPage;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			
			ArrayList<Pane> itemsBlueprints = DataSaver.ITEMS_BLUEPRINTS_LIST;
			
			if (itemsBlueprints.isEmpty()) {
				

				for(int i = 0; i < 30; i++) {
					
					
					Pane itemPane = (Pane) FXMLLoader.load(getClass().getResource("../view/ItemView.fxml"));
					itemsBlueprints.add(itemPane);
					
				}
				
			}
			
			
			for(int i = 0 ; i < itemsBlueprints.size(); i++) {
				
				ImageView iv = (ImageView) itemsBlueprints.get(i).getChildren().get(0);
				Button fav = (Button) itemsBlueprints.get(i).getChildren().get(1);
				Button fol = (Button) itemsBlueprints.get(i).getChildren().get(2);
				
				
				
				
				itemsRoot.getChildren().add(itemsBlueprints.get(i));
				
			}
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	@FXML
	private void onPreviousPage() {}
	
	
	@FXML
	private void onNextPage() {}
	

}
