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

    // TODO: 15.01.16 make singleton and initialize

    private static class RequestManagerHolder{
        private static MoviesService moviesService;
    }

    public static void initialize(String API_KEY){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Params.BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OkHttpClient client = retrofit.client();
        client.interceptors().add(new AuthInterceptor(API_KEY));

        RequestManagerHolder.moviesService = retrofit.create(MoviesService.class);
    }

    public static MoviesService getMoviesService() {
        if(RequestManagerHolder.moviesService == null) new NullPointerException("RequestManager is not initialise");
        return RequestManagerHolder.moviesService;
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