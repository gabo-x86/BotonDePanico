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
    private static String nickName;
    private static String mail;
    private static String pass;
    private static double lat;
    private static double lon;

    public Usuario() {/**Iniciar BD**/
        mAuth = FirebaseAuth.getInstance();/**Incializa instancia de autentificación de la BD*/
        mDatabase = FirebaseDatabase.getInstance().getReference();/**Inicializa instancia de la BD*/
    }

    public Usuario(String nickName, String mail, String pass, double lat, double lon) {/**Crea objeto**/
        this.nickName = nickName;
        this.mail = mail;
        this.pass = pass;
        this.lat = lat;
        this.lon = lon;
    }

    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    public static DatabaseReference getmDatabase() {
        return mDatabase;
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

}
