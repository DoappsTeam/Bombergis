package me.doapps.bombergis.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.doapps.bombergis.R;
import me.doapps.bombergis.fragment.InterestFragment;
import me.doapps.bombergis.fragment.MapsFragment;
import me.doapps.bombergis.fragment.RouteFragment;
import me.doapps.bombergis.fragment.SearchFragment;
import me.doapps.bombergis.operation.MapOperation;

/**
 * Created by user on 27/05/2015.
 */
public class DashboardActivity extends ActionBarActivity implements View.OnClickListener {

    private ImageButton btnSearch;
    private ImageButton btnInterest;
    private ImageButton btnMaps;
    private ImageButton btnRoute;
    private GoogleMap map;

    private List<LatLng> hospitales;
    private List<LatLng> colegios;
    private List<LatLng> hidrantes;
    private List<LatLng> bomberos;
    private List<LatLng> grifos;
    private List<LatLng> comisarias;

    static int band1=0;
    static int band2=0;
    static int band3=0;
    static int band4=0;
    static int band5=0;
    static int band6=0;

    static Double lati= 0.0;
    static  Double longi= 0.0;

    static int stateMapsDashboard = 1;
    private SearchFragment searchFragment;
    private RouteFragment routeFragment;
    private MapOperation mapOperation;
    CameraPosition camPos;
    CameraUpdate camUpd3;
    Marker markerS;
    Marker markerO;
    Marker markerD;

    public static int generalState = 1;

    /**
     * variable floatButton*
     */
    private FloatingActionButton floatingActionButton;
    int stateMarkerO = 0;
    int stateMarkerD = 0;
    int stateMap = 0;

    static LatLng originLaLng;
    static LatLng destinationLaLng;

    public static ArrayList<LatLng> startLocation;
    public static ArrayList<LatLng> endLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        btnInterest = (ImageButton) findViewById(R.id.btnInterest);
        btnInterest.setOnClickListener(this);

        btnMaps = (ImageButton) findViewById(R.id.btnMaps);
        btnMaps.setOnClickListener(this);

        btnRoute = (ImageButton) findViewById(R.id.btnRoute);
        btnRoute.setOnClickListener(this);

