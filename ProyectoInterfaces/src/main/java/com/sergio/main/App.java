package com.sergio.main;
	


import com.sergio.main.controller.menu.MenuController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;


public class App extends Application {

	private static final double STAGE_MIN_WIDTH = 900;
	private static final double STAGE_MIN_HEIGHT = 700;

	@Override
	public void start(Stage primaryStage) {
		try {

			HBox root = FXMLLoader.load(getClass().getResource("view/mainView.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Proyecto Interfaces");
			primaryStage.getIcons().add(new Image("/images/app-icon.png"));
			primaryStage.setWidth(STAGE_MIN_WIDTH);
			primaryStage.setHeight(STAGE_MIN_HEIGHT);
			primaryStage.setMinWidth(STAGE_MIN_WIDTH);
			primaryStage.setMinHeight(STAGE_MIN_HEIGHT);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
