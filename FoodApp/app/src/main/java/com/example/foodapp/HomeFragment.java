package com.example.foodapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.foodapp.Adapter.FoodAdapter;
import com.example.foodapp.Model.Food;
import com.example.foodapp.Utilities.Convert;
import com.example.foodapp.Utilities.InteractiveJson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        // return inflater.inflate(R.layout.fragment_home, container, false);

        // Get view
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageSlider imageSlider = view.findViewById(R.id.home_slide);
        ArrayList <SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.banh_canh, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.bun_bo_hue, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.bun_mam, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(imageList, ScaleTypes.FIT);

        InteractiveJson interactiveJson = new InteractiveJson();
        ArrayList <Food> foods = interactiveJson.foodJsonToArrayList(interactiveJson.getData(view.getContext(), "food.json"));
        setFoodRecycler(foods, view);

        return view;
    }

    private void setFoodRecycler(ArrayList<Food> foodList, View view){
        RecyclerView foodRecycler = view.findViewById(R.id.food_recycler);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        foodRecycler.setLayoutManager(layoutManager);
        FoodAdapter foodAdapter = new FoodAdapter(view.getContext(), foodList);
        foodRecycler.setAdapter(foodAdapter);

    }

}