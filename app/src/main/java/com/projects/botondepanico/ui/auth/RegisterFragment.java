package com.projects.botondepanico.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projects.botondepanico.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {
    private static FirebaseAuth mAuth;
    private static DatabaseReference mDatabase;

    private EditText email;
    private EditText pass;
    private EditText passConfirmation;
    private Button btnRegister;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();/**Incializa instancia de autentificación de la BD*/
        mDatabase = FirebaseDatabase.getInstance().getReference();/**Inicializa instancia de la BD*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.txtBoxRegisterEmail);
        pass = view.findViewById(R.id.txtBoxRegisterPass);
        passConfirmation = view.findViewById(R.id.txtBoxRegisterConfirmation);
        btnRegister = view.findViewById(R.id.btnRegisterRegister);
        final NavController navController = Navigation.findNavController(view);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navController.popBackStack();
                registerControl(navController);
            }
        });
    }
    private void registerControl(final NavController navController){
        if(!email.getText().toString().isEmpty() && !pass.getText().toString().isEmpty() && !passConfirmation.getText().toString().isEmpty()){
            if(pass.getText().toString().length() >= 6){
                userRegister(navController);
            }else{
                System.out.println("PASSWORD DEBE TENER AL MENOS 6 CARACTERES");
            }
        }else{
            System.out.println("LLENE TODOS LOS CAMPOS");

        }
    }

    private void userRegister(final NavController navController){
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", email.getText().toString());
                    map.put("email", email.getText().toString());
                    map.put("password", pass.getText().toString());

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map);
                    navController.navigate(R.id.nav_home);/**Por si se autentifica correctamente llevar a fragment...*/
                }else{
                    System.out.println("NO SE PUDO REGISTRAR ESTE USUARIO. PROBLEMAS CON EL SERVIDOR");
                }
            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

}