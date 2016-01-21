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
public final class RequestManager {
    //Singleton
    private final static RequestManager INSTANCE = new RequestManager();

    private static final String API_KEY = "70177a9f8516400506ab579f4c9f443e";

    private MoviesService moviesService;

    private RequestManager(){
    }

    public void initialize(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Params.BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OkHttpClient client = retrofit.client();
        client.interceptors().add(new AuthInterceptor(API_KEY));

        INSTANCE.moviesService = retrofit.create(MoviesService.class);
    }

    public static RequestManager getInstance(){
         return INSTANCE;
    }

    public MoviesService getMoviesService() {
        if(INSTANCE.moviesService == null) {
            throw new IllegalStateException("RequestManager is not initialise");
        }
        return INSTANCE.moviesService;
    }

    private static final class AuthInterceptor implements Interceptor {
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