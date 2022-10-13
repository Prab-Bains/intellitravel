package com.example.intellitravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    }
}
