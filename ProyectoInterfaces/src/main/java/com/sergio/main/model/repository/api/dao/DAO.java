package com.sergio.main.model.repository.api.dao;

import com.sergio.main.model.datasource.dialogs.alerts.AlertCreator;
import javafx.scene.control.Alert;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public interface DAO {

    default StringBuilder getResult(HttpURLConnection connection) throws IOException {

        int responseCode = connection.getResponseCode();



        if (responseCode == 429){

            throw new IOException("Demasiadas peticiones por minuto/segundo.");

        }

        if (responseCode != 200){

            throw new IOException("Error al contactar con la API");

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
