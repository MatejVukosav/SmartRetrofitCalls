package vuki.com.retrofit2smartcalls.network;

public class ApiManager extends AbstractApiManager {

    private static final String BASE_URL = "https://api.nytimes.com";

    private static ApiManager managerInstance;

    public static ApiManager getInstance() {
        if( managerInstance == null ) {
            managerInstance = new ApiManager();
        }
        return managerInstance;
    }

    private ApiManager() {
        super( BASE_URL );
    }

}