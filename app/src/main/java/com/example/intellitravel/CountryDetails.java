package com.example.intellitravel;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.intellitravel.databinding.ActivityCountryDetailsBinding;
import com.google.android.material.navigation.NavigationBarView;

public class CountryDetails extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityCountryDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCountryDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_search_home, R.id.nav_map_view, R.id.nav_my_list)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_country_details);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                System.out.println("item clicked");
                System.out.println(item);

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

                Intent intent = new Intent(CountryDetails.this, destination);
                intent.putExtra("clicked", item.toString());
                startActivity(intent);
                return false;
            }
        });

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map_sub_view, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .draggable(true)
                .title("Marker"));
    }
}