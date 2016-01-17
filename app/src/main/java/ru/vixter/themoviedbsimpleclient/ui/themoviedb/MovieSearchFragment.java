package ru.vixter.themoviedbsimpleclient.ui.themoviedb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import java.util.List;

import ru.vixter.themoviedbsimpleclient.model.themoviedb.Movie;

/**
 * Created by vixter on 17.01.16.
 */
public class MovieSearchFragment extends MovieBaseFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        if (isActiveNetwork  ) restRequest.searchByTitle(getArguments().getString("query")).enqueue(this);
        else movieAdapter.addAll(databaseHelper.serchByTitle(getArguments().getString("query")));

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

    @Override
    public void onSuccess(List<Movie> list) {
        movieAdapter.addAll(list);
        databaseHelper.addAllMovies(list);
    }

}
