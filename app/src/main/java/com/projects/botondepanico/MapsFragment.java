package com.projects.botondepanico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MapsFragment extends Fragment {

    private GoogleMap mMap;

    public OnMapReadyCallback callback = new OnMapReadyCallback() {
        /**
         * Maneja el mapa una vez esté disponible.
         * Esta llamada se desencadenará cuando el mapa esté listo para usarse.
         * Aqui es donde se puede añadir marcadores o lineas, "listeners" o mover la cámara.
         * Si Google Play services no está instalado en el dispositivo, el usuario debe hacerlo
         * instalándolo dentro de SupportMapFragment. Este método solo se desencadenará una vez
         * el usuario haya instalado Google Play services y volviendo entrar al proyecto.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            /*LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*//**REVISAR PARA COLOCAR MARCAS**/

            mMap = googleMap;
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);

            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng miUbicacion = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(miUbicacion).title("REEMPLAZAR POR USUARIO"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(miUbicacion)
                            .zoom(14)
                            .bearing(90)
                            .tilt(45)
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    updatePosition(miUbicacion.latitude, miUbicacion.longitude);

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        hearPositions();
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLocation();
    }
    public void getLocation(){/**Permisos**/
        int permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permission == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), Manifest.permission.ACCESS_FINE_LOCATION)){
            }else{
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }


    private void hearPositions(){
        Usuario.getmDatabase().child("Users").addValueEventListener(new ValueEventListener() {//GABRIEL ACA ESCUCHA AL HIJO UBICACION PARA OBTENER SUS HIJOS
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {//ESTE ES EL METODO DONDE OCURRE TODA LA OBTENCION DE LOS DATOS
                /*for(Marker marker:realTimeMarkers){
                    marker.remove();
                }*/
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {//ESTE FOR RECORRE TODOS LOS HIJOS DE USERS
                    if(snapshot.child(dataSnapshot.getKey()).child("connectedState").getValue().toString().equals("true")/* && snapshot.child(dataSnapshot.getKey()).child("helpState").getValue().toString().equals("true")*/){
                        System.out.println("AQUI SE COLOCAN MARCAS");
                        /**TODO: AQUI SE DIBUJAN MARCAS**/
                        //double lat = (double) snapshot.child(dataSnapshot.getKey()).child("lat").getValue();
                        //double lon = (double) snapshot.child(dataSnapshot.getKey()).child("lon").getValue();
                        //MarkerOptions markerOptions = new MarkerOptions();
                        //markerOptions.position(new LatLng(lat,lon));
                        //**tmpRealTimeMarker.add(mMap.addMarker(markerOptions));????**/
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updatePosition(double latitude,  double longitude){
        Usuario.getmDatabase().child("Users").child(Usuario.getId()).child("lat").setValue(latitude);
        Usuario.getmDatabase().child("Users").child(Usuario.getId()).child("lon").setValue(longitude);
    }
}