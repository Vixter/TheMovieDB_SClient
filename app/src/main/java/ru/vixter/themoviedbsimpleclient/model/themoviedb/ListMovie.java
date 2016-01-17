package ru.vixter.themoviedbsimpleclient.model.themoviedb;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by winfe on 29.12.2015.
 */
public class ListMovie {

    @SerializedName("results") private List<Movie> results;
    @SerializedName("page") private String page;
    @SerializedName("total_pages") private String total_pages;
    @SerializedName("total_results")private String total_results;

    public List<Movie> getResults() {
        return results;
    }

    public String getPage() {
        return page;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public String getTotal_results() {
        return total_results;
    }

    @Override
    public String toString()
    {
        return "ListMovie [results = "+results+", page = "+page+", total_pages = "+total_pages+", total_results = "+total_results+"]";
    }
}