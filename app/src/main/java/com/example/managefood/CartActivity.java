package com.example.managefood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managefood.Adapter.CartAdapter;
import com.example.managefood.Interface.OnWidgetRecycleviewClicked;
import com.example.managefood.Model.HoaDon;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CartActivity extends AppCompatActivity {
    RecyclerView rvFoodOrder;
    Button btnThanhToan;
    TextView tvThanhTien;
    CartAdapter cartAdapter;
    ImageView imgBack;
    DecimalFormat formatter;
    int thanhTien = 0;

    DatabaseReference  myReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        myReference = FirebaseDatabase.getInstance().getReference();
        formatter = new DecimalFormat("###,###,###");
        cartAdapter = new CartAdapter(HomeActivity.listFoodOrder);
        rvFoodOrder.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvFoodOrder.setLayoutManager(linearLayoutManager);
        rvFoodOrder.setAdapter(cartAdapter);
        if (HomeActivity.listFoodOrder.size() > 0) {
            thanhTien = 0;
            for (int i = 0; i < HomeActivity.listFoodOrder.size(); i++) {
                thanhTien += (int) HomeActivity.listFoodOrder.get(i).getThanhTien();
            }
            tvThanhTien.setText(formatter.format(thanhTien) +" VN??");
        }else {
            tvThanhTien.setText("0 VN??");
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //B???t s??? ki???n tha ?????i s??? l?????ng s???n ph???m -> thay ?????i t???ng ti???n
        cartAdapter.setOnWidgetRecycleviewClicked(new OnWidgetRecycleviewClicked() {
            @Override
            public void onClick(int tien) {
                thanhTien = tien;
                tvThanhTien.setText(formatter.format(tien)+" VN??");
            }
        });

        //Thanh to??n -> T???o h??a ????n:
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check r???ng
                if(HomeActivity.listFoodOrder.size() <1){
                    Toast.makeText(CartActivity.this,"Gi??? h??ng r???ng",Toast.LENGTH_SHORT).show();
                    return;
                }
                //kh??ng r???ng :
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);

                // Set Title and Message:
                builder.setTitle("Thanh to??n").setMessage("Thanh to??n ????n h??ng n??y?");

                //
                builder.setCancelable(true);

                // Create "Yes" button with OnClickListener.
                builder.setPositiveButton("X??c nh???n", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Date date = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String mDate = df.format(date);
                        HoaDon hoaDon = new HoaDon(MainActivity.user,mDate,HomeActivity.listFoodOrder,thanhTien);
                        myReference.child("DonHang").push().setValue(hoaDon);
                        Toast.makeText(getApplicationContext(),"C???m ??n b???n ???? mua h??ng",Toast.LENGTH_SHORT).show();
                        HomeActivity.listFoodOrder.clear();

                        Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });
                // Create "No" button with OnClickListener.
                builder.setNegativeButton("Mua sau", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Cancel
                        dialog.cancel();
                    }
                });

                // Create AlertDialog:
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
    private void initView(){
        rvFoodOrder = findViewById(R.id.rvFoodOrder);
        btnThanhToan = findViewById(R.id.btnThanhToanCart);
        tvThanhTien = findViewById(R.id.tvThanhTienCart);
        imgBack = findViewById(R.id.img_BackGioHang);
    }
}