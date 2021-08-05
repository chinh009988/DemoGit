package com.example.managefood.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managefood.Model.FoodOrder;
import com.example.managefood.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ChiTietHoaDonAdapter extends RecyclerView.Adapter<ChiTietHoaDonAdapter.CTHDViewHolder>{
    List<FoodOrder> foodOrders;
    DecimalFormat formatter;

    public ChiTietHoaDonAdapter(List<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }

    @NonNull
    @Override
    public CTHDViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cthd,parent,false);
        return new CTHDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CTHDViewHolder holder, int position) {
        FoodOrder foodOrder = foodOrders.get(position);
        holder.tvSoLuong.setText(""+foodOrder.getSoLuong());
        holder.tvTen.setText(foodOrder.getFood().getName());
        Picasso.with(holder.imgFood.getContext()).load(foodOrder.getFood().getImage()).into(holder.imgFood);
        formatter = new DecimalFormat("###,###,###");
        holder.tvGia.setText(formatter.format(foodOrder.getThanhTien())+"đ");
    }

    @Override
    public int getItemCount() {
        return foodOrders.size();
    }
    public class CTHDViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvTen,tvGia,tvSoLuong;
        public CTHDViewHolder(@NonNull  View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFoodHDCT);
            tvGia = itemView.findViewById(R.id.tvGiaHDCT);
            tvTen = itemView.findViewById(R.id.tvTenHDCT);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuongHDCT);
        }
    }
}
