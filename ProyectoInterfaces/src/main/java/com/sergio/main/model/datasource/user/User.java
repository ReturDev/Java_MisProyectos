package com.sergio.main.model.datasource.user;


import com.sergio.main.model.repositories.database.DataBaseTransactions;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String image;

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
	
	
	public User() {}

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

		boolean added = false;
		animeFavourites.add(idAnime);
		try {

			makeTransaction("INSERT INTO users_anime_favourites VALUES :idUser, :id", idAnime);
			added = true;

		}catch (Exception e){

			DataBaseTransactions.getInstance().rollback();
			animeFavourites.remove(idAnime);

		}

		return added;

	}

	public boolean removeAnimeFavourite(int idAnime){

		boolean removed = false;
		animeFavourites.remove(idAnime);
		try {

			makeTransaction("DELETE FROM users_anime_favourites WHERE user_id = :idUser AND anime_id = :id", idAnime);
			removed = true;

		}catch (Exception e){

			DataBaseTransactions.getInstance().rollback();
			animeFavourites.add(idAnime);

		}

		return removed;

	}

	public boolean addAnimeFollowing(int idAnime){

		boolean added = false;
		animeFollowing.add(idAnime);

		try {

			makeTransaction("INSERT INTO users_anime_following VALUES :idUser, :id", idAnime);
			added = true;


		}catch (Exception e){

			DataBaseTransactions.getInstance().rollback();
			animeFollowing.remove(idAnime);

		}

		return added;

	}

	public boolean removeAnimeFollowing(int idAnime){

		boolean removed = false;
		animeFollowing.remove(idAnime);
		try {

			makeTransaction("DELETE FROM users_anime_following WHERE user_id = :idUser AND anime_id = :id", idAnime);
			removed = true;

		}catch (Exception e){

			DataBaseTransactions.getInstance().rollback();
			animeFollowing.add(idAnime);

		}

		return removed;

	}

	public boolean addMangaFavourite(int idManga){

		boolean added = false;
		mangaFavourites.add(idManga);

		try {

			makeTransaction("INSERT INTO users_manga_favourites VALUES :idUser, :id", idManga);
			added = true;


		}catch (Exception e){

			DataBaseTransactions.getInstance().rollback();
			mangaFavourites.remove(idManga);

		}

		return added;


	}

	public boolean removeMangaFavourite(int idManga){

		boolean removed = false;
		mangaFavourites.remove(idManga);
		try {

			makeTransaction("DELETE FROM users_manga_favourites WHERE user_id = :idUser AND manga_id = :id", idManga);
			removed = true;

		}catch (Exception e){

			DataBaseTransactions.getInstance().rollback();
			mangaFavourites.add(idManga);

		}

		return removed;

	}

	public boolean addMangaFollowing(int idManga){

		boolean added = false;
		mangaFollowing.add(idManga);

		try {

			makeTransaction("INSERT INTO users_manga_following VALUES :idUser, :id", idManga);
			added = true;


		}catch (Exception e){

			DataBaseTransactions.getInstance().rollback();
			mangaFollowing.remove(idManga);

		}

		return added;

	}

	public boolean removeMangaFollowing(int idManga){

		boolean removed = false;
		mangaFollowing.remove(idManga);
		try {

			makeTransaction("DELETE FROM users_manga_following WHERE user_id = :idUser AND manga_id = :id", idManga);
			removed = true;

		}catch (Exception e){

			DataBaseTransactions.getInstance().rollback();
			mangaFollowing.add(idManga);

		}

		return removed;

	}


	private void makeTransaction(String query, int id){

		DataBaseTransactions dbt = DataBaseTransactions.getInstance();
		Query q = dbt.createQuery(query);
		q.setParameter("idUser", this.id);
		q.setParameter("id", id);

		dbt.makeTransactionVisualWork(q);

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
