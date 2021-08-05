package com.example.managefood.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managefood.CartActivity;
import com.example.managefood.HomeActivity;
import com.example.managefood.Interface.OnItemsRecycleViewClicked;
import com.example.managefood.Interface.OnWidgetRecycleviewClicked;
import com.example.managefood.Model.FoodOrder;
import com.example.managefood.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
    List<FoodOrder>  foodOrders;
    DecimalFormat formatter;
    int thanhTien;
    public CartAdapter(List<FoodOrder> foodOrders) {
        this.foodOrders = foodOrders;
    }
    private OnWidgetRecycleviewClicked onWidgetRecycleviewClicked;


    public void setOnWidgetRecycleviewClicked(OnWidgetRecycleviewClicked onWidgetRecycleviewClicked) {
        this.onWidgetRecycleviewClicked = onWidgetRecycleviewClicked;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gio_hang,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        FoodOrder foodOrder = foodOrders.get(position);
        holder.tvSoLuong.setText(""+foodOrder.getSoLuong());
        holder.tvTen.setText(foodOrder.getFood().getName());
        Picasso.with(holder.imgFood.getContext()).load(foodOrder.getFood().getImage()).into(holder.imgFood);

        formatter = new DecimalFormat("###,###,###");
        holder.tvGia.setText(formatter.format(foodOrder.getThanhTien())+"đ");
        holder.tvCong.setOnClickListener(v->{
            foodOrder.setSoLuong(foodOrder.getSoLuong()+1);
            notifyDataSetChanged();
            if(HomeActivity.listFoodOrder.size()>0) {
                thanhTien = 0;
                for (int i = 0; i < HomeActivity.listFoodOrder.size(); i++) {
                    thanhTien += (int) HomeActivity.listFoodOrder.get(i).getThanhTien();
                }
            }else{
                thanhTien = 0;
            }
            onWidgetRecycleviewClicked.onClick(thanhTien);
        });
        holder.tvTru.setOnClickListener(v->{
            if(foodOrder.getSoLuong()>0) {
                foodOrder.setSoLuong(foodOrder.getSoLuong()-1);
                notifyDataSetChanged();
            }
            if(foodOrder.getSoLuong() <= 0){
                HomeActivity.listFoodOrder.remove(foodOrder);
                notifyItemRemoved(position);
            }
            if(HomeActivity.listFoodOrder.size()>0) {
                thanhTien = 0;
                for (int i = 0; i < HomeActivity.listFoodOrder.size(); i++) {
                    thanhTien += (int) HomeActivity.listFoodOrder.get(i).getThanhTien();
                }
            }else{
                thanhTien = 0;
            }
            onWidgetRecycleviewClicked.onClick(thanhTien);

        });

    }

    @Override
    public int getItemCount() {
        return foodOrders.size();
    }
    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvTen,tvGia,tvSoLuong;
        TextView tvCong,tvTru;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFoodCart);
            tvGia = itemView.findViewById(R.id.tvGiaCart);
            tvTen = itemView.findViewById(R.id.tvTenCart);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuongCart);
            tvCong = itemView.findViewById(R.id.tvCongCart);
            tvTru = itemView.findViewById(R.id.tvTruCart);
        }
    }
}
