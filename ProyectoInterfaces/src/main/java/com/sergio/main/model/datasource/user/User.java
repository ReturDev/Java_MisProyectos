package com.sergio.main.model.datasource.user;


import com.sergio.main.model.repositories.database.DataBaseTransactions;
import com.sergio.main.model.repositories.database.dao.UserDAOImpl;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String image;
	private String password;

	@ElementCollection()
	@CollectionTable(
			name = "users_anime_favourites",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
	)
	@Column(name = "anime_id")
	private Set<Integer> animeFavourites;

	@ElementCollection
	@CollectionTable(
			name = "users_anime_following",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
	)
	@Column(name = "anime_id")
	private Set<Integer> animeFollowing;

	@ElementCollection
	@CollectionTable(
			name = "users_manga_favourites",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
	)
	@Column(name = "manga_id")
	private Set<Integer> mangaFavourites;

	@ElementCollection
	@CollectionTable(
			name = "users_manga_following",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
	)
	@Column(name = "manga_id")
	private Set<Integer> mangaFollowing;
	
	
	public User() {
		this.animeFavourites = new HashSet<>();
		this.animeFollowing = new HashSet<>();
		this.mangaFavourites = new HashSet<>();
		this.mangaFollowing = new HashSet<>();

	}

	public User(int id, String name, String email, String image, Set<Integer> animeFavourites, Set<Integer> animeFollowing, Set<Integer> mangaFavourites, Set<Integer> mangaFollowing) {
		this.id = id;
		this.name = name;
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

	private boolean add(int id, Set<Integer> visualWorks) {

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

	private boolean remove(int id, Set<Integer> visualWorks) {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Integer> getAnimeFavourites() {
		return animeFavourites;
	}

	public void setAnimeFavourites(Set<Integer> animeFavourites) {
		this.animeFavourites = animeFavourites;
	}

	public Set<Integer> getAnimeFollowing() {
		return animeFollowing;
	}

	public void setAnimeFollowing(Set<Integer> animeFollowing) {
		this.animeFollowing = animeFollowing;
	}

	public Set<Integer> getMangaFavourites() {
		return mangaFavourites;
	}

	public void setMangaFavourites(Set<Integer> mangaFavourites) {
		this.mangaFavourites = mangaFavourites;
	}

	public Set<Integer> getMangaFollowing() {
		return mangaFollowing;
	}

	public void setMangaFollowing(Set<Integer> mangaFollowing) {
		this.mangaFollowing = mangaFollowing;
	}
}
