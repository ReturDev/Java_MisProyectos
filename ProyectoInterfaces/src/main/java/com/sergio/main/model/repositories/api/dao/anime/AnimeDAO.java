package com.sergio.main.model.repositories.api.dao.anime;

import com.sergio.main.model.datasource.items.Anime;
import com.sergio.main.model.repositories.api.dao.DAO;

import java.io.IOException;
import java.util.List;

public interface AnimeDAO extends DAO {

    List<Anime> getPageAnime(int page) throws IOException;

    Anime getAnimeByID(int id);

}
