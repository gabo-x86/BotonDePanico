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
import com.google.firebase.auth.FirebaseUser;
import com.projects.botondepanico.R;
import com.projects.botondepanico.Usuario;


public class LogInFragment extends Fragment {
    private EditText email;
    private EditText pass;

    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Usuario();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println(getActivity().getTitle());        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false);
        
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventButtons(view);
    }

    private void eventButtons(View view){
        Button btnLogin = view.findViewById(R.id.btnLogin);
        Button btnRegister = view.findViewById(R.id.btnRegister);
        email = view.findViewById(R.id.txtBoxEmail);
        pass = view.findViewById(R.id.txtboxPass);
        final NavController navController = Navigation.findNavController(view);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideVirtualKeyboard();
                navController.popBackStack();
                logInControl(navController);/**To logIn verification**/
                //navController.navigate(R.id.nav_home);/**Programar evento**/
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
                navController.navigate(R.id.registerFragment);/**To RegisterFragment**/
            }
        });
    }

    private void logInControl(NavController navController){
        if(!email.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()){
            if(pass.getText().toString().length() >= 6){
                userlogIn(navController);
            }else{
                Toast.makeText(getActivity(), "Contraseña con más de 6 caracteres", Toast.LENGTH_SHORT ).show();
            }
        }else{
            Toast.makeText(getActivity(), "Llene todos los campos", Toast.LENGTH_SHORT).show();

        }

    }

    private void userlogIn(final NavController navController) {
        Usuario.getmAuth().signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(getActivity(), "Ingreso exitoso", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = Usuario.getmAuth().getCurrentUser();
                    Usuario.updateConectedState(user.getUid());
                    navController.navigate(R.id.nav_home);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getActivity(), "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void hideVirtualKeyboard(){
        InputMethodManager inputManager =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

}