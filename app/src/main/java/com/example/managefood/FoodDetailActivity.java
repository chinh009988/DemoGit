package com.example.managefood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managefood.Model.Food;
import com.example.managefood.Model.FoodOrder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FoodDetailActivity extends AppCompatActivity {
    //hiển thị thông tin chi tiết sản phẩm , 2 button mua ngay và thêm vào giỏ hàng
    ImageView imgSanPham;
    TextView tvTen,tvGia,tvMoTa,tvSoLuong,tvCong,tvTru;
    Button btnThemGioHang;
    Food food;
    DecimalFormat formatter;
    int soLuong = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        initView();

        Intent intent = getIntent();
        formatter = new DecimalFormat("###,###,###");
        Bundle bundle = intent.getExtras();
        food = (Food) bundle.getSerializable("FOOD");
        tvTen.setText(food.getName());
        tvGia.setText(food.getPriceFormated()+"đ");
        tvMoTa.setText(food.getDescription());
        Picasso.with(FoodDetailActivity.this).load(food.getImage()).into(imgSanPham);


        tvCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soLuong++;
                tvSoLuong.setText(""+soLuong);
                tvGia.setText(formatter.format(soLuong*food.getPrice())+ "đ");
            }
        });

        tvTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(soLuong>1){
                   soLuong--;
                   tvSoLuong.setText(""+soLuong);
                   tvGia.setText(formatter.format(soLuong*food.getPrice())+ "đ");
               }else{
                   tvSoLuong.setText(""+soLuong);
                   Log.e("SỐ lượng",""+soLuong);
               }
            }
        });

        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HomeActivity.listFoodOrder.size()>0){
                    for (int i = 0; i < HomeActivity.listFoodOrder.size(); i++){
                        //nếu ID giống nhau => sản phẩm đã tồn tại trong giỏ hàng:
                        if(food.getID().equalsIgnoreCase(HomeActivity.listFoodOrder.get(i).getFood().getID())){
                            // -> cộng dồn số lượng :
                            HomeActivity.listFoodOrder.get(i).setSoLuong(HomeActivity.listFoodOrder.get(i).getSoLuong()+soLuong);
                            Toast.makeText(FoodDetailActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            return;
                        }
                    }
                }
                HomeActivity.listFoodOrder.add(new FoodOrder(food,soLuong));
                Toast.makeText(FoodDetailActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
    private void initView(){
        imgSanPham = findViewById(R.id.imgSanPham);
        tvTen = findViewById(R.id.tvTen);
        tvGia = findViewById(R.id.tvGiaDetail);
        tvMoTa = findViewById(R.id.tvMoTa);
        btnThemGioHang = findViewById(R.id.btnThemVaoGioHang);
        tvCong = findViewById(R.id.tvCong);
        tvTru = findViewById(R.id.tvTru);
        tvSoLuong = findViewById(R.id.tvSoLuongDetail);

    }
}