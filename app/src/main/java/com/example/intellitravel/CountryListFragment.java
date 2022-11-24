package com.example.intellitravel;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * A fragment representing a list of Items.
 */
public class CountryListFragment extends Fragment {
    private final String url = "https://sub.yurtle.net/";

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private static ArrayList<String> countriesToShow = new ArrayList<>();
    private static ArrayList<String> countryNames = new ArrayList<>();
    private CountryListFragment.AsyncTaskRunner runner;
    RecyclerView recyclerView;

    public void setCountriesToShow(ArrayList<String> test) {
        this.countriesToShow = test;
    }

    public static ArrayList<String> getCountriesToShow() {
        return countriesToShow;
    }

    public static ArrayList<String> getCountryNames() {
        return countryNames;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CountryListFragment() {
    }

    @SuppressWarnings("unused")
    public static CountryListFragment newInstance(int columnCount) {
        CountryListFragment fragment = new CountryListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        List<String> countries_list = countriesToShow;

        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.getStringArray("countries") != null) {
            String[] countries = bundle.getStringArray("countries");
            countriesToShow.addAll(Arrays.asList(countries));
        } else {
            String tempUrl = url + "/countries";
            runner = new CountryListFragment.AsyncTaskRunner(new MyCountryListRecyclerViewAdapter(countriesToShow));
            runner.execute(tempUrl);
        }

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyCountryListRecyclerViewAdapter(countries_list));
        }
        return view;
    }

    private class AsyncTaskRunner extends AsyncTask<String, Void, String> {
        MyCountryListRecyclerViewAdapter adapter;

        public AsyncTaskRunner(MyCountryListRecyclerViewAdapter myContext) {
            this.adapter = myContext;
        }

        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue(requireActivity());
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, strings[0],
                    null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    System.out.println(response);
                    Random random = new Random();
                    while (countriesToShow.size() != 6) {
                        int index = random.nextInt(response.length());
                        JSONObject country = null;
                        try {
                            country = response.getJSONObject(index);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String countryName = null;
                        String countryPrettyName = null;
                        try {
                            assert country != null;
                            countryName = country.getString("name");
                            countryPrettyName = country.getString("prettyName");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        while (countriesToShow.contains(countryPrettyName)) {
                            index = random.nextInt(response.length());
                            try {
                                country = response.getJSONObject(index);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                countryName = country.getString("name");
                                countryPrettyName = country.getString("prettyName");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        countryNames.add(countryName);
                        countriesToShow.add(countryPrettyName);
                    }
                    System.out.println(countriesToShow);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }, error -> Toast.makeText(getActivity(), error.toString(),
                    Toast.LENGTH_SHORT).show());
            queue.add(request);
            return "";
        }
    }
}