package ru.vixter.themoviedbsimpleclient.model.themoviedb;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by winfe on 29.12.2015.
 */
public class Movie implements Parcelable {

    @SerializedName("id") private String id;
    @SerializedName("title") private String title;
    @SerializedName("original_language") private String original_language;
    @SerializedName("backdrop_path") private String backdrop_path;
    @SerializedName("adult") private String adult;
    @SerializedName("overview") private String overview;
    @SerializedName("release_date") private String release_date;
    @SerializedName("original_title") private String original_title;
    @SerializedName("popularity") private double popularity;
    @SerializedName("vote_average") private double vote_average;
    @SerializedName("vote_count") private int vote_count;
    @SerializedName("poster_path") private String poster_path;

    public Movie(){

    }

    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        original_language = in.readString();
        backdrop_path = in.readString();
        adult = in.readString();
        overview = in.readString();
        release_date = in.readString();
        original_title = in.readString();
        popularity = in.readDouble();
        vote_average = in.readDouble();
        vote_count = in.readInt();
        poster_path = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getId() {
        return id;
    }

    public Movie setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public Movie setOriginal_language(String original_language) {
        this.original_language = original_language;
        return this;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public Movie setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
        return this;
    }

    public String getAdult() {
        return adult;
    }

    public Movie setAdult(String adult) {
        this.adult = adult;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public Movie setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Movie setRelease_date(String release_date) {
        this.release_date = release_date;
        return this;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public Movie setOriginal_title(String original_title) {
        this.original_title = original_title;
        return this;
    }

    public double getPopularity() {
        return popularity;
    }

    public Movie setPopularity(double popularity) {
        this.popularity = popularity;
        return this;
    }

    public double getVote_average() {
        return vote_average;
    }

    public Movie setVote_average(double vote_average) {
        this.vote_average = vote_average;
        return this;
    }

    public int getVote_count() {
        return vote_count;
    }

    public Movie setVote_count(int vote_count) {
        this.vote_count = vote_count;
        return this;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public Movie setPoster_path(String poster_path) {
        this.poster_path = poster_path;
        return this;
    }


    @Override
    public String toString()
    {
        return "Movie [vote_average = "+vote_average+", backdrop_path = "+backdrop_path+", adult = "+adult+", id = "+id+", title = "+title+", overview = "+overview+", original_language = "+original_language+", release_date = "+release_date+", original_title = "+original_title+", vote_count = "+vote_count+", poster_path = "+poster_path+", popularity = "+popularity+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(original_language);
        dest.writeString(backdrop_path);
        dest.writeString(adult);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(original_title);
        dest.writeString(poster_path);
        dest.writeInt(vote_count);
        dest.writeDouble(popularity);
        dest.writeDouble(vote_average);
        dest.writeString(id);
    }
}