package com.sergio.main.model.repositories.api.dao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public interface DAO {

    default StringBuilder getResult(HttpURLConnection connection) throws IOException {

        int responseCode = connection.getResponseCode();

        if (responseCode != 200){

            throw new IOException();

        }

        BufferedInputStream buffer = new BufferedInputStream(connection.getInputStream());
        StringBuilder sb = new StringBuilder();
        int b;
        while ((b = buffer.read()) != -1){

            sb.append((char) b);
        }

        return sb;

    }


}
