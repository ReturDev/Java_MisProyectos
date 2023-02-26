package com.sergio.main.model.datasource.items;

import java.util.List;

public class Anime extends VisualWork {

	//Information
	private int episodes;
	private String aired;
	private List<String> producers;
	private List<String> studios;
	private String source;
	private String rating;

	public Anime() {
		super();
	}

	public Anime(VisualWork visualWork){
		this.id = visualWork.id;
		this.name = visualWork.name;
		this.image = visualWork.image;
	}
	
}
