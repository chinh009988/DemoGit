package com.example.managefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddFoodActivity extends AppCompatActivity {
    Button btnAddFood;
    ImageView imgFood;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        initView();
        storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        imgFood.setDrawingCacheEnabled(true);
        imgFood.buildDrawingCache();

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
        imgFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });


        btnAddFood.setOnClickListener(new View.OnClickListener() {
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
                imgFood.setImageBitmap(bitmap);
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
            Toast.makeText(AddFoodActivity.this, "Tên không đượ trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (gia.isEmpty()) {
            Toast.makeText(AddFoodActivity.this, "Giá không được trống", Toast.LENGTH_SHORT).show();
            return;
        }
        String photoName = ten+System.currentTimeMillis();
        Bitmap bitmap = ((BitmapDrawable) imgFood.getDrawable()).getBitmap();
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
                        databaseReference.child("Food").push().setValue(food);
                        Toast.makeText(getApplicationContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddFoodActivity.this,ListFoodActivity.class));
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Thêm thất bại",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initView() {
        btnAddFood = findViewById(R.id.btnAddFood);
        imgFood = findViewById(R.id.imgAvatarFood);
        edGia = findViewById(R.id.edGiaAddFood);
        edTen = findViewById(R.id.addFoodNameTxt);
        edMoTa = findViewById(R.id.moTaTxt);
        spnLoai = findViewById(R.id.spnLoaiFood);
        spnTrangThai = findViewById(R.id.spnTrangThai);
    }
}