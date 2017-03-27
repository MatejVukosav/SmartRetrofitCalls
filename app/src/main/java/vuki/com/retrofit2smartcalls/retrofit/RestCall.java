package vuki.com.retrofit2smartcalls.retrofit;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by Vuki on 27.3.2017..
 */

public interface RestCall<T> {

    void enqueue(RestCallback<T> callback);

    Response<T> execute() throws IOException;

    RestCall<T> clone();

    void cancel();

}