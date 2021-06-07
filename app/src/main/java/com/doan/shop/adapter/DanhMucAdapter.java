package com.doan.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.shop.R;
import com.doan.shop.model.DanhMuc;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder>{
    private Context context;
    private ArrayList<DanhMuc> listDMuc;
    private DanhMucAdapter.ItemClickListener clickListener;


    public DanhMucAdapter(ArrayList<DanhMuc> listDMuc, Context context, DanhMucAdapter.ItemClickListener clickListener) {
        this.listDMuc = listDMuc;
        this.context = context;
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTenDanhMuc; //ten
        private ImageView imageDanhMuc;//anh
        private LinearLayout layoutDanhMucCon;

        public ViewHolder(View view) {
            super(view);
            imageDanhMuc = (ImageView) view.findViewById(R.id.imageDanhMuc);
            txtTenDanhMuc = (TextView) view.findViewById(R.id.txtTenDanhMuc);
            layoutDanhMucCon = (LinearLayout) view.findViewById(R.id.layoutDanhMucCon);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.row_item_danhmuccon, null);
        ViewHolder itemHolder = new ViewHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DanhMucAdapter.ViewHolder holder, int position) {
        DanhMuc danhMuc = listDMuc.get(position);

        holder.txtTenDanhMuc.setText(danhMuc.getTen_danh_muc());
        //with(context)
        Picasso.get().load(danhMuc.getHinh_anh())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(holder.imageDanhMuc);

        holder.layoutDanhMucCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(listDMuc.get(position));
                //Toast.makeText(context, "11", Toast.LENGTH_SHORT).show();
            }
        });
/*
        holder.imageDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "11", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listDMuc.size();
    }

    public interface ItemClickListener {
        public void onItemClick(DanhMuc danhMuc);
    }
}
