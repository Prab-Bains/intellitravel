package com.example.intellitravel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class SearchPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        System.out.println("loading search page");

        SearchPageFragment searchPageFragment = new SearchPageFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.search_page_fragment_container, searchPageFragment)
                .commit();

//        SearchBarFragment searchBarFragment = new SearchBarFragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.search_bar, searchBarFragment)
//                .commit();
//
//        CountryListFragment countryListFragment = new CountryListFragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.search_items_list, countryListFragment)
//                .commit();


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.bottom_nav_menu, menu);
//
//        System.out.println("creating menu");
//
////        final MenuItem item = menu.findItem(R.id.nav_search);
////        item.getActionView().setOnClickListener(v -> System.out.println("user wants to go to search page"));
//        return true;
//    }
//
//    @Override
//    @SuppressLint("NonConstantResourceId")
//    public boolean onOptionsItemSelected(MenuItem item) {
//        System.out.println(item);
//        switch (item.getItemId()) {
//            case R.id.nav_search:
//                System.out.println("user wants to go to search page");
//                return true;
//            case R.id.nav_map:
//                System.out.println("user wants to view map");
//                return true;
//            case R.id.nav_my_list:
//                System.out.println("user wants to view their list");
//                return true;
//            default:
//                System.out.println("who knows what the users wants tbh");
//                return true;
//        }
//    }

}