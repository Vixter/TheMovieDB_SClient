package ru.vixter.themoviedbsimpleclient.ui.themoviedb;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import ru.vixter.themoviedbsimpleclient.R;
import ru.vixter.themoviedbsimpleclient.db.MovieDatabaseHelper;
import ru.vixter.themoviedbsimpleclient.model.themoviedb.Movie;
import ru.vixter.themoviedbsimpleclient.network.themoviedb.Params;

public class MovieInformationActivity extends AppCompatActivity {

    @Bind(R.id.titleText) TextView textViewTitle;
    @Bind(R.id.view_image) SimpleDraweeView imageView;
    @Bind(R.id.textScore) TextView textViewScore;
    @Bind(R.id.textAbout) TextView textViewAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);
        initActivity();
    }

    public void initActivity(){
        textViewTitle = (TextView) findViewById(R.id.titleText);
        textViewScore = (TextView) findViewById(R.id.textScore);
        textViewAbout = (TextView) findViewById(R.id.textAbout);
        imageView = (SimpleDraweeView) findViewById(R.id.view_image);

        // TODO: 15.01.16 extract to local public constants

        Movie movieItem = (Movie) getIntent().getParcelableExtra(Constants.EXTRA_MOVIE_PARCELABLE);

        textViewTitle.setText(movieItem.getTitle());
        textViewScore.setText(String.valueOf(movieItem.getVote_average()));
        textViewAbout.setText(movieItem.getOverview());
        // TODO: 15.01.16 make const
        imageView.setImageURI(Uri.withAppendedPath(Constants.basePosterUrl, movieItem.getBackdrop_path()));
    }
}
