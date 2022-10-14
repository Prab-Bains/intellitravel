package com.example.intellitravel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;

import com.example.intellitravel.CountryListFragment;
import com.example.intellitravel.R;

public class SearchPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountryListFragment countryListFragment = new CountryListFragment();
        SearchSettingsFragment searchSettingsFragment = new SearchSettingsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.linear_layout, countryListFragment).commit();

        Button button = findViewById(R.id.buttonSearchSettings);
        button.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
            fragmentTransaction1.replace(R.id.linear_layout, searchSettingsFragment);
            fragmentTransaction1.commit();
        });

        Button toSuggestedCountries = findViewById(R.id.buttonSuggestedCountries);
        toSuggestedCountries.setOnClickListener(view -> {
            Intent intent = new Intent(this, SuggestedCountries.class);
            startActivity(intent);
        });

//        Button toFurtherDetails = findViewById(R.id.buttonFurtherDetails);
//        toFurtherDetails.setOnClickListener(view -> {
//            Intent intent = new Intent(this, FurtherDetails.class);
//            startActivity(intent);
//        });
    }
}