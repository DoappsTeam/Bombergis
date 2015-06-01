package me.doapps.bombergis.operation;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import me.doapps.bombergis.R;
import me.doapps.bombergis.config.Settings;

/**
 * Created by user on 01/06/2015.
 */
public class MapOperation {
    private Context context;

    public void getReferences(String input){
        RequestParams params = new RequestParams();
        params.put("input", input);
        params.put("sensor", "false");
        params.put("key", "AIzaSyB7X6vhRYIoAzZWhIP_Tr05wx-q8ajslWg");
        params.put("components", "country:pe");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Settings.WS_GET_REFERENCE, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("EXITO", response.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
               Log.e("FALLA", responseString);
            }
        });
    }
}
