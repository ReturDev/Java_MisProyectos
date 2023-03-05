package com.sergio.main.model.repository.api.dao.manga;

import com.sergio.main.model.datasource.items.Manga;
import com.sergio.main.model.repository.api.APIConnection;
import com.sergio.main.model.util.DAOImplApiHelper;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.stream.Collectors;

public class MangaDAOImpl implements MangaDAO{

    private boolean hasNextPage;

    @Override
    public List<Manga> getPageManga(int page) throws IOException {

        HttpURLConnection connection = APIConnection.getInstance().getMangaConnection("?page=" + page);

        return getMangaPages(connection);

    }

    @Override
    public Manga getMangaByID(int id) throws IOException {

        HttpURLConnection connection = APIConnection.getInstance().getMangaConnection("/"+ id);
        StringBuilder sb = getResult(connection);
        JSONObject jsonObject = (JSONObject) new JSONObject(sb.toString()).get(DAOImplApiHelper.DATA_TAG);

        return bindManga(jsonObject);

    }

    @Override
    public List<Manga> getMangaSearched(int page, String name) throws IOException {

        HttpURLConnection connection = APIConnection.getInstance().getMangaConnection("?q=" + name + "&page=" + page);

        return getMangaPages(connection);

    }

    private List<Manga> getMangaPages(HttpURLConnection connection) throws IOException {

        StringBuilder sb = getResult(connection);
        JSONObject fullObj = new JSONObject(sb.toString());
        hasNextPage = DAOImplApiHelper.getHasNextPage(fullObj);

        return DAOImplApiHelper.getDataFromPageApi(fullObj).stream().map(Manga::new).collect(Collectors.toList());

    }

    private Manga bindManga(JSONObject obj){

        return new Manga(DAOImplApiHelper.bind(obj));

    }

    public boolean hasNextPage() {

        return hasNextPage;

    }
}
