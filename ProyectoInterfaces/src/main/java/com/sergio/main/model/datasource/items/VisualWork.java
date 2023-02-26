package com.sergio.main.model.datasource.items;

import java.util.List;

import com.sergio.main.model.datasource.enums.ItemsType;

import javafx.scene.image.Image;

public class VisualWork {

	
	protected int id;
	protected Image image;
	protected String name;
	protected String synopsis;
	//Information
	private String type;
	private String status;
	private List<String> genres;
	private List<String> themes;
	private String demographic;
	
	public VisualWork() {}

	public VisualWork(int id, Image image, String name, String synopsis, String type, String status, List<String> genres, List<String> themes, String demographic) {
		this.id = id;
		this.image = image;
		this.name = name;
		this.synopsis = synopsis;
		this.type = type;
		this.status = status;
		this.genres = genres;
		this.themes = themes;
		this.demographic = demographic;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getGenres() {
		return genres;
	}
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
	public List<String> getThemes() {
		return themes;
	}
	public void setThemes(List<String> themes) {
		this.themes = themes;
	}
	public String getDemographic() {
		return demographic;
	}
	public void setDemographic(String demographic) {
		this.demographic = demographic;
	}
	

}
