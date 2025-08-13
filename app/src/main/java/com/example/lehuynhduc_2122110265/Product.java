package com.example.lehuynhduc_2122110265;

public class Product {
    private String name;
    private double price;
    private int imageResId; // ID hình ảnh trong drawable

    public Product(String name, double price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}
