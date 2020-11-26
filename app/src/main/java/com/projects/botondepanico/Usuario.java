package com.projects.botondepanico;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Usuario {
    private static FirebaseAuth mAuth;
    private static DatabaseReference mDatabase;
    private static String id;
    private static double lat;
    /*private static String mail;
    private static String[] contact1;
    private static String pass;
    private static double lat;
    private static double lon;*/

    public Usuario() {/**Iniciar BD**/
        mAuth = FirebaseAuth.getInstance();/**Incializa instancia de autentificación de la BD*/
        mDatabase = FirebaseDatabase.getInstance().getReference();/**Inicializa instancia de la BD*/
        id="";
    }

    public Usuario(String id) {/**Crea objeto**/
        this.id=id;
    }

    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    public static DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        Usuario.id = id;
    }

    public static void updateConectedState(String user){
        new Usuario(user);
        mDatabase.child("Users").child(Usuario.getId()).child("connectedState").setValue("true");
    }

    public static void updateDisconecteddState(String user){
        new Usuario(user);
        mDatabase.child("Users").child(Usuario.getId()).child("connectedState").setValue("false");
    }

}
