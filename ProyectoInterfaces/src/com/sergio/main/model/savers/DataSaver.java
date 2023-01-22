package com.sergio.main.model.savers;

import java.util.ArrayList;

import com.sergio.main.model.ElementsTags;

import javafx.scene.layout.Pane;

public class DataSaver {

	public static final ArrayList<Pane> ITEMS_BLUEPRINTS_LIST;
	private static ElementsTags DataType;
	
	static {
		
		ITEMS_BLUEPRINTS_LIST = new ArrayList<>();
		
	}

	public static ElementsTags getDataType() {
		return DataType;
	}

	public static void setDataType(ElementsTags dataType) {
		DataType = dataType;
	}

	
}
