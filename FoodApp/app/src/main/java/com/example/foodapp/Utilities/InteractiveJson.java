package com.example.foodapp.Utilities;

import android.content.Context;
import android.widget.Toast;

import com.example.foodapp.Model.Food;
import com.example.foodapp.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class InteractiveJson {
    public ArrayList<Food> readJsonToCart(int r, Context context){
        String json = null ;
        try {
            InputStream inputStream = context.getResources().openRawResource(r);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Food[] jsonArr = new Gson().fromJson(json, Food[].class);
        ArrayList <Food> cart= new ArrayList<>();
        for (int idx = 0; idx < jsonArr.length; idx++) {
            cart.add(jsonArr[idx]);
        }
        return cart;
    }

    public ArrayList<Food> foodJsonToArrayList(String json){
        Food[] jsonArr = new Gson().fromJson(json, Food[].class);
        ArrayList <Food> cart= new ArrayList<>();
        for (int idx = 0; idx < jsonArr.length; idx++) {
            cart.add(jsonArr[idx]);
        }
        return cart;
    }

    public void saveData(Context context,String fileName, String mJsonResponse) {
        try {
            FileWriter file = new FileWriter(context.getFilesDir().getPath() + "/" + fileName);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getData(Context context, String fileName) {
        try {
            File f = new File(context.getFilesDir().getPath() + "/" + fileName);
            //check whether file exists
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
