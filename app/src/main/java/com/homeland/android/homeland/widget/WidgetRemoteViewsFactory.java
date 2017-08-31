package com.homeland.android.homeland.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.homeland.android.homeland.DetailsActivity;
import com.homeland.android.homeland.R;
import com.homeland.android.homeland.data.DataContract;
import com.homeland.android.homeland.models.Property;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.homeland.android.homeland.DetailsActivity.IS_PROPERTY;
import static com.homeland.android.homeland.DetailsActivity.ITEM_TYPE;
import static com.homeland.android.homeland.data.DataContract.DataEntry.PROPERTY_COLUMNS;


public class WidgetRemoteViewsFactory implements RemoteViewsFactory {
    private Context context;
    private ArrayList<Property> properties = new ArrayList<>();

    public WidgetRemoteViewsFactory(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        try {
            new FavoritesProperties(context).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("ExecutionException: ", String.valueOf(e));
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return properties.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.home_list_item);

        views.setImageViewResource(R.id.imageViewCover, R.drawable.bg_property_1);

        views.setTextViewText(R.id.textViewTitle, properties.get(position).getTitle());
        views.setTextViewText(R.id.textViewPrice, properties.get(position).getPrice());
        views.setTextViewText(R.id.textViewCode, properties.get(position).getCode());
        views.setTextViewText(R.id.textViewDescription, properties.get(position).getDescription());

        Property property = properties.get(position);

        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(IS_PROPERTY, property);
        intent.putExtra(ITEM_TYPE, DetailsActivity.DetailsType.Properties);
        views.setOnClickFillInIntent(R.id.relativeLayout, intent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return properties.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private class FavoritesProperties extends AsyncTask<Void, Void, ArrayList<Property>> {

        private Context mContext;

        /**
         * {@link java.lang.reflect.Constructor}
         *
         * @param context Activity Context.
         */
        private FavoritesProperties(Context context) {
            mContext = context;
        }

        /**
         * Extracts data from the Cursor database and returns an Array of Property objects.
         *
         * @param cursor cursor of database
         * @return ArrayList of Property objects
         */
        private ArrayList<Property> getFavoritePropertysDataFromCursor(Cursor cursor) {
            ArrayList<Property> Propertys = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Property Property = new Property(cursor);
                    Propertys.add(Property);
                } while (cursor.moveToNext());
                cursor.close();
            }
            return Propertys;
        }

        @Override
        protected ArrayList<Property> doInBackground(Void... params) {
            Cursor cursor = mContext.getContentResolver().query(
                    DataContract.DataEntry.PROPERTY_URI,
                    PROPERTY_COLUMNS,
                    null,
                    null,
                    null
            );
            return getFavoritePropertysDataFromCursor(cursor);
        }

        @Override
        protected void onPostExecute(ArrayList<Property> Propertys) {
            if (Propertys != null) {
                WidgetRemoteViewsFactory.this.properties = properties;
            }
        }
    }

}
