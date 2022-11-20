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

public class MyCountryListRecyclerViewAdapter extends RecyclerView.Adapter<MyCountryListRecyclerViewAdapter.ViewHolder> {
    private final static String flagUrl = "https://countryflagsapi.com/png/";

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
            String countryName = (String) textView.getText();

            Intent intent = new Intent(view.getContext(), CountryDetails.class);
            intent.putExtra("country_name", countryName);
            view.getContext().startActivity(intent);
        }
    }

    private class AsyncFlagGetter extends AsyncTask<Pair<String, ImageView>, Void, Pair<ImageView, Drawable>> {
        @SafeVarargs
        @Override
        protected final Pair<ImageView, Drawable> doInBackground(Pair<String, ImageView>... country) {
            InputStream inputStream = null;

            try {
                inputStream = (InputStream) new URL(flagUrl + country[0].first).getContent();
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