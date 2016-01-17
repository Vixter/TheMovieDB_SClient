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
import ru.vixter.themoviedbsimpleclient.network.themoviedb.MoviesService;
import ru.vixter.themoviedbsimpleclient.network.themoviedb.RequestManager;

/**
 * Created by vixter on 17.01.16.
 */
public class MovieBaseFragment extends Fragment implements Callback<ListMovie> {

    boolean isActiveNetwork;
    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    MoviesService restRequest;
    MovieDatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        initFragment(view);
        return view;
    }

    private void initFragment(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RequestManager config = new RequestManager(getString(R.string.themoviedb_api_key));
        databaseHelper = MovieDatabaseHelper.getInstance(getContext());
        restRequest = config.getRetrofit().create(MoviesService.class);
        movieAdapter = new MovieAdapter(getContext());
        isActiveNetwork = SClientApplication.isConnectingToInternet(getContext().getApplicationContext());
    }

    @Override
    public void onResponse(Response<ListMovie> response, Retrofit retrofit) {

        switch (response.code()){
            case 200:
                // TODO: 15.01.16 make base callback when define onResponse and make onSuccess or onFailure
                onSuccess(response.body().getResults());
                break;
            case 503:
                onFailure(new Throwable("Service offline: This service is temporarily offline, try again later"));
                break;
            case 400:
                onFailure(new Throwable("Invalid page: Pages start at 1 and max at 1000. They are expected to be an integer."));
                break;
        }

    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(getContext().getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    public void onSuccess(List<Movie> list ){

    }


}
