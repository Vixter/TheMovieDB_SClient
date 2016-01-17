package ru.vixter.themoviedbsimpleclient.ui.themoviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import ru.vixter.themoviedbsimpleclient.R;
import ru.vixter.themoviedbsimpleclient.model.themoviedb.ListMovie;
import ru.vixter.themoviedbsimpleclient.model.themoviedb.Movie;
import ru.vixter.themoviedbsimpleclient.network.themoviedb.Params;
import ru.vixter.themoviedbsimpleclient.ui.EndlessRecyclerViewScrollListener;
import ru.vixter.themoviedbsimpleclient.utils.Date;

/**
 * Created by vixter on 17.01.16.
 */
public class MovieDiscoverFragment extends MovieBaseFragment {

    Calendar c = Calendar.getInstance();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        c.setTime(new java.util.Date());
        c.add(Calendar.MONTH, -1);

        initRecyclerView(view);
        if (isActiveNetwork)restRequest.getResentMovies(1,
                Date.DateToUTCString(c.getTime(), "yyyy'-'MM'-'dd"),
                Date.DateToUTCString(new java.util.Date(), "yyyy'-'MM'-'dd")).enqueue(MovieDiscoverFragment.this);
        else movieAdapter.addAll(new ArrayList<Movie>(databaseHelper.getMoviesToDescribe(0)));

    }

    private void initRecyclerView(View view){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (isActiveNetwork) {
                    restRequest.getResentMovies(1,
                            Date.DateToUTCString(c.getTime(), "yyyy'-'MM'-'dd"),
                            Date.DateToUTCString(new java.util.Date(), "yyyy'-'MM'-'dd")).enqueue(MovieDiscoverFragment.this);
                } else {
                    movieAdapter.addAll(new ArrayList<Movie>(databaseHelper.getMoviesToDescribe(page)));
                }
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Movie movie = movieAdapter.getItem(position);
                startActivity(new Intent(view.getContext(), MovieInformationActivity.class).putExtra(Constants.EXTRA_MOVIE_PARCELABLE, movie));
            }

        }));
    }

    @Override
    public void onSuccess(List<Movie> list) {
        movieAdapter.addAll(list);
        databaseHelper.addAllMovies(list);
    }


}
