package com.doan.shop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.doan.shop.R;
import com.doan.shop.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> listGioHang;

    public GioHangAdapter(Context context, ArrayList<GioHang> listGioHang) {
        this.context = context;
        this.listGioHang = listGioHang;
    }

    @Override
    public int getCount() {
        return listGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return listGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        ImageView imgSPCart;
        TextView txtTenSPCart, tvCode, txtSizeCart, txtGiaCart, txtGiaKMCart;
        Button btnTru, btnValue, btnCong;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_item_giohang, null);
            viewHolder.imgSPCart = (ImageView) convertView.findViewById(R.id.imgSPCart);
            viewHolder.txtTenSPCart = (TextView) convertView.findViewById(R.id.txtTenSPCart);
            viewHolder.tvCode = (TextView) convertView.findViewById(R.id.tvCode);
            viewHolder.txtSizeCart = (TextView) convertView.findViewById(R.id.txtSizeCart);
            viewHolder.txtGiaCart = (TextView) convertView.findViewById(R.id.txtGiaCart);
            viewHolder.txtGiaKMCart = (TextView) convertView.findViewById(R.id.txtGiaKMCart);
            viewHolder.btnTru = (Button) convertView.findViewById(R.id.btnTru);
            viewHolder.btnValue = (Button) convertView.findViewById(R.id.btnValue);
            viewHolder.btnCong = (Button) convertView.findViewById(R.id.btnCong);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHang gioHang =(GioHang) getItem(position);

        Picasso.get().load(gioHang.getSanpham().getHinh_anh_sp())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(viewHolder.imgSPCart);

        viewHolder.txtTenSPCart.setText(gioHang.getSanpham().getTen_san_pham());

        viewHolder.tvCode.setBackgroundColor(Color.parseColor(gioHang.getSanpham().code));

        viewHolder.txtSizeCart.setText(gioHang.getSanpham().getKich_thuoc());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        if (gioHang.getSanpham().getGia_ban() == gioHang.getSanpham().getGia_khuyen_mai()) {
            viewHolder.txtGiaCart.setText(decimalFormat.format(gioHang.getSanpham().getGia_ban()) + " đ");
        } else {
            viewHolder.txtGiaCart.setText(decimalFormat.format(gioHang.getSanpham().getGia_ban()) + " đ");
            viewHolder.txtGiaCart.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.txtGiaKMCart.setText(decimalFormat.format(gioHang.getSanpham().getGia_khuyen_mai()) + " đ");
        }



        return convertView;
    }
}






















