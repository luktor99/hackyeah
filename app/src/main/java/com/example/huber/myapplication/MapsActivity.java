package com.example.huber.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.huber.myapplication.PermissionUtils;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private Circle mMapCircle;
    LocationRequest mLocationRequest;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private Boolean mLocationPermissionGranted = false;

    // Google map callbacks overrides:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Log.d("GoogleApiClient", "Creating Google Api Client.");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mLocationRequest = new LocationRequest();
        Log.d("Location Services", "Creating map interface and location listener.");
        mLocationRequest.setInterval(30*1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mLocationRequest.setFastestInterval(3*1000);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        enableMyLocation();

        Log.d("GoogleMap", "Map is ready.");
    }

    //Google API Client callbacks overrides:

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("GoogleApiClient", "Stopping Google Api Client.");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        mGoogleApiClient.disconnect();
    }

    public void onClick(View view) {

        if (mLastLocation != null) {
            Log.i("Location", mLastLocation.toString());
        } else {
            Log.i("Location", "No known last location");
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LocationService", "CHANGED");
        mLastLocation = location;

        Location myLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        // Add a marker in my location and move the camera
        LatLng myPosition = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

        Log.d("GoogleMap", "Moving camera to my location.");
        mMap.addMarker(new MarkerOptions().position(myPosition).title("YOU"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 14), 1500, null);

        Toast.makeText(this, mLastLocation.getLatitude() +", "+ mLastLocation.getLatitude(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        Log.d("GoogleApiClient", "GoogleApiClient connected, setting location updates");

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
        Log.i("GoogleApiClient", "GoogleApiClient connection suspended");
        retryConnecting();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result){
        Log.i("GoogleApiClient", "GoogleApiClient connection failed: " + result.toString());
        retryConnecting();
    }

    // Device map permissions overrides:

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mLocationPermissionGranted = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mLocationPermissionGranted) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mLocationPermissionGranted = false;
        }
    }

//    //Calling REST API:
//    public void onPointRequestGet(){
//        Log.d("RESTApi","Got random point nearby!");
//        if(mMapCircle != null){
//            mMapCircle.remove();
//        }
//        CircleOptions circleOptions = new CircleOptions();
//        circleOptions.center(new LatLng(mlocation.getLatitude(),location.getLongitude()));
//        circleOptions.radius(700);
//        circleOptions.fillColor(Color.TRANSPARENT);
//        circleOptions.strokeWidth(6);
//        mapCircle = mapView.addCircle(circleOption);
//    }

    //Utility functions:

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    private void retryConnecting() {
        if (!mGoogleApiClient.isConnecting()) {
            Log.d("GoogleApiClient", "Retrying connection.");
              mGoogleApiClient.connect();
        }
    }
}
