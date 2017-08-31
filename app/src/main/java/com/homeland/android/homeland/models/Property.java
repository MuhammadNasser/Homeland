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

public class Property implements Serializable {

    private String id;
    private String title;
    private String code;
    private String description;
    private String facebook;
    private String twitter;
    private String instagram;
    private String price;
    private ArrayList<Image> imagesLinks = new ArrayList<>();

    public Property(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.optString("id");
        this.title = jsonObject.optString("title");
        this.code = jsonObject.optString("code");
        this.description = jsonObject.optString("description");
        this.facebook = jsonObject.optString("facebook");
        this.twitter = jsonObject.optString("twitter");
        this.instagram = jsonObject.optString("instagram");
        this.price = jsonObject.optString("price");

        JSONArray images = jsonObject.getJSONArray("images");
        for (int i = 0; i < images.length(); i++) {
            JSONObject imageItem = images.optJSONObject(i);
            Image image = new Image(imageItem);
            imagesLinks.add(image);
        }
    }

    public Property() {
    }

    public Property(Cursor cursor) {

        this.id = cursor.getString(DataContract.DataEntry.COLUMN_ID_PROPERTY_CURSOR);
        this.title = cursor.getString(DataContract.DataEntry.COLUMN_TITLE_PROPERTY_CURSOR);
        this.code = cursor.getString(DataContract.DataEntry.COLUMN_CODE_PROPERTY_CURSOR);
        this.description = cursor.getString(DataContract.DataEntry.COLUMN_DESCRIPTION_PROPERTY_CURSOR);
        this.facebook = cursor.getString(DataContract.DataEntry.COLUMN_FACEBOOK_PROPERTY_CURSOR);
        this.twitter = cursor.getString(DataContract.DataEntry.COLUMN_TWITTER_PROPERTY_CURSOR);
        this.instagram = cursor.getString(DataContract.DataEntry.COLUMN_INSTAGRAM_PROPERTY_CURSOR);
        this.price = cursor.getString(DataContract.DataEntry.COLUMN_PRICE_PROPERTY_CURSOR);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getPrice() {
        return price;
    }

    public ArrayList<Image> getImagesLinks() {
        return imagesLinks;
    }
}
