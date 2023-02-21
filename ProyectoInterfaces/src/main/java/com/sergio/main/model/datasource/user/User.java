package com.sergio.main.model.datasource.user;


import com.sergio.main.model.repository.database.DataBaseTransactions;
import com.sergio.main.model.repository.database.dao.UserDAOImpl;

import javax.persistence.*;
import java.util.*;

@Entity(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String email;
	private String image;
	private String password;

	@ElementCollection()
	@CollectionTable(
			name = "users_anime_favourites",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
	)
	@Column(name = "anime_id")
	private List<Integer> animeFavourites;

	@ElementCollection
	@CollectionTable(
			name = "users_anime_following",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
	)
	@Column(name = "anime_id")
	private List<Integer> animeFollowing;

	@ElementCollection
	@CollectionTable(
			name = "users_manga_favourites",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
	)
	@Column(name = "manga_id")
	private List<Integer> mangaFavourites;

	@ElementCollection
	@CollectionTable(
			name = "users_manga_following",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
	)
	@Column(name = "manga_id")
	private List<Integer> mangaFollowing;
	
	
	public User() {
		this.animeFavourites = new ArrayList<>();
		this.animeFollowing = new ArrayList<>();
		this.mangaFavourites = new ArrayList<>();
		this.mangaFollowing = new ArrayList<>();

	}

	public User(int id, String username, String email, String image, List<Integer> animeFavourites, List<Integer> animeFollowing, List<Integer> mangaFavourites, List<Integer> mangaFollowing) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.image = image;
		this.animeFavourites = animeFavourites;
		this.animeFollowing = animeFollowing;
		this.mangaFavourites = mangaFavourites;
		this.mangaFollowing = mangaFollowing;
	}

	public boolean addAnimeFavourite(int idAnime){

		return add(idAnime, animeFavourites);

	}

	public boolean removeAnimeFavourite(int idAnime){

		return remove(idAnime, animeFavourites);

	}



	public boolean addAnimeFollowing(int idAnime){

		return add(idAnime, animeFollowing);

	}

	public boolean removeAnimeFollowing(int idAnime){

		return remove(idAnime, animeFollowing);

	}

	public boolean addMangaFavourite(int idManga){

		return add(idManga, mangaFavourites);


	}

	public boolean removeMangaFavourite(int idManga){

		return remove(idManga, mangaFavourites);

	}

	public boolean addMangaFollowing(int idManga){

		return add(idManga, mangaFollowing);

	}



	public boolean removeMangaFollowing(int idManga){

		return remove(idManga, mangaFollowing);

	}

	private boolean add(int id, List<Integer> visualWorks) {

		boolean added = false;
		visualWorks.add(id);

		try {

			new UserDAOImpl().registerUser(this);
			added = true;


		}catch (Exception e){

			DataBaseTransactions.getInstance().rollback();
			visualWorks.remove(id);

		}

		return added;

	}

	private boolean remove(int id, List<Integer> visualWorks) {
		boolean removed = false;
		visualWorks.remove(id);
		try {

			new UserDAOImpl().registerUser(this);
			removed = true;

		}catch (Exception e){

			DataBaseTransactions.getInstance().rollback();
			visualWorks.add(id);

		}

		return removed;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
