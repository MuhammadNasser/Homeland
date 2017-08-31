package com.homeland.android.homeland.models;

import android.database.Cursor;

import com.homeland.android.homeland.data.DataContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Muhammad on 8/19/2017
 */

public class Car implements Serializable {

    private String id;
    private String description;
    private ArrayList<Image> images = new ArrayList<>();

    public Car(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.optString("id");
        this.description = jsonObject.optString("description");

        JSONArray images = jsonObject.getJSONArray("images");
        for (int i = 0; i < images.length(); i++) {
            JSONObject imageItem = images.optJSONObject(i);
            Image image = new Image(imageItem);
            this.images.add(image);
        }
    }

    public Car(Cursor cursor) {
        this.id = cursor.getString(DataContract.DataEntry.COLUMN_ID_CAR_CURSOR);
        this.description = cursor.getString(DataContract.DataEntry.COLUMN_DESCRIPTION_CAR_CURSOR);
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Image> getImages() {
        return images;
    }
}
