package com.doan.shop.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.shop.R;
import com.doan.shop.model.SanPham;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter /*extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> */{
    /*
    Context context;
    ArrayList<SanPham> arrayListSanPham;
    private SanPhamAdapter.ItemClickListener clickListener;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arrayListSanPham) {
        this.context = context;
        this.arrayListSanPham = arrayListSanPham;
        this.clickListener = clickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, price, priceKM; //ten, gia
        private final ImageView thumbnail;//hinh san pham
        private final CardView card_view11;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title1);
            price = (TextView) view.findViewById(R.id.price1);
            priceKM = (TextView) view.findViewById(R.id.priceKM1);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail1);
            card_view11 = (CardView) view.findViewById(R.id.card_viewSP1);
        }
    }

    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_sp, null);
        SanPhamAdapter.ViewHolder itemHolder = new SanPhamAdapter.ViewHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.ViewHolder holder, int position) {
        SanPham sanPham = arrayListSanPham.get(position);
        holder.title.setText(sanPham.getTen_san_pham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        if (sanPham.getGia_ban() == sanPham.getGia_khuyen_mai()) {
            holder.price.setText(decimalFormat.format(sanPham.getGia_ban()) + " đ");
        } else {
            holder.price.setText(decimalFormat.format(sanPham.getGia_ban()) + " đ");
            holder.price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.priceKM.setText(decimalFormat.format(sanPham.getGia_khuyen_mai()) + " đ");
        }

        Picasso.get().load(sanPham.getHinh_anh_sp())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(holder.thumbnail);

        holder.card_view11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(arrayListSanPham.get(position));
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
*/
}
