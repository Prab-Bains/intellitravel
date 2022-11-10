package com.example.intellitravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CountryDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CountryDetailsFragment extends Fragment {

    String countryName = "Canada";

    public CountryDetailsFragment() {
        // Required empty public constructor
    }
    public static CountryDetailsFragment newInstance(String countryName, String param2) {
        CountryDetailsFragment fragment = new CountryDetailsFragment();
        Bundle args = new Bundle();
        args.putString("countryName", countryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            countryName = getArguments().getString("countryName");
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country_details, container, false);

        FloatingActionButton fab = view.findViewById(R.id.favourite_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("button clicked!");

                Toast toast = Toast.makeText(getContext(),countryName + " added to Favourites", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return view;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}