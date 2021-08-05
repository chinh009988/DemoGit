package com.example.managefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.managefood.Model.Food;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateFoodActivity extends AppCompatActivity {
    Button btnUpdateFood;
    ImageView imgUpdateFood;
    EditText edTen, edMoTa, edGia;
    Spinner spnLoai, spnTrangThai;
    String loai;
    String trangThai;
    String[] theLoaiList = {"Đồ ăn nhanh", "Món chính", "Đồ ăn Healthy", "Đồ uống"};
    String[] trangThaiList = {"Còn hàng", "Hết hàng"};
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private static final int RESULT_LOAD_IMAGE = 100;
    Food food1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        food1 = (Food) bundle.getSerializable("FoodUpdate");
        edTen.setText(food1.getName());
        edGia.setText(food1.getPrice()+"");
        edMoTa.setText(food1.getDescription()+"");
        if(food1.getType().equalsIgnoreCase("FastFood")){
            spnLoai.setSelection(0);
        }else if(food1.getType().equalsIgnoreCase("Rice")) {
            spnLoai.setSelection(1);
        }else if(food1.getType().equalsIgnoreCase("Healthy")) {
            spnLoai.setSelection(2);
        }else {
            spnLoai.setSelection(3);
        }
        Picasso.with(UpdateFoodActivity.this).load(food1.getImage()).into(imgUpdateFood);


        if(food1.getStatus().equalsIgnoreCase("Còn hàng")){
            spnTrangThai.setSelection(0);
        }else {
            spnTrangThai.setSelection(1);
        }

        storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        imgUpdateFood.setDrawingCacheEnabled(true);
        imgUpdateFood.buildDrawingCache();



        //spnTrangThai
        ArrayAdapter adapter1 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, trangThaiList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTrangThai.setAdapter(adapter1);
        spnTrangThai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                trangThai = (String) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //spnLoai
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, theLoaiList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoai.setAdapter(adapter);

        spnLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    loai = "FastFood";
                    return;
                }
                if (i == 1) {
                    loai = "Rice";
                    return;
                }
                if (i == 2) {
                    loai = "Healthy";
                    return;
                }
                if (i == 3) {
                    loai = "Beverage";
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        imgUpdateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });


        btnUpdateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themSanPham();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMAGE && data != null) {
            Uri uri = data.getData();
            InputStream inputStream;

            try {
                inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgUpdateFood.setImageBitmap(bitmap);
                //
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void themSanPham() {
        String ten = edTen.getText().toString();
        String moTa = edMoTa.getText().toString();
        String gia = edGia.getText().toString();

        if (ten.isEmpty()) {
            Toast.makeText(UpdateFoodActivity.this, "Tên không đượ trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (gia.isEmpty()) {
            Toast.makeText(UpdateFoodActivity.this, "Giá không được trống", Toast.LENGTH_SHORT).show();
            return;
        }
        String photoName = ten+System.currentTimeMillis();
        Bitmap bitmap = ((BitmapDrawable) imgUpdateFood.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        storageReference = storage.getReference().child(photoName+ ".png");
//        storageReference.putBytes(data);
        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Food food = new Food(loai,ten,moTa,Integer.parseInt(gia),trangThai,uri.toString());
                        Log.e("ID1", food1.getID());
                        databaseReference.child("Food").child(food1.getID()).setValue(food);

                        Toast.makeText(getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateFoodActivity.this,ListFoodActivity.class));
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initView() {
        btnUpdateFood = findViewById(R.id.btnUpdateFood);
        imgUpdateFood = findViewById(R.id.imgUpdateAvatarFood);
        edGia = findViewById(R.id.updateGiaFood);
        edTen = findViewById(R.id.updateFoodNameTxt);
        edMoTa = findViewById(R.id.updateMoTaTxt);
        spnLoai = findViewById(R.id.spnUpdateLoaiFood);
        spnTrangThai = findViewById(R.id.spnUpdateTrangThai);
    }
}