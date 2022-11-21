package com.example.intellitravel;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchBarFragment extends Fragment {

    private SearchButtonClickListener searchButtonClickListener;

    public SearchBarFragment() {
        // Required empty public constructor
    }

    public static SearchBarFragment newInstance(String param1, String param2) {
        SearchBarFragment fragment = new SearchBarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_bar, container, false);
        Button searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchButtonClickListener.onSearchButtonClick();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.searchButtonClickListener = (SearchButtonClickListener) context;
    }

    interface SearchButtonClickListener {
        void onSearchButtonClick();
    }
}