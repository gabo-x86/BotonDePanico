package com.projects.botondepanico.ui.home;

import android.annotation.SuppressLint;
import android.app.Application;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.projects.botondepanico.AlertFragment;
import com.projects.botondepanico.MapsFragment;
import com.projects.botondepanico.R;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public MapsFragment maps;

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    private AlertFragment alertFragment;
    private FragmentTransaction transaction;
    int eventCount = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        sensorInitialize();
        alertFragment = new AlertFragment();
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        maps = new MapsFragment();

    }


    @Override
    public void onPause() {
        stop();
        super.onPause();
    }
    @Override
    public void onResume() {
        start();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, maps).commit();
        super.onResume();

    }

    private void sensorInitialize() {

        sensorManager = (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        transaction = getActivity().getSupportFragmentManager().beginTransaction();


        if(sensor==null){
            getActivity().finish();
        }

        sensorEventListener = new SensorEventListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];
                start();


                if(((x>=10 || x<=-10) || (y>=10 || y<=-10) || (z>=10 || z<=-10)) && eventCount==0){
                    eventCount++;
                }else if((x<=1 || y<=1 || z<=1) && eventCount==1){
                    eventCount++;
                }

                if(eventCount==2){
                    transaction.replace(R.id.nav_host_fragment, alertFragment).commit();
                    stop();
                    eventCount=0;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        start();

    }
    private void start(){
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }



}