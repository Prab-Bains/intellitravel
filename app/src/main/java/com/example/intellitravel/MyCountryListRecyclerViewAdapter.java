package com.example.intellitravel;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.intellitravel.databinding.FragmentItemBinding;
import com.google.android.gms.common.util.JsonUtils;

import java.util.List;

public class MyCountryListRecyclerViewAdapter extends RecyclerView.Adapter<MyCountryListRecyclerViewAdapter.ViewHolder> {

    private final List<String> mValues;

    public MyCountryListRecyclerViewAdapter(List<String> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public final TextView mIdView;
        public final TextView mContentView;
        public String mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
//            mIdView = binding.itemNumber;
            mContentView = binding.content;

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
}