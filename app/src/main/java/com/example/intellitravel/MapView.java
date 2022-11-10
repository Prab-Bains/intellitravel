package com.example.intellitravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

// Implement OnMapReadyCallback.
public class MapView extends AppCompatActivity implements OnMapReadyCallback {

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

    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .draggable(true)
                .title("Marker"));
    }
}