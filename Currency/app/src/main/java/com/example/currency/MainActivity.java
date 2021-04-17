package com.example.currency;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.currency.Model.CountryInfo;
import com.example.currency.Model.Geonames;
import com.example.currency.Model.RssItem;
import com.example.currency.Service.IGeonameService;
import com.example.currency.Service.LoadImageTask;
import com.example.currency.Service.RssService;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    ArrayList<CountryInfo> listCountryInfo;
    String [] listSelectCurrency;
    int idxFromSelected;
    int idxToSelected;

    private List<RssItem> rssItemList = new ArrayList<RssItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView fromValue = (TextView) findViewById(R.id.value_from_currency);
        fromValue.setText("0");

        loadDataToSpinner();

        Button convert = (Button) findViewById(R.id.convert);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new FetchFeedTask().execute((Void) null);

            }
        });

    }

    private void loadDataToSpinner(){
        IGeonameService.iGeonameService.listCountryInfo("evel2903").enqueue(new Callback<Geonames>() {
            @Override
            public void onResponse(Call<Geonames> call, Response<Geonames> response) {
                listCountryInfo = ( response.body().getGeonames());
                listSelectCurrency = new String[listCountryInfo.size()];

                for (int idx = 0; idx < listCountryInfo.size(); idx++) {
                    listSelectCurrency[idx] = listCountryInfo.get(idx).getCurrencyCode() + " - " + listCountryInfo.get(idx).getCountryName();
                }

                Spinner select_from_currency = (Spinner) findViewById(R.id.select_from_currency);
                select_from_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) view).setTextColor(Color.parseColor("#FF018786"));

                        new LoadImageTask((ImageView) findViewById(R.id.ensign_from)).execute("https://img.geonames.org/flags/x/" + listCountryInfo.get(position).getCountryCode().toLowerCase() + ".gif");

                        idxFromSelected = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                ArrayAdapter<String> fromAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.select_currency_item, listSelectCurrency);
                select_from_currency.setAdapter(fromAdapter);

                Spinner select_to_currency = (Spinner) findViewById(R.id.select_to_currency);
                select_to_currency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) view).setTextColor(Color.parseColor("#FF018786"));

                        new LoadImageTask((ImageView) findViewById(R.id.ensign_to)).execute("https://img.geonames.org/flags/x/" + listCountryInfo.get(position).getCountryCode().toLowerCase() + ".gif");

                        idxToSelected = position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                ArrayAdapter<String> toAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.select_currency_item, listSelectCurrency);
                select_to_currency.setAdapter(toAdapter);


            }
            @Override
            public void onFailure(Call<Geonames> call, Throwable t) {
            }
        });
    }

    private double exchangeRate(String from, String to){


        double result = 0;
        String mess = rssItemList.get(0).getDescription().trim();
        System.out.println(mess);
        int idxOfSpace = 0;
        int begin = -1;
        int end = -1;

        for (int idx = 0; idx < mess.length(); idx++) {
            if (Character.isSpace(mess.charAt(idx))) {
                idxOfSpace++;

                if (idxOfSpace == 3) {
                    begin = idx;
                }
                if (idxOfSpace == 4) {
                    end = idx;
                    break;
                }
            }

        }

        result = 1 / Double.parseDouble(mess.substring(begin, end));
        return result;
    }

    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean>{

        private String urlLink;

        @Override
        protected void onPreExecute() {
            String from = listCountryInfo.get(idxFromSelected).getCurrencyCode().toLowerCase();
            String to = listCountryInfo.get(idxToSelected).getCurrencyCode().toLowerCase();

            urlLink = "https://" + from + ".fxexchangerate.com/"+ to +".xml";
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL(urlLink);

                rssItemList = new RssService().parseRSS(url);
                return true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,
                        "Currency code re-selection",
                        Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                TextView fromValue = (TextView) findViewById(R.id.value_from_currency);
                TextView toValue = (TextView) findViewById(R.id.value_to_currency);

                double fValue = Double.parseDouble(fromValue.getText().toString());
                double tValue = fValue / exchangeRate(listCountryInfo.get(idxFromSelected).getCurrencyCode().toLowerCase(), listCountryInfo.get(idxToSelected).getCurrencyCode().toLowerCase());
                DecimalFormat format = new DecimalFormat("###,##0.000");
                toValue.setText(format.format(tValue));
            } else {
                Toast.makeText(MainActivity.this,
                        "Currency code re-selection",
                        Toast.LENGTH_LONG).show();

            }
        }
    }
}
