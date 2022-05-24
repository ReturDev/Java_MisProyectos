module TiroAlPlato {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.media;
	
	opens com.retur to javafx.graphics, javafx.fxml;
}
