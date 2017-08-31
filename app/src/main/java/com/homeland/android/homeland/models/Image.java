package com.homeland.android.homeland.models;

import android.database.Cursor;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Muhammad on 8/30/2017
 */

public class Image implements Serializable {

    String id;
    String imageUrl;

    public Image(JSONObject jsonObject) {
        this.id = jsonObject.optString("id");
        this.imageUrl = jsonObject.optString("image_path");
    }

    public Image(Cursor cursor) {
    }

    public Image() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
