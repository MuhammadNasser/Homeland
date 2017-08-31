package com.homeland.android.homeland.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Muhammad on 4/27/2017
 */
public class DataContract {

    public static final String CONTENT_AUTHORITY = "com.homeland.android.homeland";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PROPERTY = "property";
    public static final String PATH_CAR = "car";


    public static final class DataEntry implements BaseColumns {
        public static final Uri PROPERTY_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROPERTY).build();
        public static final Uri CAR_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CAR).build();

        public static final String CONTENT_PROPERTY =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROPERTY;
        public static final String CONTENT_CAR =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CAR;

        public static final String TABLE_PROPERTY = "property";
        public static final String TABLE_CARS = "cars";

        public static final String COLUMN_PROPERTY_ID = "id";
        public static final String COLUMN_CAR_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_FACEBOOK = "facebook";
        public static final String COLUMN_TWITTER = "twitter";
        public static final String COLUMN_INSTAGRAM = "instagram";
        public static final String COLUMN_PRICE = "price";

        public static final int COLUMN_ID_PROPERTY_CURSOR = 0;
        public static final int COLUMN_TITLE_PROPERTY_CURSOR = 1;
        public static final int COLUMN_CODE_PROPERTY_CURSOR = 2;
        public static final int COLUMN_DESCRIPTION_PROPERTY_CURSOR = 3;
        public static final int COLUMN_FACEBOOK_PROPERTY_CURSOR = 4;
        public static final int COLUMN_TWITTER_PROPERTY_CURSOR = 5;
        public static final int COLUMN_INSTAGRAM_PROPERTY_CURSOR = 6;
        public static final int COLUMN_PRICE_PROPERTY_CURSOR = 7;

        public static final int COLUMN_ID_CAR_CURSOR = 0;
        public static final int COLUMN_DESCRIPTION_CAR_CURSOR = 1;
        public static final int COLUMN_IMAGE_LINK_CAR_CURSOR = 2;

        public static final String[] PROPERTY_COLUMNS = {
                COLUMN_PROPERTY_ID,
                COLUMN_TITLE,
                COLUMN_CODE,
                COLUMN_DESCRIPTION,
                COLUMN_FACEBOOK,
                COLUMN_TWITTER,
                COLUMN_INSTAGRAM,
                COLUMN_PRICE
        };

        public static final String[] CAR_COLUMNS = {
                COLUMN_CAR_ID,
                COLUMN_DESCRIPTION
        };

        public static Uri buildPropertyUri(long id) {
            return ContentUris.withAppendedId(PROPERTY_URI, id);
        }

        public static Uri buildCarUri(long id) {
            return ContentUris.withAppendedId(CAR_URI, id);
        }
    }
}
