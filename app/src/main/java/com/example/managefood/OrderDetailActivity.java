package com.example.managefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managefood.Adapter.CartAdapter;
import com.example.managefood.Adapter.ChiTietHoaDonAdapter;
import com.example.managefood.Model.Food;
import com.example.managefood.Model.FoodOrder;
import com.example.managefood.Model.HoaDon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    TextView tvMa, tvKhach, tvTien, tvThoiGian;
    RecyclerView rvList;
    HoaDon hoaDon;
    DatabaseReference databaseReference;
    List<FoodOrder> foodOrders;
    ChiTietHoaDonAdapter cartAdapter;
    DecimalFormat formatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);
        initView();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String ma = bundle.getString("HOADON");
        rvList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(linearLayoutManager);
        formatter = new DecimalFormat("###,###,###");
        databaseReference.child("DonHang").orderByKey().equalTo(ma).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> convert = snapshot.getChildren();
                for (DataSnapshot children : convert) {
                    hoaDon = children.getValue(HoaDon.class);
                    hoaDon.setMaHoaDon(children.getKey());
                }
                tvKhach.setText("Người mua  : "+hoaDon.getKhachHang());
                tvMa.setText("Mã đơn        : "+hoaDon.getMaHoaDon());
                tvThoiGian.setText("Thời gian     : "+hoaDon.getThoiGian());
                tvTien.setText("Tổng tiền     : "+formatter.format(hoaDon.getTongTien()) +" VNĐ");
                foodOrders = hoaDon.getFoodOrders();
                cartAdapter = new ChiTietHoaDonAdapter(foodOrders);
                rvList.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void initView() {
        tvMa = findViewById(R.id.tvMaHDCT);
        tvKhach = findViewById(R.id.tvKhachHDCT);
        tvTien = findViewById(R.id.tvTongTienHDCT);
        tvThoiGian = findViewById(R.id.tvThoiGianHDCT);
        rvList = findViewById(R.id.rvListHDCT);
    }
}