package ru.vixter.themoviedbsimpleclient.network.themoviedb;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import ru.vixter.themoviedbsimpleclient.model.themoviedb.ListMovie;

/**
 * Created by vixter on 17.01.16.
 */
public interface MoviesService {

    @GET("/3/discover/movie")
    Call<ListMovie> getResentMovies(@Query(Params.PARAM_PAGE) int page,
                                    @Query(Params.PARAM_RELEASE_DATE_gte) String date_gte,
                                    @Query(Params.PARAM_RELEASE_DATE_lte) String date_lte);

    @GET("/3/discover/movie")
    Call<ListMovie> getPopularMovies(@Query(Params.PARAM_PAGE) int page,
                                     @Query(Params.PARAM_SORT) String sort_by);

    @GET("3/search/movie")
    Call<ListMovie> searchByTitle(@Query("query") String query);
}