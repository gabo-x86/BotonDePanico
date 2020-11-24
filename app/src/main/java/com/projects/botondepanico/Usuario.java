package com.projects.botondepanico;

//import com.google.firebase.auth.FirebaseAuth;

public class Usuario {
    /*private FirebaseAuth mAuth;
    private String nickName;
    private String mail;
    private String pass;
    private double lat;
    private double lon;

    public Usuario(String nickName, String mail, String pass, double lat, double lon) {
        this.mAuth = mAuth = FirebaseAuth.getInstance();;
        this.nickName = nickName;
        this.mail = mail;
        this.pass = pass;
        this.lat = lat;
        this.lon = lon;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public String getNickName() {
        return nickName;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    /**setters**/
    /*public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    /**functional methods**/
    /*public void registrar(String email, String password){
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

                        // ...
                    }
                });

    }*/
}
