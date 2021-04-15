package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.CartFragment;
import com.example.foodapp.FoodDetailActivity;
import com.example.foodapp.Model.Food;
import com.example.foodapp.R;
import com.example.foodapp.Utilities.InteractiveJson;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    public FoodAdapter(Context context, ArrayList<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    Context context;
    ArrayList <Food> foodList;

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_list_item,parent,false);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.img.setImageResource(foodList.get(position).getImgURL());
        holder.title.setText(foodList.get(position).getName());
        holder.price.setText(foodList.get(position).getPrice());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FoodDetailActivity.class);
                intent.putExtra("foodJson", new Gson().toJson(foodList.get(position)));

                context.startActivity(intent);
            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InteractiveJson interactiveJson = new InteractiveJson();
                ArrayList <Food> cart = interactiveJson.foodJsonToArrayList(interactiveJson.getData(v.getContext(), "cart.json"));

                cart.add(foodList.get(position));
                interactiveJson.saveData(v.getContext(), "cart.json", new Gson().toJson(cart));
                Toast.makeText(v.getContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static final class FoodViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title, price;
        ImageButton addToCart;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.food_list_item_url);
            title = itemView.findViewById(R.id.food_list_item_title);
            price = itemView.findViewById(R.id.food_list_item_price);
            addToCart = itemView.findViewById(R.id.food_list_item_addtocart);
        }
    }

}
