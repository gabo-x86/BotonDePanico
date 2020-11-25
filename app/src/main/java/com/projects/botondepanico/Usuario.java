package com.projects.botondepanico;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Usuario {
    private static FirebaseAuth mAuth;
    private static String nickName;
    private static String mail;
    private static String pass;
    private static double lat;
    private static double lon;

    public Usuario() {/**Iniciar BD**/
        this.mAuth = mAuth = FirebaseAuth.getInstance();
    }

    public Usuario(String nickName, String mail, String pass, double lat, double lon) {/**Crea objeto**/
        this.nickName = nickName;
        this.mail = mail;
        this.pass = pass;
        this.lat = lat;
        this.lon = lon;
    }

    public String getNickName() {
        return nickName;
    }

    public String getMail() {
        return mail;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    /**setters**/
    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    /**functional methods**/
    public void autoLogIn(){/**FALTA!!!*/
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    public void createUser( ){


    }
    /*
    public void userRegister(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });

    }*/
}
