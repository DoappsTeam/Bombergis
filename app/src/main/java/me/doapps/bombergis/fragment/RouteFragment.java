package me.doapps.bombergis.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import me.doapps.bombergis.R;
import me.doapps.bombergis.operation.MapOperation;

/**
 * Created by user on 27/05/2015.
 */
public class RouteFragment extends Fragment implements View.OnClickListener {
    private Button btnBscRuta;
    //private Button btnBscRuta2;
    private InterfaceRoute interfaceRoute;
    private AutoCompleteTextView autoCompleteOrigen;
    private AutoCompleteTextView autoCompleteDestino;

    private ArrayList<String> resultList;
    private ArrayList<String> referenceList;

    static String routeOrigin;
    static String routeDestino;

    public RouteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        btnBscRuta = (Button) view.findViewById(R.id.btnBscRuta);
        //btnBscRuta2 = (Button)view.findViewById(R.id.btnBscRuta2);
        autoCompleteOrigen = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteOrigen);
        autoCompleteDestino = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteDestino);
        autoCompleteOrigen.setText(routeOrigin);
        autoCompleteDestino.setText(routeDestino);

        return view;
    }

    static int stateOrigin = 0;
    static int stateDestino = 0;

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        autoCompleteOrigen.setAdapter(new AutoCompleteAdapter(getActivity(), R.layout.item_list));
        autoCompleteOrigen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                routeOrigin = resultList.get(i);
                Log.e("Lugar - Reference", routeOrigin + "--" + referenceList.get(i));
                interfaceRoute.getRoute(1, referenceList.get(i)); stateOrigin = 1;
            }
        });

        autoCompleteOrigen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                interfaceRoute.getRoute(1, ""); routeOrigin = autoCompleteOrigen.getText().toString();stateOrigin = 1;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        autoCompleteDestino.setAdapter(new AutoCompleteAdapter(getActivity(), R.layout.item_list));
        autoCompleteDestino.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                routeDestino = resultList.get(i);
                Log.e("Lugar - Reference", routeDestino + "--" + referenceList.get(i));
                interfaceRoute.getRoute(2, referenceList.get(i)); stateDestino = 1;
            }
        });

        autoCompleteDestino.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                interfaceRoute.getRoute(2, ""); routeDestino = autoCompleteDestino.getText().toString();stateDestino = 1;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnBscRuta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnBscRuta) {

            if(stateOrigin == 0){interfaceRoute.getRoute(1, autoCompleteOrigen.getText().toString()); stateOrigin = 0;}
            if(stateDestino == 0){interfaceRoute.getRoute(2, autoCompleteDestino.getText().toString()); stateDestino = 0;}
            interfaceRoute.getRoute(3, "ROUTE");
        }
        //if(v.getId() == R.id.btnBscRuta2){interfaceRoute.getRoute(2, autoCompleteDestino.getText().toString());}

    }

    /**
     * Methods*
     */
    private ArrayList<String> autoComplete(String input) {
        MapOperation mapOperation = new MapOperation();
        mapOperation.getPredictions(input);
        mapOperation.setInterfacePredictions(new MapOperation.InterfacePredictions() {
            @Override
            public void getArrayPrediction(String status, JSONArray predictions) {
                if (status.equals("OK")) {
                    resultList = new ArrayList<String>();
                    referenceList = new ArrayList<String>();
                    for (int i = 0; i < predictions.length(); i++) {
                        try {
                            resultList.add(predictions.getJSONObject(i).getString("description"));
                            referenceList.add(predictions.getJSONObject(i).getString("reference"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
        return resultList;
    }

    /**
     * Adapter interno*
     */
    public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resulList;

        public AutoCompleteAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public int getCount() {
            return resulList.size();
        }

        @Override
        public String getItem(int position) {
            return resulList.get(position);
        }

        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults filterResults = new FilterResults();
                    if (charSequence != null) {
                        resulList = autoComplete(charSequence.toString());
                        filterResults.values = resulList;
                        filterResults.count = resulList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    if (filterResults != null && filterResults.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

    /**
     * Interference *
     */
    public interface InterfaceRoute {
        void getRoute(int s, String ruta);
    }

    public void setInterfaceRoute(InterfaceRoute interfaceRoute) {
        this.interfaceRoute = interfaceRoute;
    }

}
