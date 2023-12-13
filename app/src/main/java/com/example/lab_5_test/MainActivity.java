package com.example.lab_5_test;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ListView currencyRatesListView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyRatesListView = findViewById(R.id.currencyRatesListView);

        new GetCurrencyRatesTask().execute();
    }

    private class GetCurrencyRatesTask extends AsyncTask<Void, Void, List<CurrencyRates>> {
        @Override
        protected List<CurrencyRates> doInBackground(Void... voids) {
            try {
                URL url = new URL("https://api.exchangerate-api.com/v4/latest/USD");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder jsonString = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    jsonString.append(line);
                }

                JSONObject jsonObject = new JSONObject(jsonString.toString());
                JSONObject ratesObject = jsonObject.getJSONObject("rates");
                List<CurrencyRates> currencyRatesList = new ArrayList<>();

                for (Iterator<String> it = ratesObject.keys(); it.hasNext(); ) {
                    String currency = it.next();
                    String rate = ratesObject.getString(currency);
                    currencyRatesList.add(new CurrencyRates(currency, rate));
                }

                return currencyRatesList;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<CurrencyRates> currencyRatesList) {
            super.onPostExecute(currencyRatesList);

            if (currencyRatesList != null) {
                List<String> currencyRateStrings = new ArrayList<>();

                for (CurrencyRates currencyRates : currencyRatesList) {
                    currencyRateStrings.add(currencyRates.getCurrency() + ": " + currencyRates.getRate());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, currencyRateStrings);
                currencyRatesListView.setAdapter(arrayAdapter);
            }
        }
    }
}