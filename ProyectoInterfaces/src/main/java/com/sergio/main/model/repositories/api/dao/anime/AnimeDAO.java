package com.sergio.main.model.repositories.api.dao.anime;

import com.sergio.main.model.datasources.items.Anime;
import com.sergio.main.model.repositories.api.dao.DAO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

public interface AnimeDAO extends DAO {

    List<Anime> getPageAnime(int page) throws IOException;

    Anime getAnimeByID(int id);

}
