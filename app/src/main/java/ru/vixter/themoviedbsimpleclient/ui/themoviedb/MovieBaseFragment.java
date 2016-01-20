package ru.vixter.themoviedbsimpleclient.ui.themoviedb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import ru.vixter.themoviedbsimpleclient.R;
import ru.vixter.themoviedbsimpleclient.SClientApplication;
import ru.vixter.themoviedbsimpleclient.db.MovieDatabaseHelper;
import ru.vixter.themoviedbsimpleclient.model.themoviedb.ListMovie;
import ru.vixter.themoviedbsimpleclient.model.themoviedb.Movie;
import ru.vixter.themoviedbsimpleclient.network.themoviedb.BaseCallback;
import ru.vixter.themoviedbsimpleclient.network.themoviedb.MoviesService;
import ru.vixter.themoviedbsimpleclient.network.themoviedb.RequestManager;

/**
 * Created by vixter on 17.01.16.
 */
public class MovieBaseFragment extends Fragment{

    boolean isActiveNetwork;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    MoviesService restRequest;
    MovieDatabaseHelper databaseHelper;
    BaseCallback baseCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }



}
