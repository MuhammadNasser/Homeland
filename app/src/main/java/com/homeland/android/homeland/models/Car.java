package com.homeland.android.homeland.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.homeland.android.homeland.data.DataContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Muhammad on 8/19/2017
 */

public class Car implements Parcelable {

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

    protected Car(Parcel in) {
        id = in.readString();
        description = in.readString();
        images = in.createTypedArrayList(Image.CREATOR);
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(description);
        parcel.writeTypedList(images);
    }
}
