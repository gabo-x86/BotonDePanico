package com.projects.botondepanico.ui.auth;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projects.botondepanico.R;
import com.projects.botondepanico.Usuario;

import java.util.HashMap;
import java.util.Map;

import static androidx.core.content.ContextCompat.getSystemService;

public class RegisterFragment extends Fragment {

    private EditText email;
    private EditText pass;
    private EditText passConfirmation;
    private Button btnRegister;

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
                emptyStack();
                hideVirtualKeyboard();
                registerControl(navController);
            }
        });
    }
    private void registerControl(final NavController navController){
        if(!email.getText().toString().isEmpty() && !pass.getText().toString().isEmpty() && !passConfirmation.getText().toString().isEmpty()){
            if(pass.getText().toString().length() >= 6){
                userRegister(navController);
            }else{
                Toast.makeText(getActivity(), "Contraseña con más de 6 caracteres", Toast.LENGTH_SHORT ).show();
            }
        }else{
            Toast.makeText(getActivity(), "Llene todos los campos", Toast.LENGTH_SHORT).show();

        }
    }



    private void userRegister(final NavController navController){
        Usuario.getmAuth().createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", email.getText().toString());
                    map.put("email", email.getText().toString());
                    map.put("password", pass.getText().toString());
                    String id =  Usuario.getmAuth().getCurrentUser().getUid();

                    Usuario.getmDatabase().child("Users").child(id).setValue(map);
                    Toast.makeText(getActivity(), "Cuenta creada, por favor espere", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.nav_home);/**Por si se autentifica correctamente llevar a fragment...*/
                }else{
                    Toast.makeText(getActivity(), "No puede usar ese correo", Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }

    private void emptyStack(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }
    private void hideVirtualKeyboard(){
        InputMethodManager inputManager =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }



}