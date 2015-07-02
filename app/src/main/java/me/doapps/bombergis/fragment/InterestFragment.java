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
    private CheckBox checkHidrantes;
    private CheckBox checkColegios;
    private CheckBox checkComisarias;
    private CheckBox checkGrifos;
    private CheckBox checkBomberos;
    private CheckBox checkHospitales;
    private InterfaceInstitutes interfaceInstitutes;


    /* Estado de los botones*/
    static int stateHospitales = 0;
    static int stateColegios = 0;
    static int stateHidrantes = 0;
    static int stateBomberos = 0;
    static int stateGrifos = 0;
    static int stateComisarias = 0;

    public InterestFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interest, container, false);
        checkHidrantes = (CheckBox) view.findViewById(R.id.checkHidrantes);
        checkColegios = (CheckBox) view.findViewById(R.id.checkColegios);
        checkComisarias = (CheckBox) view.findViewById(R.id.checkComisarias);
        checkGrifos = (CheckBox) view.findViewById(R.id.checkGrifos);
        checkBomberos = (CheckBox) view.findViewById(R.id.checkBomberos);
        checkHospitales = (CheckBox) view.findViewById(R.id.checkHospitales);

        if (stateHospitales == 1) {
            checkHospitales.setChecked(true);
        }
        if (stateBomberos == 1) {
            checkBomberos.setChecked(true);
        }
        if (stateGrifos == 1) {
            checkGrifos.setChecked(true);
        }
        if (stateComisarias == 1) {
            checkComisarias.setChecked(true);
        }
        if (stateColegios == 1) {
            checkColegios.setChecked(true);
        }
        if (stateHidrantes == 1) {
            checkHidrantes.setChecked(true);
        }
        interfaceInstitutes.getInstitute(stateHospitales, stateColegios, stateHidrantes, stateBomberos, stateGrifos, stateComisarias);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkHospitales.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    stateHospitales = 1;
                } else {
                    stateHospitales = 0;
                }
                interfaceInstitutes.getInstitute(stateHospitales, stateColegios, stateHidrantes, stateBomberos, stateGrifos, stateComisarias);
            }
        });

        checkColegios.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    stateColegios = 1;
                } else {
                    stateColegios = 0;
                }
                interfaceInstitutes.getInstitute(stateHospitales, stateColegios, stateHidrantes, stateBomberos, stateGrifos, stateComisarias);
            }
        });

        checkHidrantes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    stateHidrantes = 1;
                } else {
                    stateHidrantes = 0;
                }
                interfaceInstitutes.getInstitute(stateHospitales, stateColegios, stateHidrantes, stateBomberos, stateGrifos, stateComisarias);
            }
        });

        checkBomberos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    stateBomberos = 1;
                } else {
                    stateBomberos = 0;
                }
                interfaceInstitutes.getInstitute(stateHospitales, stateColegios, stateHidrantes, stateBomberos, stateGrifos, stateComisarias);
            }
        });

        checkGrifos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    stateGrifos = 1;
                } else {
                    stateGrifos = 0;
                }
                interfaceInstitutes.getInstitute(stateHospitales, stateColegios, stateHidrantes, stateBomberos, stateGrifos, stateComisarias);
            }
        });

        checkComisarias.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    stateComisarias = 1;
                } else {
                    stateComisarias = 0;
                }
                interfaceInstitutes.getInstitute(stateHospitales, stateColegios, stateHidrantes, stateBomberos, stateGrifos, stateComisarias);
            }
        });
    }

    /*inner interface*/
    public interface InterfaceInstitutes {
        void getInstitute(int flag1, int flag2, int flag3, int flag4, int flag5, int flag6);
    }

    public void setInterfaceInstitutes(InterfaceInstitutes interfaceInstitutes) {
        this.interfaceInstitutes = interfaceInstitutes;
    }
}
