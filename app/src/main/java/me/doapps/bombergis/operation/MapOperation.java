package me.doapps.bombergis.operation;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InterfaceAddress;

import me.doapps.bombergis.R;
import me.doapps.bombergis.activity.DashboardActivity;
import me.doapps.bombergis.config.Settings;

/**
 * Created by user on 01/06/2015.
 */
public class MapOperation {
    private Context context;
    private String var;
    private GoogleMap mMap;

    private InterfaceTest interfaceTest;

    public void getReferences(String input) {
        final RequestParams params = new RequestParams();
        params.put("input", input);
        params.put("sensor", "false");
        params.put("key", "AIzaSyB7X6vhRYIoAzZWhIP_Tr05wx-q8ajslWg");
        params.put("components", "country:pe");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Settings.WS_GET_REFERENCE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("response success", response.toString());
                interfaceTest.getResponse(response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("response failure", responseString);
            }
        });
    }

    public void getLocation(String input1) {

        final RequestParams params = new RequestParams();
        params.put("reference", input1);
        params.put("sensor", "false");
        params.put("key", "AIzaSyB7X6vhRYIoAzZWhIP_Tr05wx-q8ajslWg");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Settings.WS_GET_LOCATION, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Log.e("Latitud", response.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lat"));
                    Log.e("Altitud", response.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lng"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("FALLA", responseString);
            }
        });
    }

    /**Interfaces**/
    public interface InterfaceTest{
        void getResponse(String x);
    }
    public void setInterfaceTest(InterfaceTest interfaceTest){
        this.interfaceTest = interfaceTest;
    }
}
