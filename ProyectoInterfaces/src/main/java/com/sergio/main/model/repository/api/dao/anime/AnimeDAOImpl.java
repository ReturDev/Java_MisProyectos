package com.sergio.main.model.repository.api.dao.anime;

import com.sergio.main.model.datasource.items.Anime;
import com.sergio.main.model.repository.api.APIConnection;
import com.sergio.main.model.util.DAOImplApiHelper;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de los métodos del dao.
 */
public class AnimeDAOImpl implements AnimeDAO{

    private boolean hasNextPage;

    @Override
    public List<Anime> getPageAnime(int page) throws IOException {

        HttpURLConnection connection = APIConnection.getInstance().getAnimeConnection("?page=" + page);

        return getAnimePages(connection);

    }

    @Override
    public Anime getAnimeByID(int id) throws IOException {

        HttpURLConnection connection = APIConnection.getInstance().getAnimeConnection("/"+ id);
        StringBuilder sb = getResult(connection);
        JSONObject jsonObject = (JSONObject) new JSONObject(sb.toString()).get(DAOImplApiHelper.DATA_TAG);

        return bindAnime(jsonObject);

    }

    @Override
    public List<Anime> getAnimeSearched(int page, String name) throws IOException {

        HttpURLConnection connection = APIConnection.getInstance().getAnimeConnection("?q=" + name + "&page=" + page);

        return getAnimePages(connection);

    }

    private List<Anime> getAnimePages(HttpURLConnection connection) throws IOException {

        StringBuilder sb = getResult(connection);
        JSONObject fullObj = new JSONObject(sb.toString());
        hasNextPage = DAOImplApiHelper.getHasNextPage(fullObj);

        return DAOImplApiHelper.getDataFromPageApi(fullObj).stream().map(Anime::new).collect(Collectors.toList());

    }

    private Anime bindAnime(JSONObject obj){

        return new Anime(DAOImplApiHelper.bind(obj));

    }

    public boolean hasNextPage() {

        return hasNextPage;

    }

}
