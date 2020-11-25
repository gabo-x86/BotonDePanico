package com.projects.botondepanico;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AlertFragment extends Fragment {



    public AlertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alert, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button sos = view.findViewById(R.id.btnSOS);
        Button cancel = view.findViewById(R.id.btnNo);

        final NavController navController = Navigation.findNavController(view);

        counterInitialize(view, (short)10, navController);


        /**sos.setOnClickListener(new View.OnClickListener() {EVENTO DE SOS
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.inicioFragment);
            }
        });**/
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
                navController.navigate(R.id.nav_home);
            }
        });

    }

    private void counterInitialize(View view, short time, final NavController navController) {
        final TextView textView = (TextView) view.findViewById(R.id.counter);
        new CountDownTimer(time*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                long segPend = millisUntilFinished / 1000;
                textView.setText((int) segPend + "");
            }

            public void onFinish() {
                /**EVENTO DE SOS**/
            }
        }.start();
    }

}