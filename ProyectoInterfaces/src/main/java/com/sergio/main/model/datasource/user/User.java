package com.sergio.main.model.datasource.user;


import com.sergio.main.model.datasource.exceptions.ActionFailedException;
import com.sergio.main.model.repository.database.DataBaseTransactions;
import com.sergio.main.model.repository.database.dao.UserDAOImpl;

import javax.persistence.*;
import java.util.*;

@Entity(name = "users")
public class User {

	@Id
	@Column(length = 50)
	private String username;
	private String email;
	private String image;
	private String password;

	@ElementCollection()
	@CollectionTable(
			name = "users_anime_favourites",
			joinColumns = @JoinColumn(name = "username", referencedColumnName = "username")
	)
	@Column(name = "anime_id")
	private List<Integer> animeFavourites;

	@ElementCollection
	@CollectionTable(
			name = "users_anime_following",
			joinColumns = @JoinColumn(name = "username", referencedColumnName = "username")
	)
	@Column(name = "anime_id")
	private List<Integer> animeFollowing;

	@ElementCollection
	@CollectionTable(
			name = "users_manga_favourites",
			joinColumns = @JoinColumn(name = "username", referencedColumnName = "username")
	)
	@Column(name = "manga_id")
	private List<Integer> mangaFavourites;

	@ElementCollection
	@CollectionTable(
			name = "users_manga_following",
			joinColumns = @JoinColumn(name = "username", referencedColumnName = "username")
	)
	@Column(name = "manga_id")
	private List<Integer> mangaFollowing;
	
	
	public User() {
		this.animeFavourites = new ArrayList<>();
		this.animeFollowing = new ArrayList<>();
		this.mangaFavourites = new ArrayList<>();
		this.mangaFollowing = new ArrayList<>();

	}

	public User(String username, String email, String image, List<Integer> animeFavourites, List<Integer> animeFollowing, List<Integer> mangaFavourites, List<Integer> mangaFollowing) {
		this.username = username;
		this.email = email;
		this.image = image;
		this.animeFavourites = animeFavourites;
		this.animeFollowing = animeFollowing;
		this.mangaFavourites = mangaFavourites;
		this.mangaFollowing = mangaFollowing;
	}

	public void addAnimeFavourite(int idAnime) throws ActionFailedException {

		add(idAnime, animeFavourites);

	}

	public void removeAnimeFavourite(int idAnime) throws ActionFailedException {

		remove(idAnime, animeFavourites);

	}



	public void addAnimeFollowing(int idAnime) throws ActionFailedException {

		add(idAnime, animeFollowing);

	}

	public void removeAnimeFollowing(int idAnime) throws ActionFailedException {

		remove(idAnime, animeFollowing);

	}

	public void addMangaFavourite(int idManga) throws ActionFailedException {

		add(idManga, mangaFavourites);


	}

	public void removeMangaFavourite(int idManga) throws ActionFailedException {

		remove(idManga, mangaFavourites);

	}

	public void addMangaFollowing(int idManga) throws ActionFailedException {

		add(idManga, mangaFollowing);

	}



	public void removeMangaFollowing(int idManga) throws ActionFailedException {

		remove(idManga, mangaFollowing);

	}

	private void add(int id, List<Integer> visualWorks) throws ActionFailedException {

		boolean added = false;
		visualWorks.add(id);

		try {

			new UserDAOImpl().updateUser(this);
			added = true;


		}catch (Exception e){

			DataBaseTransactions.getInstance().rollback();
			visualWorks.remove(id);

		}

		if (!added){

			throw new ActionFailedException();

		}

	}

	private void remove(Integer id, List<Integer> visualWorks) throws ActionFailedException {
		boolean removed = false;
		visualWorks.remove(id);
		try {

			new UserDAOImpl().updateUser(this);
			removed = true;

		}catch (Exception e){

			DataBaseTransactions.getInstance().rollback();
			visualWorks.add(id);

		}

		if (!removed){

			throw new ActionFailedException();

		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
