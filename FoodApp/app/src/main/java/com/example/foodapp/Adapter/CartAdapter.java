package com.example.foodapp.Adapter;

import android.content.Context;
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
import com.example.foodapp.Model.Food;
import com.example.foodapp.R;
import com.example.foodapp.Utilities.InteractiveJson;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    public CartAdapter(Context context, ArrayList<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    Context context;
    ArrayList <Food> foodList;

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_list_item,parent,false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.img.setImageResource(foodList.get(position).getImgURL());
        holder.title.setText(foodList.get(position).getName());
        holder.price.setText(foodList.get(position).getPrice());

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InteractiveJson interactiveJson = new InteractiveJson();
                ArrayList <Food> cart = interactiveJson.foodJsonToArrayList(interactiveJson.getData(v.getContext(), "cart.json"));

                for (int idx = 0; idx < cart.size(); idx++) {
                    if (cart.get(idx).getId() == foodList.get(position).getId()){
                        cart.remove(idx);
                        break;
                    }
                }

                interactiveJson.saveData(v.getContext(), "cart.json", new Gson().toJson(cart));

                Toast.makeText(v.getContext(), "Đã xóa khỏi giỏ hàng", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static final class CartViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title, price;
        ImageButton del;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cart_list_item_url);
            title = itemView.findViewById(R.id.cart_list_item_title);
            price = itemView.findViewById(R.id.cart_list_item_price);
            del = itemView.findViewById(R.id.cart_list_item_del);
        }
    }



}
