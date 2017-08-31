package com.homeland.android.homeland.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.homeland.android.homeland.DetailsActivity;
import com.homeland.android.homeland.MainActivity;
import com.homeland.android.homeland.R;
import com.homeland.android.homeland.data.DataContract;
import com.homeland.android.homeland.models.Car;
import com.homeland.android.homeland.server.ContentVolley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.homeland.android.homeland.DetailsActivity.IS_CAR;
import static com.homeland.android.homeland.DetailsActivity.ITEM_TYPE;

/**
 * Created by Muhammad on 7/29/2017
 */

public class CarsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    final int LOADER_ID = 1;
    private final String TAG = CarsFragment.class.getSimpleName();

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_properties, container, false);

        activity = (MainActivity) getActivity();

        ButterKnife.bind(this, view);

        Content content = new Content();
        content.getCars();

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        activity.isLoading(true);
        return new CursorLoader(activity, DataContract.DataEntry.CAR_URI,
                DataContract.DataEntry.CAR_COLUMNS, null, null, DataContract.DataEntry.COLUMN_CAR_ID);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        activity.isLoading(false);
        ArrayList<Car> cars = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Car car = new Car(cursor);
                cars.add(car);
            } while (cursor.moveToNext());
            cursor.close();
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new CarsAdapter(activity, cars));
    }


    public class CarsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Car> cars;


        private LayoutInflater inflater;

        public CarsAdapter(Activity activity, ArrayList<Car> cars) {
            inflater = activity.getLayoutInflater();
            this.cars = cars;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view;
            RecyclerView.ViewHolder holder;

            //inflater your layout and pass it to view holder
            view = inflater.inflate(R.layout.cars_list_item, viewGroup, false);
            holder = new ItemHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
            layoutParams.setFullSpan(false);
            layoutParams.height = (int) (recyclerView.getMeasuredWidth() * 0.75);

            ItemHolder itemHolder = (ItemHolder) viewHolder;
            itemHolder.setDetails(cars.get(position));
        }

        @Override
        public int getItemCount() {
            return cars.size();
        }


        public class ItemHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.imageViewCover)
            ImageView imageViewCover;

            @BindView(R.id.textViewDescription)
            TextView textViewDescription;

            Car car;

            public ItemHolder(View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, DetailsActivity.class);
                        intent.putExtra(IS_CAR, car);
                        intent.putExtra(ITEM_TYPE, DetailsActivity.DetailsType.Cars);
                        startActivity(intent);
                    }
                });
            }

            private void setDetails(Car car) {
                this.car = car;

                if (!car.getImages().isEmpty()) {
                    Picasso.with(activity).load(car.getImages().get(0).getImageUrl()).
                            placeholder(R.drawable.placeholder).
                            error(R.drawable.ic_warning).
                            into(imageViewCover);
                } else {
                    imageViewCover.setImageResource(R.drawable.ic_warning);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    textViewDescription.setText(Html.fromHtml(car.getDescription(), Html.FROM_HTML_OPTION_USE_CSS_COLORS));
                } else {
                    //noinspection deprecation
                    textViewDescription.setText(Html.fromHtml(car.getDescription()));
                }
            }

        }
    }

    private class Content extends ContentVolley {

        public Content() {
            super(TAG, activity);
        }

        @Override
        protected void onPreExecute(ActionType actionType) {
            activity.isLoading(true);
        }

        @Override
        protected void onPostExecuteGetCars(ActionType actionType, boolean success, String message, ArrayList<Car> cars) {
            activity.isLoading(false);
            if (success) {
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(false);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(new CarsAdapter(activity, cars));
            } else {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

                if (message.contains("No active internet connection")) {
                    activity.getSupportLoaderManager().restartLoader(LOADER_ID, null, CarsFragment.this);
                }
            }
        }
    }
}
