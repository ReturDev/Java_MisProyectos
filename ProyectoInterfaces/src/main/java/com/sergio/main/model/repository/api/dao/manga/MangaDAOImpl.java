package com.sergio.main.model.repository.api.dao.manga;

import com.sergio.main.model.datasource.items.Anime;
import com.sergio.main.model.datasource.items.Manga;
import com.sergio.main.model.repository.api.APIConnection;
import com.sergio.main.model.util.DAOImplApiHelper;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MangaDAOImpl implements MangaDAO{

    private boolean hasNextPage;

    @Override
    public List<Manga> getPageManga(int page) throws IOException {

        HttpURLConnection connection = APIConnection.getInstance().getMangaConnection("?page=" + page);
        StringBuilder sb = getResult(connection);
        JSONObject fullObj = new JSONObject(sb.toString());
        hasNextPage = DAOImplApiHelper.getHasNextPage(fullObj);

        return DAOImplApiHelper.getDataFromPageApi(fullObj).stream().map(Manga::new).collect(Collectors.toList());

    }

    @Override
    public Manga getMangaByID(int id) throws IOException {

        HttpURLConnection connection = APIConnection.getInstance().getMangaConnection("/"+ id);
        StringBuilder sb = getResult(connection);
        JSONObject jsonObject = (JSONObject) new JSONObject(sb.toString()).get(DAOImplApiHelper.DATA_TAG);

        return bindManga(jsonObject);

    }

    private Manga bindManga(JSONObject obj){

        return new Manga(DAOImplApiHelper.bind(obj));

    }

    public boolean hasNextPage() {

        return hasNextPage;

    }
}
