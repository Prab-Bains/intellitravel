package com.example.intellitravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SearchPage extends AppCompatActivity implements SearchBarFragment.SearchButtonClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setSelectedItemId(R.id.nav_search_home);

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
                intent.putExtra("clicked", item.toString());
                startActivity(intent);
                return false;
            }
        });

        SearchBarFragment searchBarFragment = new SearchBarFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.search_bar, searchBarFragment)
                .commit();

        CountryListFragment countryListFragment = new CountryListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.search_items_list, countryListFragment)
                .commit();
    }

    @Override
    public void onSearchButtonClick() {
        /* this gets the text from the search text field --> do what you need to with it */
        TextView searchText = findViewById(R.id.country_search_text_entry);
        String query = searchText.getText().toString();
        System.out.println(query);
    }
}