package com.example.managefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.managefood.Adapter.HoaDonAdapter;
import com.example.managefood.Interface.OnItemHoaDonClicked;
import com.example.managefood.Model.Food;
import com.example.managefood.Model.HoaDon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    List<HoaDon> hoaDonList;
    HoaDonAdapter hoaDonAdapter;
    RecyclerView rvList;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        imgBack = findViewById(R.id.img_BackDonHang);
        rvList = findViewById(R.id.lv_oder);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        hoaDonList = new ArrayList<>();
        hoaDonAdapter = new HoaDonAdapter(hoaDonList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setHasFixedSize(true);
        databaseReference.child("DonHang").orderByChild("khachHang").equalTo(MainActivity.user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> convert = snapshot.getChildren();
                for (DataSnapshot children : convert) {
                    HoaDon hoaDon = children.getValue(HoaDon.class);
                    hoaDon.setMaHoaDon(children.getKey() + "");
                    hoaDonList.add(hoaDon);
                }
                rvList.setAdapter(hoaDonAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        hoaDonAdapter.setOnItemHoaDonClicked(new OnItemHoaDonClicked() {
            @Override
            public void onClick(String maHoaDon) {
                Intent intent = new Intent(OrderActivity.this,OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("HOADON",maHoaDon);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}