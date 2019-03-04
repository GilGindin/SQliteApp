package com.e.sqliteapp;

import java.io.Serializable;

public class Product implements Serializable {

    private int _id ;
    private String name;
    private double price;



    public Product(){ }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product(int _id, String name, double price) {
      this(name,price);
       this._id = _id;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) { this._id = _id; }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
