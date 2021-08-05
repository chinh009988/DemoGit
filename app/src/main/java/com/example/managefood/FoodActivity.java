package com.example.managefood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.managefood.Adapter.FoodAdapter;
import com.example.managefood.Interface.OnItemsRecycleViewClicked;
import com.example.managefood.Model.Food;
import com.example.managefood.Model.FoodOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {
    ImageView imgBack, imgGioHang;
    EditText edSearch;
    RecyclerView rvBeverage;
    List<Food> foodList;
    FoodAdapter foodAdapter;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String TYPE_FOOD = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initView();

        Intent intent = getIntent();
        TYPE_FOOD = intent.getStringExtra("TYPE_FOOD");
        foodList = new ArrayList<>();
        foodAdapter = new FoodAdapter(foodList);
        rvBeverage.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvBeverage.setLayoutManager(linearLayoutManager);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        // Read from the database
        myRef.child("Food").orderByChild("type").equalTo(TYPE_FOOD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Iterable<DataSnapshot> convert = dataSnapshot.getChildren();
                for (DataSnapshot children : convert) {
                    Food food = children.getValue(Food.class);
                    food.setID(children.getKey() + "");
                    foodList.add(food);
                    Log.e("Name",food.getName());
                }
                rvBeverage.setAdapter(foodAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Value Failed", "Failed to read value.", error.toException());
            }
        });

        imgGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodActivity.this, CartActivity.class));
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        //tìm kiếm :
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                myRef.child("Food").orderByChild("name").startAt(charSequence.toString()+"").endAt(charSequence.toString()+"\uf8ff").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> convert = snapshot.getChildren();
                        foodList.clear();
                        for (DataSnapshot children : convert) {
                            Food food = children.getValue(Food.class);
                            if(food.getType().equalsIgnoreCase(TYPE_FOOD)) {
                                food.setID(children.getRef() + "");
                                foodList.add(food);
                            }
                        }
                        foodAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //on item rv click:
        foodAdapter.setOnItemsRecycleViewClicked(new OnItemsRecycleViewClicked() {
            @Override
            public void onClick(Food food) {
                Intent intent = new Intent(FoodActivity.this, FoodDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("FOOD", food);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    public void initView() {
        imgGioHang = findViewById(R.id.imgGioHangBeverage);
        imgBack = findViewById(R.id.imgBackBeverage);
        edSearch = findViewById(R.id.edSearchBeverage);
        rvBeverage = findViewById(R.id.rvListFoodBeverage);
    }
}