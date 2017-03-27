package vuki.com.retrofit2smartcalls.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vuki.com.retrofit2smartcalls.network.deserializers.DateDeserializers;
import vuki.com.retrofit2smartcalls.retrofit.ErrorHandlingCallAdapterFactory;

/**
 * Created by Vuki on 27.3.2017..
 */

public abstract class AbstractApiManager implements ApiManagerInterface {
    protected static final String TAG = "Network";
    private String baseUrl;

    private static ApiManagerService service;
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter( Date.class, new DateDeserializers() )
            .create();

    protected AbstractApiManager( String baseUrl ) {
        this.baseUrl = baseUrl;

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor( new LoggingInterceptor() )
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( this.baseUrl )
                .client( client )
                .addCallAdapterFactory( new ErrorHandlingCallAdapterFactory() )
                .addConverterFactory( GsonConverterFactory.create( gson ) )
                .build();
        service = retrofit.create( ApiManagerService.class );
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public ApiManagerService getService() {
        return service;
    }

    public static Gson getGson() {
        return gson;
    }
}
