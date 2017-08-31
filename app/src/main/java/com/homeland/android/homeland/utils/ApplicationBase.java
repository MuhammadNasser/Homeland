package com.homeland.android.homeland.utils;

import android.content.Context;
import android.database.Cursor;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.homeland.android.homeland.DetailsActivity;
import com.homeland.android.homeland.data.DataContract;

/**
 * Created by Muhammad on 07/29/2017
 */
public class ApplicationBase extends MultiDexApplication {


    private static ApplicationBase mInstance;

    public static synchronized ApplicationBase getInstance() {
        return mInstance;
    }

    public int isSaved(Context context, String id, DetailsActivity.DetailsType detailsType) {
        int numRows = 0;
        switch (detailsType) {
            case Properties:
                Cursor cursor = context.getContentResolver().query(
                        DataContract.DataEntry.PROPERTY_URI,
                        null,   // projection
                        DataContract.DataEntry.COLUMN_PROPERTY_ID + " = ?", // selection
                        new String[]{id},   // selectionArgs
                        null    // sort order
                );
                if (cursor != null) {
                    numRows = cursor.getCount();
                    cursor.close();
                }
                break;
            case Cars:
                cursor = context.getContentResolver().query(
                        DataContract.DataEntry.CAR_URI,
                        null,   // projection
                        DataContract.DataEntry.COLUMN_CAR_ID + " = ?", // selection
                        new String[]{id},   // selectionArgs
                        null    // sort order
                );
                if (cursor != null) {
                    numRows = cursor.getCount();
                    cursor.close();
                }
                break;
        }

        return numRows;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public String getRegistrationToken() {
        FirebaseInstanceId firebaseInstanceId = null;
        try {
            firebaseInstanceId = FirebaseInstanceId.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String token = null;
        if (firebaseInstanceId != null) {
            token = firebaseInstanceId.getToken();
            Log.e("FireBaseRegistration", "getRegistrationToken: " + token);
        } else {
            Log.e("FireBaseRegistration", "getRegistrationToken: firebaseInstanceId is null");
        }
        return token;
    }


}
