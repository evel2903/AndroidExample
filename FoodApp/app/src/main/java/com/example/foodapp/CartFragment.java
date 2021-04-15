package com.example.foodapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.Adapter.CartAdapter;
import com.example.foodapp.Adapter.FoodAdapter;
import com.example.foodapp.Model.Food;
import com.example.foodapp.Utilities.Convert;
import com.example.foodapp.Utilities.InteractiveJson;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String phoneNumber ="0979374764";
    String message = "Danh sách món ăn: ";

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        TextView total = view.findViewById(R.id.cart_total_price);

        InteractiveJson interactiveJson = new InteractiveJson();

        ArrayList <Food> cart = interactiveJson.foodJsonToArrayList(interactiveJson.getData(view.getContext(), "cart.json"));

        setCartRecycler(cart,view);
        total.setText(getTotalPrice(cart));

        Button sendOrder = view.findViewById(R.id.cart_order);
        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Food f :
                        cart) {
                    message += f.getName() + ", ";
                }
                message = message + "\nTổng thanh toán: " + getTotalPrice(cart);

                if (checkPermission(view.getContext(), Manifest.permission.SEND_SMS)){
                    sendOrderSMS(phoneNumber, message);

                    ArrayList <Food> cart = new ArrayList<Food>();

                    InteractiveJson interactiveJson = new InteractiveJson();
                    interactiveJson.saveData(view.getContext(), "cart.json", new Gson().toJson(cart));

                    setCartRecycler(cart, view);
                    total.setText(getTotalPrice(cart));
                }
                else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS},100);
                }

            }
        });

        return view;
    }

    private void setCartRecycler(ArrayList<Food> foodList, View view){
        RecyclerView cartRecycler = view.findViewById(R.id.cart_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        cartRecycler.setLayoutManager(layoutManager);

        CartAdapter cartAdapter = new CartAdapter(view.getContext(), foodList);
        cartRecycler.setAdapter(cartAdapter);
    }

    private String getTotalPrice(ArrayList<Food> foodList){
        Convert convert = new Convert();
        long total = 0;
        for (Food food:
             foodList) {
            total += convert.VNDFormatToLong(food.getPrice());
        }
        return convert.longToVNDFormat(total);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }


    private boolean checkPermission(Context context, String permission){
        int check = ContextCompat.checkSelfPermission(context, permission);
        return check == PackageManager.PERMISSION_GRANTED;
    }
    private void sendOrderSMS(String phoneNumber, String message){
        if (!phoneNumber.equals("") && !message.equals("")){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

            Toast.makeText(this.getContext(), "Đặt thành công", Toast.LENGTH_LONG).show();
            System.out.println("mess length " +message.length());
            System.out.println("Thành công");

        }
        else {
            Toast.makeText(this.getContext(), "Đặt thất bại", Toast.LENGTH_LONG).show();
            System.out.println("Thất bại");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            sendOrderSMS(phoneNumber, message);
        }
        else {
            Toast.makeText(this.getContext(), "PERMISSION Denied", Toast.LENGTH_LONG).show();
            System.out.println("PERMISSION Denied");
        }
    }
}