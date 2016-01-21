package ru.vixter.themoviedbsimpleclient.ui.themoviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import ru.vixter.themoviedbsimpleclient.R;
import ru.vixter.themoviedbsimpleclient.SClientApplication;
import ru.vixter.themoviedbsimpleclient.db.MovieDatabaseHelper;
import ru.vixter.themoviedbsimpleclient.model.themoviedb.Movie;
import ru.vixter.themoviedbsimpleclient.network.themoviedb.BaseCallback;
import ru.vixter.themoviedbsimpleclient.network.themoviedb.MoviesService;
import ru.vixter.themoviedbsimpleclient.network.themoviedb.RequestManager;

/**
 * Created by vixter on 17.01.16.
 */
public class MovieSearchFragment extends MovieBaseFragment {

    boolean isActiveNetwork;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    MoviesService restRequest;
    MovieDatabaseHelper databaseHelper;
    BaseCallback baseCallback;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragment(view);
        initRecyclerView(view);
        if (isActiveNetwork  ) restRequest.searchByTitle(getArguments().getString("query")).enqueue(baseCallback);
        else movieAdapter.addAll(databaseHelper.serchByTitle(getArguments().getString("query")));
    }

    private void initFragment(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        databaseHelper = MovieDatabaseHelper.getInstance(getContext());
        restRequest = RequestManager.getMoviesService();
        movieAdapter = new MovieAdapter(getContext());
        isActiveNetwork = SClientApplication.isConnectingToInternet(getContext().getApplicationContext());
        baseCallback = new BaseCallback() {
            @Override
            public void onSuccess(List<Movie> list) {
                movieAdapter.addAll(list);
                databaseHelper.addAllMovies(list);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void initRecyclerView(View view){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Movie movie = movieAdapter.getItem(position);
                startActivity(new Intent(view.getContext(), MovieInformationActivity.class).putExtra(Constants.EXTRA_MOVIE_PARCELABLE, movie));
            }

        }));
    }
}
