package com.example.intellitravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPageFragment extends Fragment {

    public SearchPageFragment() {
        // Required empty public constructor
    }

    public static SearchPageFragment newInstance(String param1, String param2) {
        SearchPageFragment fragment = new SearchPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("onCreate in search page fragment");
//        SearchBarFragment searchBarFragment = new SearchBarFragment();
//        getChildFragmentManager()
//                .beginTransaction()
//                .add(R.id.search_bar, searchBarFragment)
//                .commit();
//
//        CountryListFragment countryListFragment = new CountryListFragment();
//        getChildFragmentManager()
//                .beginTransaction()
//                .add(R.id.search_items_list, countryListFragment)
//                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("onCreateView");
        SearchBarFragment searchBarFragment = new SearchBarFragment();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.search_bar, searchBarFragment)
                .commit();

        CountryListFragment countryListFragment = new CountryListFragment();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.search_items_list, countryListFragment)
                .commit();
        return inflater.inflate(R.layout.fragment_search_page, container, false);
    }
}