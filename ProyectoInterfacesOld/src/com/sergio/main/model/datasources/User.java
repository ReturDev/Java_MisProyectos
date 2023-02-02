package com.sergio.main.model.datasources;

import java.util.List;

public class User {

	private int id;
	private String name;
	private String email;
	private String image;
	
	private String password;
	
	
	private List<Integer> animeFavourites;
	private List<Integer> animeFollowing;
	private List<Integer> mangaFavourites;
	private List<Integer> mangaFollowing;
	
	
	public User() {}
	
	public User(int id, String name, String email, String image, List<Integer> animeFavourites,
			List<Integer> animeFollowing, List<Integer> mangaFavourites, List<Integer> mangaFollowing) {
		
		this.id = id;
		this.name = name;
		this.email = email;
		this.image = image;
		this.animeFavourites = animeFavourites;
		this.animeFollowing = animeFollowing;
		this.mangaFavourites = mangaFavourites;
		this.mangaFollowing = mangaFollowing;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Integer> getAnimeFavourites() {
		return animeFavourites;
	}

	public void setAnimeFavourites(List<Integer> animeFavourites) {
		this.animeFavourites = animeFavourites;
	}

	public List<Integer> getAnimeFollowing() {
		return animeFollowing;
	}

	public void setAnimeFollowing(List<Integer> animeFollowing) {
		this.animeFollowing = animeFollowing;
	}

	public List<Integer> getMangaFavourites() {
		return mangaFavourites;
	}

	public void setMangaFavourites(List<Integer> mangaFavourites) {
		this.mangaFavourites = mangaFavourites;
	}

	public List<Integer> getMangaFollowing() {
		return mangaFollowing;
	}

	public void setMangaFollowing(List<Integer> mangaFollowing) {
		this.mangaFollowing = mangaFollowing;
	}
	
	
}
