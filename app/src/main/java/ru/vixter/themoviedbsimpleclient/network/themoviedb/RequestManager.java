package ru.vixter.themoviedbsimpleclient.network.themoviedb;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by vixter on 17.01.16.
 */
public class RequestManager {
    private static RequestManager requestManager;
    private final String API_KEY;
    private Retrofit retrofit;
    private final MoviesService moviesService;

    // TODO: 15.01.16 make singleton and initialize

    public static void initialize(String api_key){
        requestManager = new RequestManager(api_key);
    }

    private RequestManager(String API_KEY){
        this.API_KEY = API_KEY;
        retrofit = new Retrofit.Builder().baseUrl(Params.BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OkHttpClient client = retrofit.client();
        client.interceptors().add(new AuthInterceptor(API_KEY));

        moviesService = retrofit.create(MoviesService.class);
    }

    public static MoviesService getMoviesService() {
        return requestManager.moviesService;
    }

    private static class AuthInterceptor implements Interceptor {
        private final String API_KEY;

        AuthInterceptor(String API_KEY) {
            this.API_KEY = API_KEY;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.httpUrl()
                    .newBuilder()
                    .addQueryParameter(Params.PARAM_API_KEY, API_KEY)
                    .build();

            request = request.newBuilder().url(url).build();
            Response response = chain.proceed(request);
            return response;
        }

    }

}