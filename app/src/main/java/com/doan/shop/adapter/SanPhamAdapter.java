package com.doan.shop.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.shop.R;
import com.doan.shop.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    Context context;
    ArrayList<SanPham> arrayListSanPham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arrayListSanPham) {
        this.context = context;
        this.arrayListSanPham = arrayListSanPham;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, priceKM; //ten, gia
        ImageView thumbnail;//hinh san pham

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            price = (TextView) view.findViewById(R.id.price);
            priceKM = (TextView) view.findViewById(R.id.priceKM);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item_row, null);
        ViewHolder itemHolder = new ViewHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = arrayListSanPham.get(position);
        holder.title.setText(sanPham.getTen_san_pham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        if(sanPham.getGia_ban() == sanPham.getGia_khuyen_mai()){
            holder.price.setText(decimalFormat.format(sanPham.getGia_ban()) + " đ");
        }else{
            holder.price.setText(decimalFormat.format(sanPham.getGia_ban()) + " đ");
            holder.price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.priceKM.setText(decimalFormat.format(sanPham.getGia_khuyen_mai()) + " đ");
        }
        Picasso.with(context).load(sanPham.getHinh_anh_sp()) //i replace 'with(context)' by 'get()' and problem is gone. Hope it's OK.
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return arrayListSanPham.size();
    }

}
