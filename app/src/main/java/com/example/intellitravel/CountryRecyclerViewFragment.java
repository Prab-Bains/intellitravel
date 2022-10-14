package com.example.intellitravel;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CountryRecyclerViewFragment extends Fragment implements ItemClickListener {
    RecyclerView recyclerView;
    String[] countries, countryDescription;
    int[] flag_images = {R.drawable.canada, R.drawable.usa, R.drawable.japan,
            R.drawable.germany, R.drawable.uk, R.drawable.brazil};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country_recycler_view, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        countries = getResources().getStringArray(R.array.country_names_list);
        countryDescription = getResources().getStringArray(R.array.descriptions);

        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), countries, countryDescription, flag_images);
        myRecyclerViewAdapter.setClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(myRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onClick(View view, int position) {
        FurtherDetails furtherDetailsFragment = new FurtherDetails();
        Bundle bundle = new Bundle();
        bundle.putString("country", countries[position]);
        Log.d("country", countries[position]);
        furtherDetailsFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(
//                        R.anim.slide_in_right, // enter
//                        R.anim.fade_out, // exit
//                        R.anim.fade_in, // popEnter
//                        R.anim.slide_out_right // popExit
//                )
                .replace(R.id.listOfCountriesFragment, furtherDetailsFragment)
//                .addToBackStack(null)
                .commit();
    }
}
