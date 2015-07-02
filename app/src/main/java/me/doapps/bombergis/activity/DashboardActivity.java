package me.doapps.bombergis.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
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

    static ArrayList<Marker> markerHospitales;
    static ArrayList<Marker> markerColegios;
    static ArrayList<Marker> markerHidrantes;
    static ArrayList<Marker> markerBomberos;
    static ArrayList<Marker> markerGrifos;
    static ArrayList<Marker> markerComisarias;

    private List<LatLng> hospitales;
    private List<LatLng> colegios;
    private List<LatLng> hidrantes;
    private List<LatLng> bomberos;
    private List<LatLng> grifos;
    private List<LatLng> comisarias;

    static int stateMapsDashboard = 1;
    private SearchFragment searchFragment;
    private RouteFragment routeFragment;
    private MapOperation mapOperation;
    CameraPosition camPos;
    CameraUpdate camUpd3;
    static Marker markerS;
    static Marker markerO;
    static Marker markerD;

    /**
     * variable floatButton*
     */
    private FloatingActionButton floatingActionButton;

    static LatLng originLaLng;
    static LatLng destinationLaLng;

    static public ArrayList<LatLng> startLocation;
    static public ArrayList<LatLng> endLocation;
    static public ArrayList<Polyline> routeList;

    static public Double dashLat = -12.1023776;
    static public Double dashLng = -77.0219219;

    static public Double origenLat = 0.;
    static public Double origenLng = 0.;

    static public Double destinoLat = 0.;
    static public Double destinoLng = 0.;


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
        initSearchFragment();
        /*cargamos markers*/
        loadMarkers();

        routeList = new ArrayList<Polyline>();

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (markerS.isInfoWindowShown()) {
                    markerS.hideInfoWindow();
                } else {
                    markerS.showInfoWindow();
                }

                return false;
            }
        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.getId() == markerS.getId()) {
                    markerS.hideInfoWindow();
                }
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSearch:
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search2));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route));

                initSearchFragment();
                break;

            case R.id.btnInterest:
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest2));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route));

                initInterestFragment();
                break;

            case R.id.btnMaps:
                btnSearch.setImageDrawable(getDrawable(R.mipmap.ic_search));
                btnInterest.setImageDrawable(getDrawable(R.mipmap.ic_interest));
                btnMaps.setImageDrawable(getDrawable(R.mipmap.ic_maps2));
                btnRoute.setImageDrawable(getDrawable(R.mipmap.ic_route));

                initMapsFragment();
                break;

            case R.id.btnRoute:
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
        //map.setMyLocationEnabled(true);

        if (stateMapsDashboard == 1) {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (stateMapsDashboard == 2) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        if (stateMapsDashboard == 3) {
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

        //map.getUiSettings().setZoomControlsEnabled(true);
        //mapSettings = map.getUiSettings();
        //map.getUiSettings().setScrollGesturesEnabled(true);
        // map.getUiSettings().setTiltGesturesEnabled(true);
        // map.getUiSettings().setRotateGesturesEnabled(true);

        camPos = new CameraPosition.Builder()
                .target(new LatLng(-12.1023776, -77.0219219))
                .zoom(16)
                .build();
        camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(camUpd3);
        map.setInfoWindowAdapter(new MarkerInfoWindowAdapter());

    }

    private void initSearchFragment() {

        if (markerS != null) {
            markerS.remove();
        }
        if (markerO != null) {
            markerO.remove();
        }
        if (markerD != null) {
            markerD.remove();
        }

        if (routeList != null) {
            for (int k = 0; k < routeList.size(); k++)
                routeList.get(k).setVisible(false);
        }

        markerS = map.addMarker(new MarkerOptions()
                        .position(new LatLng(dashLat, dashLng))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        );
        camPos = new CameraPosition.Builder()
                .target(new LatLng(dashLat, dashLng))
                .zoom(16)
                .build();
        camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        map.animateCamera(camUpd3);

        searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, searchFragment).commit();

        searchFragment.setInterfaceSearch(new SearchFragment.InterfaceSearch() {
            @Override
            public void getAddress(String address) {

                if (markerS != null) {
                    markerS.remove();
                }
                if (markerO != null) {
                    markerO.remove();
                }
                if (markerD != null) {
                    markerD.remove();
                }

                if (routeList != null) {
                    for (int k = 0; k < routeList.size(); k++)
                        routeList.get(k).remove();
                }

                mapOperation = new MapOperation();
                mapOperation.getLocation(address);
                mapOperation.setInterfaceLocation(new MapOperation.InterfaceLocation() {
                    @Override
                    public void getLocation(String status, Double lat, Double lng) {
                        if (status.equals("OK")) {

                            dashLat = lat;
                            dashLng = lng;
                            markerS = map.addMarker(new MarkerOptions()
                                            .position(new LatLng(dashLat, dashLng))
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            );
                            camPos = new CameraPosition.Builder()
                                    .target(new LatLng(dashLat, dashLng))
                                    .zoom(16)
                                    .build();
                            camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                            map.animateCamera(camUpd3);
                        }
                    }
                });
            }
        });
    }

    private void initInterestFragment() {

        InterestFragment interestFragment = new InterestFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLayout, interestFragment)
                .commit();

        interestFragment.setInterfaceInstitutes(new InterestFragment.InterfaceInstitutes() {
            //(stateHospitales, stateColegios, stateHidrantes, stateBomberos, stateGrifos, stateComisarias);
            @Override
            public void getInstitute(int flag1, int flag2, int flag3, int flag4, int flag5, int flag6) {

                if (markerHospitales != null) {
                    for (int k = 0; k < markerHospitales.size(); k++) {
                        markerHospitales.get(k).remove();
                    }
                }

                if (markerColegios != null) {
                    for (int k = 0; k < markerColegios.size(); k++) {
                        markerColegios.get(k).remove();
                    }
                }

                if (markerHidrantes != null) {
                    for (int k = 0; k < markerHidrantes.size(); k++) {
                        markerHidrantes.get(k).remove();
                    }
                }

                if (markerBomberos != null) {
                    for (int k = 0; k < markerBomberos.size(); k++) {
                        markerBomberos.get(k).remove();
                    }
                }

                if (markerGrifos != null) {
                    for (int k = 0; k < markerGrifos.size(); k++) {
                        markerGrifos.get(k).remove();
                    }
                }

                if (markerComisarias != null) {
                    for (int k = 0; k < markerComisarias.size(); k++) {
                        markerComisarias.get(k).remove();
                    }
                }


                if (flag1 == 1) {
                    markerHospitales = new ArrayList<Marker>();

                    for (int i = 0; i < hospitales.size(); i++) {
                        Marker tempH = map.addMarker(new MarkerOptions()
                                .position(hospitales.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_hospital)));
                        markerHospitales.add(tempH);
                    }
                }

                if (flag2 == 1) {
                    markerColegios = new ArrayList<Marker>();

                    for (int i = 0; i < colegios.size(); i++) {
                        Marker tempC = map.addMarker(new MarkerOptions()
                                .position(colegios.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_school)));
                        markerColegios.add(tempC);
                    }
                }

                if (flag3 == 1) {
                    markerHidrantes = new ArrayList<Marker>();

                    for (int i = 0; i < hidrantes.size(); i++) {
                        Marker tempHi = map.addMarker(new MarkerOptions()
                                .position(hidrantes.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_hydrant)));
                        markerHidrantes.add(tempHi);
                    }
                }

                if (flag4 == 1) {
                    markerBomberos = new ArrayList<Marker>();

                    for (int i = 0; i < bomberos.size(); i++) {
                        Marker tempB = map.addMarker(new MarkerOptions()
                                .position(bomberos.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_fireman)));
                        markerBomberos.add(tempB);
                    }
                }

                if (flag5 == 1) {
                    markerGrifos = new ArrayList<Marker>();

                    for (int i = 0; i < grifos.size(); i++) {
                        Marker tempG = map.addMarker(new MarkerOptions()
                                .position(grifos.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_gasstation)));
                        markerGrifos.add(tempG);
                    }
                }

                if (flag6 == 1) {
                    markerComisarias = new ArrayList<Marker>();

                    for (int i = 0; i < comisarias.size(); i++) {
                        Marker tempCo = map.addMarker(new MarkerOptions()
                                .position(comisarias.get(i))
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_policestation)));
                        markerComisarias.add(tempCo);
                    }
                }

            }
        });
    }

    private void initMapsFragment() {
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
    }

    static int stateOriginMarker = 0;
    static int stateDestinoMarker = 0;

    private void initRouteFragment() {

        if (stateOriginMarker == 1) {
            markerS.remove();

            markerO = map.addMarker(new MarkerOptions()
                    .position(originLaLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
        }

        if (stateDestinoMarker == 1) {
            markerD = map.addMarker(new MarkerOptions()
                    .position(destinationLaLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
        }

        if (routeList != null) {
            for (int k = 0; k < routeList.size(); k++)
                routeList.get(k).setVisible(true);
        }

        routeFragment = new RouteFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerLayout, routeFragment)
                .commit();
        routeFragment.setInterfaceRoute(new RouteFragment.InterfaceRoute() {
            @Override
            public void getRoute(int s, String ruta) {
                if (s == 1) {

                    if (markerS != null) {
                        markerS.remove();
                    }
                    if (markerO != null) {
                        markerO.remove();
                    }

                    if (routeList != null) {
                        for (int k = 0; k < routeList.size(); k++)
                            routeList.get(k).remove();
                    }

                    mapOperation = new MapOperation();
                    mapOperation.getLocation(ruta);
                    mapOperation.setInterfaceLocation(new MapOperation.InterfaceLocation() {
                        @Override
                        public void getLocation(String status, Double lat, Double lng) {

                            if (status.equals("OK")) {

                                originLaLng = new LatLng(lat, lng);
                                markerO = map.addMarker(new MarkerOptions()
                                        .position(originLaLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker)));
                                stateOriginMarker = 1;
                                camPos = new CameraPosition.Builder()
                                        .target(originLaLng)
                                        .zoom(16)
                                        .build();
                                camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                                map.animateCamera(camUpd3);
                            } else {
                                stateOriginMarker = 0;
                            }
                        }
                    });
                }

                if (s == 2) {

                    if (markerS != null) {
                        markerS.remove();
                    }

                    if (markerD != null) {
                        markerD.remove();
                    }

                    if (routeList != null) {
                        for (int k = 0; k < routeList.size(); k++)
                            routeList.get(k).remove();
                    }

                    mapOperation = new MapOperation();
                    mapOperation.getLocation(ruta);
                    mapOperation.setInterfaceLocation(new MapOperation.InterfaceLocation() {
                        @Override
                        public void getLocation(String status, Double lat, Double lng) {

                            if (status.equals("OK")) {

                                destinationLaLng = new LatLng(lat, lng);
                                markerD = map.addMarker(new MarkerOptions()
                                        .position(destinationLaLng)
                                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker2)));
                                stateDestinoMarker = 1;
                                camPos = new CameraPosition.Builder()
                                        .target(destinationLaLng)
                                        .zoom(16)
                                        .build();
                                camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                                map.animateCamera(camUpd3);
                            } else {
                                stateDestinoMarker = 0;
                            }
                        }
                    });
                }

                if (s == 3) {

                    if (markerS != null) {
                        markerS.remove();
                    }

                    /*Validación de Markers*/

                    if ((stateOriginMarker == 1) && (stateDestinoMarker == 1)) {

                        //stateOriginMarker = 0;
                        //stateDestinoMarker = 0;

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
                                                    .width(10).color(Color.rgb(150, 40, 27)));
                                            routeList.add(line);

                                        }

                                        camPos = new CameraPosition.Builder()
                                                .target(new LatLng((originLaLng.latitude + destinationLaLng.latitude) / 2, (originLaLng.longitude + destinationLaLng.longitude) / 2))
                                                .zoom(13)
                                                .build();
                                        camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
                                        map.animateCamera(camUpd3);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    } else {
                        if (stateOriginMarker == 0) {
                            Toast.makeText(DashboardActivity.this, "Ingresar Origen", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DashboardActivity.this, "Ingresar Destino", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }


    private void loadMarkers() {

        /*agregamos hospitales*/
        hospitales = new ArrayList<>();
        hospitales.add(new LatLng(-12.080524, -77.000626));
        hospitales.add(new LatLng(-12.075663, -77.001184));
        hospitales.add(new LatLng(-12.133225, -77.003632));
        hospitales.add(new LatLng(-12.065472, -77.006543));
        hospitales.add(new LatLng(-12.141435, -77.003563));
        hospitales.add(new LatLng(-12.046863, -77.005342));
        /*agregamos colegios*/
        colegios = new ArrayList<>();
        colegios.add(new LatLng(-12.086524, -77.000632));
        colegios.add(new LatLng(-12.075632, -77.000623));
        colegios.add(new LatLng(-12.136524, -77.020525));
        colegios.add(new LatLng(-12.066424, -77.030524));
        colegios.add(new LatLng(-12.144524, -77.010247));
        colegios.add(new LatLng(-12.047241, -77.050324));
        /*agregamos hidrantes*/
        hidrantes = new ArrayList<>();
        hidrantes.add(new LatLng(-12.133566, -77.000524));
        hidrantes.add(new LatLng(-12.036345, -77.000525));
        hidrantes.add(new LatLng(-12.093454, -77.030862));
        hidrantes.add(new LatLng(-12.074755, -77.040424));
        hidrantes.add(new LatLng(-12.125256, -77.010675));
        hidrantes.add(new LatLng(-12.047345, -77.010455));
        /*agregamos bomberos*/
        bomberos = new ArrayList<>();
        bomberos.add(new LatLng(-12.093414, -77.000822));
        bomberos.add(new LatLng(-12.056245, -77.000686));
        bomberos.add(new LatLng(-12.137652, -77.020873));
        bomberos.add(new LatLng(-12.036735, -77.030252));
        bomberos.add(new LatLng(-12.136732, -77.010824));
        bomberos.add(new LatLng(-12.036782, -77.010435));
        /*agregramos grifos*/
        grifos = new ArrayList<>();
        grifos.add(new LatLng(-12.123525, -77.000456));
        grifos.add(new LatLng(-12.058356, -77.040354));
        grifos.add(new LatLng(-12.094794, -77.000562));
        grifos.add(new LatLng(-12.078435, -77.000662));
        grifos.add(new LatLng(-12.093514, -77.030356));
        grifos.add(new LatLng(-12.077452, -77.040356));
        /*agregramos comisarias*/
        comisarias = new ArrayList<>();
        comisarias.add(new LatLng(-12.114546, -77.020244));
        comisarias.add(new LatLng(-12.062461, -77.000462));
        comisarias.add(new LatLng(-12.178624, -77.020245));
        comisarias.add(new LatLng(-12.076243, -77.020254));
        comisarias.add(new LatLng(-12.076262, -77.010466));
        comisarias.add(new LatLng(-12.097624, -77.030343));
    }

    public void Alert(View v) {
        if (v.getId() == R.id.float_button) {
            Toast.makeText(getApplicationContext(), R.string.alert, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /*Custom Map*/
    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        public MarkerInfoWindowAdapter() {
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View view = getLayoutInflater().inflate(R.layout.custom_map, null);
            /*ListView list_comments = (ListView)view.findViewById(R.id.list_comments);

            ArrayList<Comment_DTO> comment_dtos = new ArrayList<Comment_DTO>();

            comment_dtos.add(new Comment_DTO("Está vacío", "07 SET. a las 10PM"));
            comment_dtos.add(new Comment_DTO("Muy buenos grupos", "05 SET. a las 10PM"));
            comment_dtos.add(new Comment_DTO("Las mujeres pagan", "22 AGO. a las 10PM"));
            comment_dtos.add(new Comment_DTO("Nunca más vengo", "18 AGO. a las 10PM"));

            list_comments.setAdapter(new Comment_Adapter(comment_dtos, Nomad_Event_Detail.this));
*/
            return view;
        }
    }
}
