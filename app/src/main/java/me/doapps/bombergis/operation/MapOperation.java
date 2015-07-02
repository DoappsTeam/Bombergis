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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InterfaceAddress;
import java.util.ArrayList;

import me.doapps.bombergis.R;
import me.doapps.bombergis.activity.DashboardActivity;
import me.doapps.bombergis.config.Settings;

/**
 * Created by user on 01/06/2015.
 */
public class MapOperation {
    private Context context;
    private String wskey = "AIzaSyBsy_H2fkcm4-eiIFszDVmQqfP66exG5sM";
    private GoogleMap mMap;

    private InterfaceReference interfaceReference;
    private InterfacePredictions interfacePredictions;
    private InterfaceLocation interfaceLocation;
    private InterfaceSteps interfaceSteps;

    public void getSteps(String origen, String destino){
        final RequestParams params = new RequestParams();
        params.put("origin", origen);
        params.put("destination", destino);
        params.put("sensor", "false");
        params.put("units", "metric");
        params.put("mode", "driving");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Settings.WS_GET_STEPS, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("response steps", response.toString());
                try {
                    if(response.getString("status").equals("OK")){
                        //interfacePredictions.getArrayPrediction(response.getString("status"),response.getJSONArray("predictions"));
                        interfaceSteps.getRouteSteps(response.getString("status"),response.getJSONArray("routes").getJSONObject(0));
                    }else{
                        interfacePredictions.getArrayPrediction(response.getString("status"),null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("response failure", responseString);
            }
        });
    }

    public void getPredictions(String input){
        final RequestParams params = new RequestParams();
        params.put("input", input);
        params.put("sensor", "false");
        params.put("key", wskey);
        params.put("components", "country:pe");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Settings.WS_GET_REFERENCE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Log.e("response success", response.toString());
                try {
                    if(response.getString("status").equals("OK")){
                        interfacePredictions.getArrayPrediction(response.getString("status"),response.getJSONArray("predictions"));
                    }else{
                        interfacePredictions.getArrayPrediction(response.getString("status"),null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("response failure", responseString);
            }
        });
    }


    public void getReferences(String input) {
        final RequestParams params = new RequestParams();
        params.put("input", input);
        params.put("sensor", "false");
        params.put("key", wskey);
        params.put("components", "country:pe");

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Settings.WS_GET_REFERENCE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("response success", response.toString());
                try {
                    if(response.getString("status").equals("OK")){
                        interfaceReference.getResponse(response.get("status").toString(),response.getJSONArray("predictions").getJSONObject(0).getString("reference"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("response failure", responseString);
            }
        });
    }

    public void getLocation(String reference) {

        final RequestParams params = new RequestParams();
        params.put("reference", reference);
        params.put("sensor", "false");
        params.put("key", wskey);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Settings.WS_GET_LOCATION, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    interfaceLocation.getLocation(response.getString("status"),response.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lat"),response.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
                } catch (JSONException e) {
                    interfaceLocation.getLocation("",0.,0.);
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
    public interface InterfaceReference{
        void getResponse(String status, String reference);
    }
    public void setInterfaceReference(InterfaceReference interfaceReference){
        this.interfaceReference = interfaceReference;
    }

    public interface InterfacePredictions{
        void getArrayPrediction(String status, JSONArray predictions);
    }
    public void setInterfacePredictions(InterfacePredictions interfacePredictions){
        this.interfacePredictions = interfacePredictions;
    }

    public interface InterfaceLocation{
        void getLocation(String status, Double lat, Double lng);
    }

    public void setInterfaceLocation(InterfaceLocation interfaceLocation){
        this.interfaceLocation = interfaceLocation;
    }

    public interface InterfaceSteps{
        void getRouteSteps(String status, JSONObject steps);
    }

    public void setInterfaceSteps(InterfaceSteps interfaceSteps){
        this.interfaceSteps = interfaceSteps;
    }

}
