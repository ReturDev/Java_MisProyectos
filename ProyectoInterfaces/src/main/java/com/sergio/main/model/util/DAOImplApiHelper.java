package com.sergio.main.model.util;


import com.sergio.main.model.datasource.items.VisualWork;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DAOImplApiHelper {

    private static final String PAGINATION_TAG = "pagination";
    public static final String DATA_TAG = "data";
    private static final String ID_TAG = "mal_id";
    private static final String TITLE_TAG = "title";
    private static final String IMAGE_TAG = "images";
    private static final String IMAGE_FORMAT_TAG = "jpg";
    private static final String IMAGE_TYPE_TAG = "large_image_url";

    private DAOImplApiHelper(){}


    public static List<VisualWork> getDataFromPageApi(JSONObject fullObj) throws IOException {

        JSONArray jsonArray = fullObj.getJSONArray(DATA_TAG);

        List<VisualWork> visualWorks = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){

            JSONObject obj = jsonArray.getJSONObject(i);
            visualWorks.add(bind(obj));

        }

        return visualWorks;

    }

    public static boolean getHasNextPage(JSONObject fullObj){

        return ((JSONObject)fullObj.get(PAGINATION_TAG)).getBoolean("has_next_page");

    }

    public static VisualWork bind(JSONObject obj){

        VisualWork visualWork = new VisualWork();
        visualWork.setId(obj.getInt(ID_TAG));
        visualWork.setName(obj.getString(TITLE_TAG));

        JSONObject image = (JSONObject) ((JSONObject) obj.get(IMAGE_TAG)).get(IMAGE_FORMAT_TAG);
        visualWork.setImage(new Image(image.getString(IMAGE_TYPE_TAG)));

        return visualWork;

    }

}
