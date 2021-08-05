package com.example.managefood.Model;

import java.text.DecimalFormat;

public class FoodOrder {
    Food food;
    int soLuong;

    public FoodOrder() {
    }

    public FoodOrder(Food food, int soLuong) {
        this.food = food;
        this.soLuong = soLuong;
    }

    public double getThanhTien(){
        return food.getPrice()*this.soLuong;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
