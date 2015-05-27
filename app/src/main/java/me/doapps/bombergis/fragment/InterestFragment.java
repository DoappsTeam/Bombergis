package me.doapps.bombergis.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import me.doapps.bombergis.R;

/**
 * Created by MiguelGarrafa_2 on 27/05/2015.
 */
public class InterestFragment extends Fragment {
    private Button btnTest;
    private CheckBox checkHidrantes;
    private InterfaceInstitutes interfaceInstitutes;

    public InterestFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interest, container, false);
        btnTest = (Button) view.findViewById(R.id.btnTest);
        checkHidrantes = (CheckBox) view.findViewById(R.id.checkHidrantes);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkHidrantes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    interfaceInstitutes.getInstitute(1);
                }
            }
        });


    }

    /*inner interface*/
    public interface InterfaceInstitutes{
        void getInstitute(int flag);
    }
    public void setInterfaceInstitutes(InterfaceInstitutes interfaceInstitutes){
        this.interfaceInstitutes = interfaceInstitutes;
    }
}
