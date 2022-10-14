package com.example.intellitravel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FurtherDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FurtherDetails extends Fragment {
    public FurtherDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param country String.
     * @return A new instance of fragment FurtherDetails.
     */
    public static FurtherDetails newInstance(String country) {
        FurtherDetails fragment = new FurtherDetails();
        Bundle args = new Bundle();
        args.putString("country", country);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_further_details, container, false);
        if (getArguments() != null) {
            String country = getArguments().getString("country");
            TextView nameView = view.findViewById(R.id.countryNameView);
            nameView.setText(country);
        }
        return view;
    }
}