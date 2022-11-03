package com.example.intellitravel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserFavourites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_favourites);

        CountryListFragment countryListFragment = new CountryListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.search_items_list, countryListFragment)
                .commit();
    }
}