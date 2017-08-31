package com.homeland.android.homeland.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Muhammad on 8/19/2017
 */

public class Service implements Serializable {

    private String id;
    private String title;
    private String description;
    private String image;

    public Service(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.optString("id");
        this.title = jsonObject.optString("title");
        this.description = jsonObject.optString("description");
        this.image = jsonObject.optString("image");
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
