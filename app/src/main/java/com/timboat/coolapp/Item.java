package com.timboat.coolapp;

/**
 * Created by Timmy on 2018-03-03.
 */

public class Item {

    private String name;
    private int type; // 0 -> clothes, 1-> tech 2-> Grocery
    private int price;
    private String store;

    public Item(String name, int type, int price, String store)
    {
        this.name = name;
        this.type = type;
        this.price = price;
        this.store = store;
    }

    public String getName(){
        return name;
    }
    public int getType(){
        return type;
    }
    public int getPrice(){return price;}
    public String getStore(){return store;}

}
