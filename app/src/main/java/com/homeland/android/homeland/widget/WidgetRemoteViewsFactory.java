package com.homeland.android.homeland.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
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
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = manager.getAppWidgetIds(new ComponentName(context, HomelandWidgetProvider.class));
        manager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetListView);
    }

    @Override
    public void onDataSetChanged() {

        try {
            new FavoritesProperties(context).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        int size = properties.size();
        return size;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);

        views.setTextViewText(R.id.textViewTitle, properties.get(position).getTitle());
        views.setTextViewText(R.id.textViewCode, properties.get(position).getCode());
        views.setTextViewText(R.id.textViewDescription, properties.get(position).getDescription());

        Property property = properties.get(position);

        Intent intent = new Intent();
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
        return 2;
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
        protected void onPostExecute(ArrayList<Property> properties) {
            if (properties != null) {
                WidgetRemoteViewsFactory.this.properties = properties;
            }
        }
    }

}
