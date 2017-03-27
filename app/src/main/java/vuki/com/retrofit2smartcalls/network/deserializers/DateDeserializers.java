package vuki.com.retrofit2smartcalls.network.deserializers;

import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vuki on 13.2.2016..
 */
public class DateDeserializers implements JsonDeserializer<Date>, JsonSerializer<Date> {

    public static final SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault() );

    @Override
    public Date deserialize( JsonElement json, Type typeOfT, JsonDeserializationContext context ) throws JsonParseException {
        String jsonDate = json.getAsString();
        if( !TextUtils.isEmpty( jsonDate ) ) {
            try {
                return formatter.parse( jsonDate );
            } catch( ParseException ex ) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public JsonElement serialize( Date src, Type typeOfSrc, JsonSerializationContext context ) {
        if( src != null ) {
            return context.serialize( formatter.format( src ) );
        }
        return null;
    }
}