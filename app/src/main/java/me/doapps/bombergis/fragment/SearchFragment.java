package me.doapps.bombergis.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import me.doapps.bombergis.activity.DashboardActivity;
import me.doapps.bombergis.operation.MapOperation;

/**
 * Created by MiguelGarrafa_2 on 27/05/2015.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    private Button btnBuscar;
    private InterfaceSearch interfaceSearch;
    private AutoCompleteTextView txtAddress;
    private String data = "";

    private ArrayList<String> resultList;
    private ArrayList<String> referenceList;

    public SearchFragment() {
    }

    @Nullable
    @Override
    /*Conectar el Fragment con el layout*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        btnBuscar = (Button) view.findViewById(R.id.btnBuscar);
        txtAddress = (AutoCompleteTextView) view.findViewById(R.id.txtAddress);
        return view;
    }

    /*Para manipular los elementos del layout*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    /**adaptar la baraja de opciones en la edittext osea que se despliegue las opciones**/
        txtAddress.setAdapter(new AutoCompleteAdapter(getActivity(),R.layout.item_list));
        txtAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("seleccionado", resultList.get(i));
            }
        });

        btnBuscar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        interfaceSearch.getAddress(txtAddress.getText().toString());
    }


    /**Methods**/
    private ArrayList<String> autoComplete(String input){
        MapOperation mapOperation = new MapOperation();
        mapOperation.getPredictions(input);
        mapOperation.setInterfacePredictions(new MapOperation.InterfacePredictions() {
            @Override
            public void getArrayPrediction(String status, JSONArray predictions) {
                if(status.equals("OK")){
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

    /**Adapter interno**/
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
                    if(charSequence!=null){
                        resulList = autoComplete(charSequence.toString());
                        filterResults.values = resulList;
                        filterResults.count = resulList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    if(filterResults!=null && filterResults.count>0){
                        notifyDataSetChanged();
                    }else{
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }


    /**Interfaces**/
    public interface InterfaceSearch {
        void getAddress(String address);
    }
    public void setInterfaceSearch(InterfaceSearch interfaceSearch) {
        this.interfaceSearch = interfaceSearch;
    }


}
