package com.example.intellitravel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class CountryViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_view);

        Bundle bundle = getIntent().getExtras();
        String country = bundle.getString("country");

        TextView textView = this.findViewById(R.id.country_label);

        textView.setText(country);

        Spinner countrySpinner = findViewById(R.id.airport_options);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.airports,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(R.layout.activity_country_view);
        countrySpinner.setAdapter(arrayAdapter);
    }
}