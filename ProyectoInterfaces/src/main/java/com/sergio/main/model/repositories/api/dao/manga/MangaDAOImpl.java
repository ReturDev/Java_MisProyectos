package com.sergio.main.model.repositories.api.dao.manga;

import com.sergio.main.model.datasource.items.Manga;
import com.sergio.main.model.repositories.api.APIConnection;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MangaDAOImpl implements MangaDAO{

    private boolean hasNextPage;

    @Override
    public List<Manga> getPageManga(int page) throws IOException {

        HttpURLConnection connection = APIConnection.getInstance().getMangaConnection("?page=" + page);

        StringBuilder sb = getResult(connection);

        JSONObject fullObj = new JSONObject(sb.toString());
        hasNextPage = ((JSONObject)fullObj.get("pagination")).getBoolean("has_next_page");

        JSONArray jsonArray = fullObj.getJSONArray("data");

        List<Manga> mangas = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){

            JSONObject obj = jsonArray.getJSONObject(i);
            Manga manga = new Manga();
            manga.setId(obj.getInt("mal_id"));
            manga.setName(obj.getString("title"));

            JSONObject image = (JSONObject) ((JSONObject) obj.get("images")).get("jpg");
            manga.setImage(new Image(image.getString("large_image_url")));
            mangas.add(manga);
        }


        return mangas;

    }

    @Override
    public Manga getMangaByID(int id) {
        return null;
    }

    public boolean hasNextPage() {

        return hasNextPage;

    }
}
