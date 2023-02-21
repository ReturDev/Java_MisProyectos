package com.sergio.main;
	


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;


public class App extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			HBox root = (HBox)FXMLLoader.load(getClass().getResource("view/mainView.fxml"));
			Scene scene = new Scene(root,700,700);
			//scene.getStylesheets().add(getClass().getResource("/resources/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(1200);
			primaryStage.setMinHeight(900);
			primaryStage.show();


			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