        /*Inicializar el mapa*/
        setUpMapIfNeeded();
        /*Inicializo Searragment*/
        initSearhFragment();
        /*cargamos markers*/
        loadMarkers();
       // Toast.makeText(getApplicationContext(), String.valueOf(generalState), Toast.LENGTH_SHORT).show();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSearch:
               // Toast.makeText(getApplicationContext(), String.valueOf(generalState), Toast.LENGTH_SHORT).show();
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search2));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route));
                initSearhFragment();
                break;

            case R.id.btnInterest:
               // Toast.makeText(getApplicationContext(), String.valueOf(generalState), Toast.LENGTH_SHORT).show();
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest2));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route));

                InterestFragment interestFragment = new InterestFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerLayout, interestFragment)
                        .commit();

                interestFragment.setInterfaceInstitutes(new InterestFragment.InterfaceInstitutes() {

                    @Override
                    public void getInstitute(int flag1, int flag2, int flag3, int flag4, int flag5, int flag6) {
                        map.clear();
                        if(generalState == 1) {
                            markerS = map.addMarker(new MarkerOptions()
                                    .position(new LatLng(lati, longi))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
               //             Toast.makeText(getApplicationContext(), String.valueOf(flag1) + String.valueOf(flag2) + String.valueOf(flag3) + String.valueOf(flag4) + String.valueOf(flag5) + String.valueOf(flag6), Toast.LENGTH_SHORT).show();
                        }

                            if (flag1 == 1) {
                            for (int i = 0; i < hospitales.size(); i++) {
                                map.addMarker(new MarkerOptions()
                                        .position(hospitales.get(i))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_hospital)));
                            }

                        }

                        if (flag2 == 1) {
                            for (int i = 0; i < colegios.size(); i++) {
                                map.addMarker(new MarkerOptions()
                                        .position(colegios.get(i))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_school)));
                            }
                        }

                        if (flag3 == 1) {
                            for (int i = 0; i < hidrantes.size(); i++) {
                                map.addMarker(new MarkerOptions()
                                        .position(hidrantes.get(i))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_hydrant)));
                            }
                        }

                        if (flag4 == 1) {
                            for (int i = 0; i < bomberos.size(); i++) {
                                map.addMarker(new MarkerOptions()
                                        .position(bomberos.get(i))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_fireman)));
                            }
                        }

                        if (flag5 == 1) {
                            for (int i = 0; i < grifos.size(); i++) {
                                map.addMarker(new MarkerOptions()
                                        .position(grifos.get(i))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_gasstation)));
                            }
                        }

                        if (flag6 == 1) {
                            for (int i = 0; i < comisarias.size(); i++) {
                                map.addMarker(new MarkerOptions()
                                        .position(comisarias.get(i))
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_policestation)));
                            }
                        }
                    }
                });
                generalState = 2;
                break;

            case R.id.btnMaps:
                //Toast.makeText(getApplicationContext(), String.valueOf(generalState), Toast.LENGTH_SHORT).show();
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps2));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route));

                MapsFragment mapsFragment = new MapsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerLayout, mapsFragment)
                        .commit();

                mapsFragment.setInterfaceChangeMaps(new MapsFragment.InterfaceChangeMaps() {
                    @Override
                    public void getMaps(int flag) {
                        if (flag == 1) {
                            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            stateMapsDashboard = 1;
                        }

                        if (flag == 2) {
                            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            stateMapsDashboard = 2;
                        }

                        if (flag == 3) {
                            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                            stateMapsDashboard = 3;
                        }
                    }
                });
                generalState = 3;

                break;

            case R.id.btnRoute:
              //  Toast.makeText(getApplicationContext(), String.valueOf(generalState), Toast.LENGTH_SHORT).show();
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route2));
                initRouteFragment();

                break;
        }
    }

    /**
     * Methods and Functions*
     */
    private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        map.setMyLocationEnabled(true);

        if (stateMapsDashboard == 1) {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (stateMapsDashboard == 2) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        if (stateMapsDashboard == 3) {
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

        map.getUiSettings().setZoomControlsEnabled(true);
        //mapSettings = map.getUiSettings();
        map.getUiSettings().setScrollGesturesEnabled(true);
        map.getUiSettings().setTiltGesturesEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(true);

        camPos = new CameraPosition.Builder()
                .target(new LatLng(-12.1023776, -77.0219219))
                .zoom(16)
                .build();
        camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(camUpd3);
    }

    private void initSearhFragment() {
        searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, searchFragment).commit();
        //Toast.makeText(getApplicationContext(), "Mensaje de Prueba Primeraaa" + String.valueOf(generalState), Toast.LENGTH_SHORT).show();

        if (generalState == 4 || generalState == 1) {
            map.clear();
        }
        searchFragment.setInterfaceSearch(new SearchFragment.InterfaceSearch() {
            @Override
            public void getAddress(String address) {

                mapOperation = new MapOperation();
                mapOperation.getLocation(address);
                mapOperation.setInterfaceLocation(new MapOperation.InterfaceLocation() {
                    @Override
                    public void getLocation(String status, Double lat, Double lng) {
                        if (status.equals("OK")) {
                            markerS = map.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat, lng))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                    lati=lat;
                                    longi=lng;
                            camPos = new CameraPosition.Builder()
                                    .target(new LatLng(lat, lng))
                                    .zoom(16)
                                    .build();
                            camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                            map.animateCamera(camUpd3);
                        }
                    }
                });
            }
        });
        generalState = 1;
    }

    private void initRouteFragment() {
        routeFragment = new RouteFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLayout, routeFragment)
                .commit();
        routeFragment.setInterfaceRoute(new RouteFragment.InterfaceRoute() {
            @Override
            public void getRoute(int s, String ruta) {
                if (s == 1) {
                    if (stateMap == 0) {
                        if (generalState == 1 || generalState == 4) {
                            map.clear();
                        }
                        stateMap = 1;
                    }
                    //if(stateMarkerO == 1){map.addMarker(new MarkerOptions().position(originLaLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));}
                    //if(stateMarkerD == 1){map.addMarker(new MarkerOptions().position(destinationLaLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));}

                    mapOperation = new MapOperation();
                    mapOperation.getLocation(ruta);
                    mapOperation.setInterfaceLocation(new MapOperation.InterfaceLocation() {
                        @Override
                        public void getLocation(String status, Double lat, Double lng) {
                            if (status.equals("OK")) {
                                originLaLng = new LatLng(lat, lng);
                                //markerO.setVisible(false);
                                markerO = map.addMarker(new MarkerOptions()
                                        .position(originLaLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
                                stateMarkerO = 1;
                                camPos = new CameraPosition.Builder()
                                        .target(originLaLng)
                                        .zoom(16)
                                        .build();
                                camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                                map.animateCamera(camUpd3);
                            }
                        }
                    });
                }

                if (s == 2) {
                    if (stateMap == 0) {
                        if (generalState == 1 || generalState == 4) {
                            map.clear();
                        }

                        stateMap = 1;
                    }
                    //if(stateMarkerO == 1){map.addMarker(new MarkerOptions().position(originLaLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));}
                    //if(stateMarkerD == 1){map.addMarker(new MarkerOptions().position(destinationLaLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));}

                    mapOperation = new MapOperation();
                    mapOperation.getLocation(ruta);
                    mapOperation.setInterfaceLocation(new MapOperation.InterfaceLocation() {
                        @Override
                        public void getLocation(String status, Double lat, Double lng) {
                            if (status.equals("OK")) {
                                destinationLaLng = new LatLng(lat, lng);
                                //markerD.setVisible(false);
                                markerD = map.addMarker(new MarkerOptions()
                                        .position(destinationLaLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker2)));
                                stateMarkerD = 1;
                                camPos = new CameraPosition.Builder()
                                        .target(destinationLaLng)
                                        .zoom(16)
                                        .build();
                                camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                                map.animateCamera(camUpd3);
                            }
                        }
                    });
                }

                if (s == 3) {
                    //map.clear();
                    mapOperation = new MapOperation();
                    mapOperation.getSteps(String.valueOf(originLaLng.latitude) + "," + String.valueOf(originLaLng.longitude), String.valueOf(destinationLaLng.latitude) + "," + String.valueOf(destinationLaLng.longitude));
                    mapOperation.setInterfaceSteps(new MapOperation.InterfaceSteps() {
                        @Override
                        public void getRouteSteps(String status, JSONObject steps) {
                            if (status.equals("OK")) {
                                try {
                                    //JSONArray arraySteps = new JSONArray(steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps"));
                                    Log.e("objeto steps", steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps").toString());
                                    startLocation = new ArrayList<LatLng>();
                                    endLocation = new ArrayList<LatLng>();

                                    for (int k = 0; k < steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps").length(); k++) {
                                        startLocation.add(new LatLng(steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(k).getJSONObject("start_location").getDouble("lat"), steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(k).getJSONObject("start_location").getDouble("lng")));
                                        endLocation.add(new LatLng(steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(k).getJSONObject("end_location").getDouble("lat"), steps.getJSONArray("legs").getJSONObject(0).getJSONArray("steps").getJSONObject(k).getJSONObject("end_location").getDouble("lng")));
                                        Polyline line = map.addPolyline(new PolylineOptions().
                                                add(startLocation.get(k), endLocation.get(k))
                                                .width(12).color(Color.rgb(150, 40, 27)));

                                    }

                                    map.addMarker(new MarkerOptions()
                                            .position(originLaLng)
                                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));

                                    map.addMarker(new MarkerOptions()
                                            .position(destinationLaLng)
                                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker2)));

                                    camPos = new CameraPosition.Builder()
                                            .target(new LatLng((originLaLng.latitude + destinationLaLng.latitude) / 2, (originLaLng.longitude + destinationLaLng.longitude) / 2))
                                            .zoom(13)
                                            .build();
                                    camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                                    map.animateCamera(camUpd3);

                                    stateMap = 0;

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        });
        generalState = 4;
    }

    private void loadMarkers() {

        /*agregamos hospitales*/
        hospitales = new ArrayList<>();
        hospitales.add(new LatLng(-12.101524, -77.021626));
        hospitales.add(new LatLng(-12.097663, -77.022184));
        hospitales.add(new LatLng(-12.154225, -77.055632));
        hospitales.add(new LatLng(-12.086472, -77.098543));
        hospitales.add(new LatLng(-12.162435, -77.045563));
        hospitales.add(new LatLng(-12.067863, -77.056342));
        /*agregamos colegios*/
        colegios = new ArrayList<>();
        colegios.add(new LatLng(-12.106524, -77.025632));
        colegios.add(new LatLng(-12.095632, -77.025623));
        colegios.add(new LatLng(-12.156524, -77.054525));
        colegios.add(new LatLng(-12.086424, -77.096524));
        colegios.add(new LatLng(-12.164524, -77.046247));
        colegios.add(new LatLng(-12.067241, -77.057324));
        /*agregamos hidrantes*/
        hidrantes = new ArrayList<>();
        hidrantes.add(new LatLng(-12.153566, -77.024524));
        hidrantes.add(new LatLng(-12.056345, -77.026525));
        hidrantes.add(new LatLng(-12.113454, -77.067862));
        hidrantes.add(new LatLng(-12.094755, -77.078424));
        hidrantes.add(new LatLng(-12.145256, -77.045675));
        hidrantes.add(new LatLng(-12.067345, -77.041455));
        /*agregamos bomberos*/
        bomberos = new ArrayList<>();
        bomberos.add(new LatLng(-12.113414, -77.056822));
        bomberos.add(new LatLng(-12.076245, -77.057686));
        bomberos.add(new LatLng(-12.157652, -77.089873));
        bomberos.add(new LatLng(-12.056735, -77.087252));
        bomberos.add(new LatLng(-12.156732, -77.067824));
        bomberos.add(new LatLng(-12.056782, -77.069435));
        /*agregramos grifos*/
        grifos = new ArrayList<>();
        grifos.add(new LatLng(-12.173525, -77.023456));
        grifos.add(new LatLng(-12.078356, -77.097354));
        grifos.add(new LatLng(-12.114794, -77.024562));
        grifos.add(new LatLng(-12.098435, -77.024662));
        grifos.add(new LatLng(-12.113514, -77.087356));
        grifos.add(new LatLng(-12.097452, -77.097356));
        /*agregramos comisarias*/
        comisarias = new ArrayList<>();
        comisarias.add(new LatLng(-12.114546, -77.076244));
        comisarias.add(new LatLng(-12.062461, -77.014462));
        comisarias.add(new LatLng(-12.178624, -77.076245));
        comisarias.add(new LatLng(-12.076243, -77.076254));
        comisarias.add(new LatLng(-12.076262, -77.052466));
        comisarias.add(new LatLng(-12.097624, -77.087343));
    }

    public void Alert(View v) {
        if (v.getId() == R.id.float_button) {
            Toast.makeText(getApplicationContext(), R.string.alert, Toast.LENGTH_SHORT).show();
        }
    }
}
