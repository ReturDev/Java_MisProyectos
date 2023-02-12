package com.sergio.main.model.repositories.api.dao.manga;

import com.sergio.main.model.datasources.items.Anime;
import com.sergio.main.model.datasources.items.Manga;
import com.sergio.main.model.repositories.api.dao.DAO;

import java.io.IOException;
import java.util.List;

public interface MangaDAO extends DAO {

    List<Manga> getPageManga(int page) throws IOException;

    Manga getMangaByID(int id);

}
