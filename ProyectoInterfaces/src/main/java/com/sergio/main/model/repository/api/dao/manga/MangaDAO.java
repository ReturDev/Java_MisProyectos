package com.sergio.main.model.repository.api.dao.manga;

import com.sergio.main.model.datasource.items.Manga;
import com.sergio.main.model.repository.api.dao.DAO;

import java.io.IOException;
import java.util.List;


/**
 * Dao de manga que obtiene los datos de la API.
 */
public interface MangaDAO extends DAO {

    List<Manga> getPageManga(int page) throws IOException;

    Manga getMangaByID(int id) throws IOException;

    List<Manga> getMangaSearched(int page, String name) throws IOException;

}
