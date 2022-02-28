module MiProgramaInterfaz {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.xml;
	requires javafx.base;
	
	
	opens controlador to javafx.graphics, javafx.fxml, javafx.base;
	opens modelo.clases to javafx.graphics, javafx.fxml, javafx.base;
}
