package com.example.intellitravel;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.intellitravel.databinding.FragmentItemBinding;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MyCountryListRecyclerViewAdapter extends RecyclerView.Adapter<MyCountryListRecyclerViewAdapter.ViewHolder> {
    private final static String flagUrl = "https://countryflagsapi.com/png/";
    private String countryNameToShow;
    private final List<String> mValues;
    private final List<Drawable> mDrawables;

    public MyCountryListRecyclerViewAdapter(List<String> items) {
        mValues = items;
        mDrawables = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(holder.mItem);

        AsyncFlagGetter flagGetter = new AsyncFlagGetter();
        flagGetter.execute(new Pair<String, ImageView>(holder.mItem, holder.mImageView));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public String mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
//            mIdView = binding.itemNumber;
            mContentView = binding.content;
            mImageView = binding.imageView;

            mContentView.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            System.out.println("click!");

            TextView textView = view.findViewById(R.id.content);
            String countryPrettyName = (String) textView.getText();
            String countryName = countryPrettyName.replaceAll("[,\\(\\)]+", "");
            countryName = countryName.replaceAll("['\\s]+", "-");
            countryName = countryName.replaceAll("ô", "o");
            countryName = countryName.replaceAll("ü", "u");
//            ArrayList<String> countriesToShow = CountryListFragment.getCountriesToShow();
//            ArrayList<String> countryNames = CountryListFragment.getCountryNames();
//            ArrayList<String> sCountriesToShow = SearchPage.getCountriesToShow();
//            ArrayList<String> sCountryNames = SearchPage.getCountryNames();

            Intent intent = new Intent(view.getContext(), CountryDetails.class);
//            for (int i = 0; i < countryNames.size(); i++) {
//                if (countriesToShow.get(i).equals(countryName)) {
//                    intent.putExtra("country_name", countryNames.get(i));
//                    intent.putExtra("country_pretty_name", countriesToShow.get(i));
//                    view.getContext().startActivity(intent);
//                    break;
//                }
//            }
//            System.out.println(countryName);
//            for (int i = 0; i < sCountryNames.size(); i++) {
//                if (sCountriesToShow.get(i).equals(countryName)) {
//                    intent.putExtra("country_name", sCountryNames.get(i));
//                    intent.putExtra("country_pretty_name", sCountriesToShow.get(i));
//                    view.getContext().startActivity(intent);
//                    break;
//                }
//            }
            intent.putExtra("country_name", countryName);
            intent.putExtra("country_pretty_name", countryPrettyName);
            view.getContext().startActivity(intent);

//            System.out.println(countryNames);
//            System.out.println(sCountryNames);
        }
    }

    private class AsyncFlagGetter extends AsyncTask<Pair<String, ImageView>, Void, Pair<ImageView, Drawable>> {
        @SafeVarargs
        @Override
        protected final Pair<ImageView, Drawable> doInBackground(Pair<String, ImageView>... country) {
            InputStream inputStream = null;

            try {
                String countryFlagUrl = flagUrl + country[0].first;
                countryFlagUrl = countryFlagUrl.replace(" ", "%20");
                countryFlagUrl = countryFlagUrl.replace("&", "");
                switch (country[0].first) {
                    case "Brunei": {
                        countryFlagUrl = flagUrl + "bn";
                        break;
                    }
                    case "Cook Islands": {
                        countryFlagUrl = flagUrl + "ck";
                        break;
                    }
                    case "Côte d'Ivoire (Ivory Coast)": {
                        countryFlagUrl = flagUrl + "ci";
                        break;
                    }
                    case "Israel, the West Bank and the Gaza Strip": {
                        countryFlagUrl = flagUrl + "il";
                        break;
                    }
                    case "Micronesia (FSM)": {
                        countryFlagUrl = flagUrl + "fm";
                        break;
                    }
                    case "South Korea": {
                        countryFlagUrl = flagUrl + "kr";
                        break;
                    }
                    case "Turks and Caicos Islands": {
                        countryFlagUrl = flagUrl + "tc";
                        break;
                    }
                    case "Türkiye": {
                        countryFlagUrl = flagUrl + "tr";
                        break;
                    }
                    case "": {
                        countryFlagUrl = "";
                        break;
                    }
                    default: break;
                }
                inputStream = (InputStream) new URL(countryFlagUrl).getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new Pair<>(country[0].second, Drawable.createFromStream(inputStream, "aa"));
        }

        @Override
        protected void onPostExecute(Pair<ImageView, Drawable> result) {
            if (result.second != null) {
                result.first.setImageDrawable(result.second);
            }
        }
    }
}