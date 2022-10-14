package com.example.intellitravel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

public class SearchSettingsFragment extends Fragment {

    public String[] settings;

    public SearchSettingsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getResources().getStringArray(R.array.search_settings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_settings, container, false);
        TextView textView = view.findViewById(R.id.searchSettings);
        StringBuilder settingsFullString = new StringBuilder();
        for (String str : settings) {
            settingsFullString.append(str).append("\n");
        }
        textView.setText(settingsFullString);
        return view;
    }
}
