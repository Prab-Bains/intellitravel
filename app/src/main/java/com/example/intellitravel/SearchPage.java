package com.example.intellitravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class SearchPage extends AppCompatActivity implements SearchBarFragment.SearchButtonClickListener {
    private final String url = "https://sub.yurtle.net/";
    RecyclerView recyclerView;
    private ArrayList<String> countriesToShow = new ArrayList<>();
    String query;
    private SearchPage.AsyncTaskRunner runner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setSelectedItemId(R.id.nav_search_home);

        recyclerView = findViewById(R.id.list);

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
        query = searchText.getText().toString();
        recyclerView = findViewById(R.id.list);
//        ArrayList<String> newCountries = new ArrayList<>();
//        newCountries.add("Cambodia");
//        newCountries.add("Canada");
//        newCountries.add("Mexico");
//        newCountries.add("USA");
//        ArrayList<String> countriesToShow = new ArrayList<>();
//        if (query.length() > 0) {
//            String queryLowercase = query.toLowerCase();
//            for (int i = 0; i < newCountries.size(); i++) {
//                String countryLowercase = newCountries.get(i).toLowerCase();
//                if (countryLowercase.contains(queryLowercase)) {
//                    countriesToShow.add(newCountries.get(i));
//                }
//            }
//        }
//        recyclerView.setAdapter(new MyCountryListRecyclerViewAdapter(countriesToShow));
        System.out.println(query);
        String tempUrl = url + "/countries";
        runner = new SearchPage.AsyncTaskRunner(new MyCountryListRecyclerViewAdapter(countriesToShow));
        runner.execute(tempUrl);
    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        MyCountryListRecyclerViewAdapter adapter;

        public AsyncTaskRunner(MyCountryListRecyclerViewAdapter myContext) {
            this.adapter = myContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(SearchPage.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, strings[0],
                    null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    System.out.println(response);
                    if (query.length() > 0) {
                        String queryLowercase = query.toLowerCase();
                        countriesToShow = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject country = response.getJSONObject(i);
                                String countryName = country.getString("prettyName");
                                String countryNameLowercase = countryName.toLowerCase();
                                if (countryNameLowercase.contains(queryLowercase)) {
                                    countriesToShow.add(countryName);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println(countriesToShow);
                    }
                    recyclerView.setAdapter(new MyCountryListRecyclerViewAdapter(countriesToShow));
                }
            }, error -> Toast.makeText(SearchPage.this, error.toString(),
                    Toast.LENGTH_SHORT).show());
            queue.add(request);
            return "";
        }
    }
}