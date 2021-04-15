package com.example.foodapp.Model;


import com.example.foodapp.R;

import java.util.ArrayList;

public class Food {
    private int id;
    private  String name;
    private  Integer imgURL;
    private String description;
    private  String price;

    public Food() {
    }

    public Food(int id, String name, Integer imgURL, String description, String price) {
        this.id = id;
        this.name = name;
        this.imgURL = imgURL;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImgURL() {
        return imgURL;
    }

    public void setImgURL(Integer imgURL) {
        this.imgURL = imgURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
