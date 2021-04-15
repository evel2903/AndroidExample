package com.example.temperature;

public class Convert {
    public float celsiusToFahrenheit(float celsius){return (celsius * 1.8f) + 32; }
    public float fahrenheitToCelsius(float fahrenheit){
        return (fahrenheit - 32 ) / 1.8f ;
    }

}
