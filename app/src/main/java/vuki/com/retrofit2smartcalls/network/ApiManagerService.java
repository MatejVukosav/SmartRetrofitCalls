package vuki.com.retrofit2smartcalls.network;

import retrofit2.http.GET;
import vuki.com.retrofit2smartcalls.retrofit.RestCall;

/**
 * Created by Vuki on 13.2.2016..
 */
public interface ApiManagerService {

    final String apiKey = "160529a003a94985900c216bfb4ef232";

    @GET("/svc/movies/v2/reviews/search.json?api-key=" + apiKey)
    RestCall<Void> getMessage();
}
