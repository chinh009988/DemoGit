package com.example.managefood.Model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Food implements Serializable {
    private String ID,Type,Name,Description ,Image,Status;
    private int Price;

    public Food() {
    }

    public Food(String Type, String name, String description, int price, String status, String image) {
        this.Type = Type;
        Name = name;
        Description = description;
        Price = price;
        Status = status;
        Image = image;
    }
    public String getPriceFormated(){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(Price);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }



    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatus() {
        return Status;
    }
}
