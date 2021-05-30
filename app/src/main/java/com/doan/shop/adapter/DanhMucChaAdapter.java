package com.doan.shop.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.shop.R;
import com.doan.shop.model.DanhMucCha;
import com.doan.shop.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DanhMucChaAdapter extends RecyclerView.Adapter<DanhMucChaAdapter.ViewHolder> {
    Context context;
    ArrayList<DanhMucCha> listDMucCha;


    public DanhMucChaAdapter(ArrayList<DanhMucCha> listDMucCha, Context context) {
        this.listDMucCha = listDMucCha;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenDanhMucCha; //ten


        public ViewHolder(View view) {
            super(view);
            txtTenDanhMucCha = (TextView) view.findViewById(R.id.txtTenDanhMucCha);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_parent_cate, null);
       ViewHolder itemHolder = new ViewHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhMucCha danhMucCha = listDMucCha.get(position);
        holder.txtTenDanhMucCha.setText(danhMucCha.getTen_danh_muc_cha());
    }


    @Override
    public int getItemCount() {
        return listDMucCha.size();
    }
}
