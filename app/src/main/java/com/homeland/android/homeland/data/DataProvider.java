package com.homeland.android.homeland.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by Muhammad on 4/27/2017
 */
public class DataProvider extends ContentProvider {

    static final int PROPERTY = 100;
    static final int CAR = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DataBaseHelper mOpenHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, DataContract.PATH_PROPERTY, PROPERTY);
        matcher.addURI(authority, DataContract.PATH_CAR, CAR);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case PROPERTY:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.DataEntry.TABLE_PROPERTY, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case CAR:
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DataContract.DataEntry.TABLE_CARS, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PROPERTY:
                return DataContract.DataEntry.CONTENT_PROPERTY;
            case CAR:
                return DataContract.DataEntry.CONTENT_CAR;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case PROPERTY:
                long _id = db.insert(DataContract.DataEntry.TABLE_PROPERTY, null, values);
                if (_id > 0) {
                    returnUri = DataContract.DataEntry.buildPropertyUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case CAR:
                _id = db.insert(DataContract.DataEntry.TABLE_CARS, null, values);
                if (_id > 0) {
                    returnUri = DataContract.DataEntry.buildCarUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (sUriMatcher.match(uri)) {
            case PROPERTY:
                rowsDeleted = db.delete(
                        DataContract.DataEntry.TABLE_PROPERTY, selection, selectionArgs);
                break;
            case CAR:
                rowsDeleted = db.delete(
                        DataContract.DataEntry.TABLE_CARS, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsUpdated;

        switch (sUriMatcher.match(uri)) {
            case PROPERTY:
                rowsUpdated = db.update(DataContract.DataEntry.TABLE_PROPERTY, values, selection,
                        selectionArgs);
                break;
            case CAR:
                rowsUpdated = db.update(DataContract.DataEntry.TABLE_CARS, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
