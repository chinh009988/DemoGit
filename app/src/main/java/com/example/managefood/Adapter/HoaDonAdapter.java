package com.example.managefood.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.managefood.Interface.OnItemHoaDonClicked;
import com.example.managefood.Model.HoaDon;
import com.example.managefood.R;

import java.text.DecimalFormat;
import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder> {
    List<HoaDon> hoaDons;

    public HoaDonAdapter(List<HoaDon> hoaDons) {
        this.hoaDons = hoaDons;
    }
    private OnItemHoaDonClicked onItemHoaDonClicked;

    public void setOnItemHoaDonClicked(OnItemHoaDonClicked onItemHoaDonClicked) {
        this.onItemHoaDonClicked = onItemHoaDonClicked;
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoa_don,parent,false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  HoaDonViewHolder holder, int position) {
        holder.tvDate.setText(hoaDons.get(position).getThoiGian());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        holder.tvGia.setText(formatter.format(hoaDons.get(position).getTongTien())+" VNĐ");
        holder.tvMa.setText(hoaDons.get(position).getMaHoaDon().substring(1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemHoaDonClicked.onClick(hoaDons.get(position).getMaHoaDon());
            }
        });
    }

    @Override
    public int getItemCount() {
        return hoaDons.size();
    }
    public class HoaDonViewHolder extends RecyclerView.ViewHolder {
        TextView tvMa,tvGia,tvDate;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.tvMaHD);
            tvGia = itemView.findViewById(R.id.tvTongTienHD);
            tvDate = itemView.findViewById(R.id.tvThoiGianHD);
        }
    }
}
