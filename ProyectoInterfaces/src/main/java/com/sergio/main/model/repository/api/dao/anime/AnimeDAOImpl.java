package com.sergio.main.model.repository.api.dao.anime;

import com.sergio.main.model.datasource.items.Anime;
import com.sergio.main.model.repository.api.APIConnection;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class AnimeDAOImpl implements AnimeDAO{

    private boolean hasNextPage;

    @Override
    public List<Anime> getPageAnime(int page) throws IOException {

        HttpURLConnection connection = APIConnection.getInstance().getAnimeConnection("?page=" + page);

        StringBuilder sb = getResult(connection);


        JSONObject fullObj = new JSONObject(sb.toString());
        hasNextPage = ((JSONObject)fullObj.get("pagination")).getBoolean("has_next_page");

        JSONArray jsonArray = fullObj.getJSONArray("data");

        List<Anime> animes = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){

            JSONObject obj = jsonArray.getJSONObject(i);
            Anime anime = new Anime();
            anime.setId(obj.getInt("mal_id"));
            anime.setName(obj.getString("title"));

            JSONObject image = (JSONObject) ((JSONObject) obj.get("images")).get("jpg");
           anime.setImage(new Image(image.getString("large_image_url")));
            animes.add(anime);
        }


        return animes;

    }

    @Override
    public Anime getAnimeByID(int id) {
        return null;
    }

    public boolean hasNextPage() {

        return hasNextPage;

    }

}
