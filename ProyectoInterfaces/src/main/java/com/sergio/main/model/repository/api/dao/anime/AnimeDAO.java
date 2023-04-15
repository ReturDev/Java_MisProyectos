package com.sergio.main.model.repository.api.dao.anime;

import com.sergio.main.model.datasource.items.Anime;
import com.sergio.main.model.repository.api.dao.DAO;

import java.io.IOException;
import java.util.List;

/**
 * Dao de anime que obtiene los datos de la API.
 */
public interface AnimeDAO extends DAO {

    List<Anime> getPageAnime(int page) throws IOException;

    Anime getAnimeByID(int id) throws IOException;

    List<Anime> getAnimeSearched(int page, String name) throws IOException;

}
