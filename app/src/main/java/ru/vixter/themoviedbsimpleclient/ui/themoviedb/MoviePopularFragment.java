package ru.vixter.themoviedbsimpleclient.ui.themoviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import ru.vixter.themoviedbsimpleclient.R;
import ru.vixter.themoviedbsimpleclient.model.themoviedb.ListMovie;
import ru.vixter.themoviedbsimpleclient.model.themoviedb.Movie;
import ru.vixter.themoviedbsimpleclient.network.themoviedb.Params;
import ru.vixter.themoviedbsimpleclient.ui.EndlessRecyclerViewScrollListener;

/**
 * Created by vixter on 17.01.16.
 */
public class MoviePopularFragment extends MovieBaseFragment{

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView(view);
        if (isActiveNetwork) restRequest.getPopularMovies(1, Params.PARAM_SORT_BY_POPULARITY).enqueue(this);
        else movieAdapter.addAll(new ArrayList<Movie>(databaseHelper.getMoviesByPopularity(0)));

    }

    private void initRecyclerView(View view){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if (isActiveNetwork){
                    restRequest.getPopularMovies(page + 1, Params.PARAM_SORT_BY_POPULARITY).enqueue(MoviePopularFragment.this);
                } else {
                    movieAdapter.addAll(new ArrayList<Movie>(databaseHelper.getMoviesByPopularity(page)));
                }
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(view.getContext(), MovieInformationActivity.class)
                        .putExtra(Constants.EXTRA_MOVIE_ID, movieAdapter.getMovieID(position))
                        .putExtra(Constants.EXTRA_MOVIE_TITLE, movieAdapter.getMovieTitle(position)));
            }

        }));
    }

    @Override
    public void onSuccess(List<Movie> list) {
        movieAdapter.addAll(list);
        databaseHelper.addAllMovies(list);
    }
}
