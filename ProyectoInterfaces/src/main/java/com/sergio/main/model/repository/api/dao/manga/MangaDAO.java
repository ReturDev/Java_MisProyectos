package com.sergio.main.model.repository.api.dao.manga;

import com.sergio.main.model.datasource.items.Manga;
import com.sergio.main.model.repository.api.dao.DAO;

import java.io.IOException;
import java.util.List;

public interface MangaDAO extends DAO {

    List<Manga> getPageManga(int page) throws IOException;

    Manga getMangaByID(int id) throws IOException;

}
