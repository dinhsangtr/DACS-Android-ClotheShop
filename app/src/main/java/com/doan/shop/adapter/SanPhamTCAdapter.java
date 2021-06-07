package com.doan.shop.adapter;

import android.content.Context;
import android.graphics.Paint;
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
import com.doan.shop.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamTCAdapter extends RecyclerView.Adapter<SanPhamTCAdapter.ViewHolder> {
    Context context;
    ArrayList<SanPham> arrayListSanPham;
    private SanPhamTCAdapter.ItemClickListener clickListener;

    public SanPhamTCAdapter(Context context, ArrayList<SanPham> arrayListSanPham, SanPhamTCAdapter.ItemClickListener clickListener) {
        this.context = context;
        this.arrayListSanPham = arrayListSanPham;
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, price, priceKM; //ten, gia
        private ImageView thumbnail;//hinh san pham

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_sp_tc, null);
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
        Picasso.get().load(sanPham.getHinh_anh_sp())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(sanPham);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListSanPham.size();
    }

    public interface ItemClickListener {
        public void onItemClick(SanPham sanPham);
    }
}
