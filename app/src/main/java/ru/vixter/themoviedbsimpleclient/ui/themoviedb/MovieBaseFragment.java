package ru.vixter.themoviedbsimpleclient.ui.themoviedb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.vixter.themoviedbsimpleclient.R;

/**
 * Created by vixter on 17.01.16.
 */
public class MovieBaseFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }



}
