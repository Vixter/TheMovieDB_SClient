package ru.vixter.themoviedbsimpleclient.network.themoviedb;

/**
 * Created by vixter on 17.01.16.
 */
public class Params {
    public static final String PARAM_YEAR = "year";
    public static final String PARAM_SORT = "sort_by";
    public static final String PARAM_SORT_BY_POPULARITY = "popularity.desc";
    public static final String PARAM_RELEASE_DATE_gte = "primary_release_date.gte";
    public static final String PARAM_RELEASE_DATE_lte = "primary_release_date.lte";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_LANGUAGE = "language";
    public static final String PARAM_ID = "id";
    public static final String PARAM_ADULT = "include_adult";
    public static final String PARAM_API_KEY = "api_key";
    public static final String BASE_URL = "http://api.themoviedb.org/";
    public static final String IMAGE_PATH_URL = "http://image.tmdb.org/t/p/w185";

    public static String API_KEY_VALUE;
}
