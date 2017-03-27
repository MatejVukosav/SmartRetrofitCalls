package vuki.com.retrofit2smartcalls;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import retrofit2.Response;
import vuki.com.retrofit2smartcalls.network.ApiManager;
import vuki.com.retrofit2smartcalls.retrofit.RestCall;
import vuki.com.retrofit2smartcalls.retrofit.RestCallbackImpl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        RestCall<Void> restCall = ApiManager.getInstance().getService().getMessage();
        restCall.enqueue( new RestCallbackImpl<Void>( restCall, MainActivity.this ) {

            @Override
            public void success( Response<Void> response ) {
                new Handler( Looper.getMainLooper() ).post( new Runnable() {
                    public void run() {
                        Toast.makeText( MainActivity.this, "dada", Toast.LENGTH_SHORT ).show();
                    }
                } );
            }

        } );

    }
}
