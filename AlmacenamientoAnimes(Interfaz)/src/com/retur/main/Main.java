package com.retur.main;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Aqui elimine ./
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("vista/VentanaInterfaz.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Mi programa");
			primaryStage.setMinWidth(900);
			primaryStage.setMinHeight(680);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
