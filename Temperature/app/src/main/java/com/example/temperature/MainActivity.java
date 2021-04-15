package com.example.temperature;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText c = (EditText) findViewById(R.id.number_C);
        final EditText fahrenheit = (EditText) findViewById(R.id.fahrenheit);
        final EditText f = (EditText) findViewById(R.id.number_F);
        final EditText celsius = (EditText) findViewById(R.id.celsius);


        c.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    float data = new Convert().celsiusToFahrenheit(Float.parseFloat(String.valueOf(c.getText())));

                    fahrenheit.setText(Float.toString(data));
                }
                else {
                    fahrenheit.setText("");
                }
            }
        });

        f.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    float data = new Convert().fahrenheitToCelsius(Float.parseFloat(String.valueOf(f.getText())));

                    celsius.setText(Float.toString(data));
                }
                else {
                    celsius.setText("");
                }
            }

        });
    }
}