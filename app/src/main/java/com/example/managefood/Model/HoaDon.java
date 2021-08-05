package com.example.managefood.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HoaDon implements Serializable {
    private String maHoaDon,khachHang;
    private String thoiGian;
    private List<FoodOrder> foodOrders;
    private double tongTien;

    public HoaDon() {

    }

    public HoaDon(String khachHang, String thoiGian, List<FoodOrder> foodOrders, double tongTien) {
        this.khachHang = khachHang;
        this.thoiGian = thoiGian;
        this.foodOrders = foodOrders;
        this.tongTien = tongTien;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(String khachHang) {
        this.khachHang = khachHang;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public List<FoodOrder> getFoodOrders() {
        return foodOrders;
    }

    public void setFoodOrders(List<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}


