package com.sergio.main.model.repositories.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class APIConnection {

    private final String ANIME_DEFAULT_URL = "https://api.jikan.moe/v4/anime";
    private final String MANGA_DEFAULT_URL = "https://api.jikan.moe/v4/manga";

    private HttpURLConnection connection;

    private static APIConnection instance;

    public static APIConnection getInstance(){

        if(instance == null){

            instance = new APIConnection();

        }

        return instance;

    }

    private void createConnection(String dirURL) throws IOException {

        URL url = new URL(dirURL);

        connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.connect();

    }

    public HttpURLConnection getAnimeConnection(String parameters) throws IOException {

        createConnection(ANIME_DEFAULT_URL + parameters);
        return connection;

    }

    public HttpURLConnection getMangaConnection(String parameters) throws IOException {

        createConnection(MANGA_DEFAULT_URL + parameters);
        return connection;
    }

}
