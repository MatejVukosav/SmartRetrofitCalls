package vuki.com.retrofit2smartcalls.retrofit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

import okhttp3.MediaType;
import retrofit2.Response;
import vuki.com.retrofit2smartcalls.R;

/**
 * Created by Vuki on 27.3.2017..
 */

public abstract class RestCallbackImpl<T> implements RestCallback<T> {

    private static final int TOTAL_RETRIES = 3;
    private final RestCall<T> call;
    private int retryCount = 0;
    private Activity activity;

    public RestCallbackImpl( RestCall<T> call ) {
        this.call = call;
    }

    public RestCallbackImpl( RestCall<T> call, Activity activity ) {
        this.call = call;
        this.activity = activity;
    }

    @Override
    public void networkError( Throwable t ) {
        if( retryCount++ < TOTAL_RETRIES ) {
            new Handler( Looper.getMainLooper() ).postDelayed( new Runnable() {
                @Override
                public void run() {
                    retry();
                }
            }, 10000 );
        } else {
            showDialog( "Network Error!" );
        }
    }

    @Override
    public void onResponse( Response<T> response ) {
    }

    @Override
    public void serverError( Response<?> response ) {
        showDialog( "Server Error!" );
    }

    public void clientError( Response<?> response ) {

        switch( response.code() ) {
            case 401:
                unauthenticated( response );
                break;
            default:
                try {
                    if( response.errorBody() != null && isContentTypeJson( response ) ) {
                        JSONObject jObject = new JSONObject( response.errorBody().string() );
                        if( jObject.has( "message" ) ) {
                            String error = jObject.getString( "message" );
                            showDialog( error );
                        }
                    }
                } catch( Exception e ) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private boolean isContentTypeJson( Response response ) {

        if( response.errorBody().contentType() != null ) {
            if( response.errorBody().contentType().toString().equalsIgnoreCase( MediaType.parse( "application/json; charset=UTF-8" ).toString() ) ) {
                return true;
            } else {
                try {
                    throw new NullPointerException( "error body content type is not application/json" );
                } catch( NullPointerException n ) {
                    n.printStackTrace();
                }
            }
        } else {
            try {
                throw new NullPointerException( "error body is null" );
            } catch( NullPointerException n ) {
                n.printStackTrace();
            }
        }
        return false;
    }

    private void showDialog( final String msg ) {

        new Handler( Looper.getMainLooper() ).post( new Runnable() {

            public void run() {
                if( activity != null && !activity.isFinishing() ) {
                    new AlertDialog.Builder( activity )
                            .setTitle( R.string.server_error )
                            .setMessage( msg )
                            .setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                                public void onClick( DialogInterface dialog, int id ) {
                                    dialog.dismiss();
                                }
                            } ).show();
                }
            }
        } );
    }

    @Override
    public void unexpectedError( Throwable t ) {
        showDialog( "Unexpected Error!" );
    }

    @Override
    public void unauthenticated( Response<?> response ) {
    }

    private void retry() {
        call.clone().enqueue( this );
    }

}