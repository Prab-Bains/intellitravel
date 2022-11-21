package com.example.intellitravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;
import java.util.Locale;

// Implement OnMapReadyCallback.
public class MapView extends AppCompatActivity implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, SearchBarFragment.SearchButtonClickListener {

    private GoogleMap map;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    private Location userCurrentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout file as the content view.
        setContentView(R.layout.activity_map_view);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setSelectedItemId(R.id.nav_map_view);

        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Class destination = null;

                switch (item.toString()) {
                    case "Search":
                        destination = SearchPage.class;
                        break;
                    case "Map":
                        destination = MapView.class;
                        break;
                    case "My List":
                        destination = UserFavourites.class;
                        break;
                }

                Intent intent = new Intent(getApplicationContext(), destination);
                startActivity(intent);
                return false;
            }
        });

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.nav_host_fragment_activity_map_view, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);

        SearchBarFragment searchBarFragment = new SearchBarFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.search_bar, searchBarFragment)
                .commit();

//        Button searchButton = findViewById(R.id.button);
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView searchText = findViewById(R.id.editTextTextPersonName);
//                String query = searchText.getText().toString();
//                System.out.println(query);
//            }
//        });

    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.map = googleMap;

        enableMyLocation();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    userCurrentLocation = location;
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            this.map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
                    .draggable(true)
                    .title("Current location"));
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                Geocoder geocoder = new Geocoder(MapView.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addresses.size() > 0) {
                        String country = addresses.get(0).getCountryName();
                        System.out.println(country);

                        Intent intent = new Intent(MapView.this, CountryDetails.class);
                        intent.putExtra("country_name", country);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     *
     * This is from the Google Maps docs example.
     * https://developers.google.com/maps/documentation/android-sdk/location
     */
    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            this.map.setMyLocationEnabled(true);
            return;
        }

        // 2. Otherwise, request location permissions from the user.
        String[] permissionArray = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(this, permissionArray, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
        }
    }

    @Override
    public void onSearchButtonClick() {
        TextView searchText = findViewById(R.id.country_search_text_entry);
        String query = searchText.getText().toString();
        System.out.println(query);

        Geocoder geocoder = new Geocoder(this);

        try {
            List<Address> locations = geocoder.getFromLocationName(query, 1);
            LatLng country = new LatLng(locations.get(0).getLatitude(), locations.get(0).getLongitude());

            map.clear();

            map.moveCamera(CameraUpdateFactory.newLatLng(country));
            map.animateCamera(CameraUpdateFactory.zoomTo(3));

            map.addMarker(new MarkerOptions()
                    .position(country)
                    .draggable(true)
                    .title(query));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}