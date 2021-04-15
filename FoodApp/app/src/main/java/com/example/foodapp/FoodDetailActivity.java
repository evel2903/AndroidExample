package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Model.Food;
import com.example.foodapp.Utilities.InteractiveJson;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FoodDetailActivity extends AppCompatActivity {

    Food food;

    ImageView food_detail_img;
    TextView food_detail_title, food_detail_description, food_detail_price;
    Button food_detail_addtocart;

    ImageButton food_detail_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        Intent intent = getIntent();
        food = new Gson().fromJson(intent.getStringExtra("foodJson"), Food.class);

        food_detail_img = findViewById(R.id.food_detail_url);
        food_detail_title = findViewById(R.id.food_detail_title);
        food_detail_description = findViewById(R.id.food_detail_description);
        food_detail_price = findViewById(R.id.food_detail_price);
        food_detail_addtocart = findViewById(R.id.food_detail_addtocart);
        food_detail_back = findViewById(R.id.food_detail_back);

        food_detail_img.setImageResource(food.getImgURL());
        food_detail_title.setText(food.getName());
        food_detail_description.setText(food.getDescription());
        food_detail_price.setText(food.getPrice());

        food_detail_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InteractiveJson interactiveJson = new InteractiveJson();
                ArrayList<Food> cart = interactiveJson.foodJsonToArrayList(interactiveJson.getData(v.getContext(), "cart.json"));

                cart.add(food);
                interactiveJson.saveData(v.getContext(), "cart.json", new Gson().toJson(cart));
                Toast.makeText(v.getContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_LONG).show();

            }
        });

        food_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }
}