package ru.vixter.themoviedbsimpleclient.network.themoviedb;

import android.widget.Toast;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import ru.vixter.themoviedbsimpleclient.SClientApplication;
import ru.vixter.themoviedbsimpleclient.model.themoviedb.ListMovie;
import ru.vixter.themoviedbsimpleclient.model.themoviedb.Movie;

/**
 * Created by vixter on 19.01.16.
 */
public abstract class BaseCallback implements Callback<ListMovie> {

    @Override
    public void onResponse(Response<ListMovie> response, Retrofit retrofit) {

        switch (response.code()){
            case 200:
                if(response.body() != null)
                    onSuccess(response.body().getResults());
                else onFailure(new Throwable("Request body is null."));
                break;
            case 503:
                onFailure(new Throwable("Service offline: This service is temporarily offline, try again later"));
                break;
            case 400:
                onFailure(new Throwable("Invalid page: Pages start at 1 and max at 1000. They are expected to be an integer."));
                break;
        }

    }


    public abstract void onSuccess(List<Movie> list );

    public abstract void onFailure(Throwable t);
}
