package ru.vixter.themoviedbsimpleclient;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.facebook.drawee.backends.pipeline.Fresco;

import ru.vixter.themoviedbsimpleclient.network.themoviedb.Params;
import ru.vixter.themoviedbsimpleclient.network.themoviedb.RequestManager;

/**
 * Created by vixter on 17.01.16.
 */
public class SClientApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this.getApplicationContext());
        Params.API_KEY_VALUE = getString(R.string.themoviedb_api_key);
        RequestManager.getInstance().initialize();

    }

    public static boolean isConnectingToInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}
