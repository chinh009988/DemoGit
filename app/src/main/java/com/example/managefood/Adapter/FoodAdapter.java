package com.example.managefood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import com.example.managefood.Interface.OnItemsRecycleViewClicked;
import com.example.managefood.Model.Food;
import com.example.managefood.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    List<Food> foodList;

    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }
    private OnItemsRecycleViewClicked onItemsRecycleViewClicked;

    public void setOnItemsRecycleViewClicked(OnItemsRecycleViewClicked onItemsRecycleViewClicked) {
        this.onItemsRecycleViewClicked = onItemsRecycleViewClicked;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  FoodViewHolder holder, int position) {
            Picasso.with(holder.imgFood.getContext()).load(foodList.get(position).getImage()).into(holder.imgFood);
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            holder.tvTen.setText(foodList.get(position).getName());
            holder.tvGia.setText(formatter.format(foodList.get(position).getPrice())+ " VNĐ");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemsRecycleViewClicked.onClick(foodList.get(position));
                }
            });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
    protected class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvGia,tvTen;
        public FoodViewHolder(@NonNull  View itemView) {
            super(itemView);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvTen = itemView.findViewById(R.id.tvTen);
            imgFood = itemView.findViewById(R.id.imgFood);
        }
    }
}
