package com.example.intellitravel;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class CountryListFragment extends ListFragment {

    String[] names;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        names = getResources().getStringArray(R.array.country_names_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, names);
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Toast.makeText(requireActivity().getBaseContext(), names[position] + " position " + position + " id " + id, Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putString("country", names[position]);

        Intent intent = new Intent(CountryListFragment.this.getContext(), CountryViewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}