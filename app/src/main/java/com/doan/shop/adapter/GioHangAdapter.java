package com.doan.shop.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.doan.shop.R;
import com.doan.shop.activity.MainActivity;
import com.doan.shop.fragment.CartFragment;
import com.doan.shop.model.GioHang;
import com.doan.shop.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> listGioHang;
    private GioHangAdapter.ItemClickListener clickListener;

    public GioHangAdapter(Context context, ArrayList<GioHang> listGioHang, GioHangAdapter.ItemClickListener clickListener) {
        this.context = context;
        this.listGioHang = listGioHang;
        this.clickListener = clickListener;
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
        TextView txtTenSPCart, tvCode, txtSizeCart, txtGiaCart, txtGiaKMCart, txtTongGiaCart;
        Button btnTru, btnValue, btnCong;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
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
            viewHolder.txtTongGiaCart = (TextView) convertView.findViewById(R.id.txtTongGiaCart);
            viewHolder.btnTru = (Button) convertView.findViewById(R.id.btnTru);
            viewHolder.btnValue = (Button) convertView.findViewById(R.id.btnValue);
            viewHolder.btnCong = (Button) convertView.findViewById(R.id.btnCong);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHang gioHang = (GioHang) getItem(position);

        Picasso.get().load(gioHang.getSanpham().getHinh_anh_sp())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(viewHolder.imgSPCart);

        viewHolder.txtTenSPCart.setText(gioHang.getSanpham().getTen_san_pham());

        try {
            viewHolder.tvCode.setBackgroundColor(Color.parseColor(gioHang.getSanpham().code));
        } catch (Exception ex) {
            String error = ex.getStackTrace().toString();
            Log.d("Color error: ", error);
        }


        viewHolder.txtSizeCart.setText(gioHang.getSanpham().getKich_thuoc());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        if (gioHang.getSanpham().getGia_ban() == gioHang.getSanpham().getGia_khuyen_mai()) {
            viewHolder.txtGiaCart.setText(decimalFormat.format(gioHang.getSanpham().getGia_ban()) + " đ");
        } else {
            viewHolder.txtGiaCart.setText(decimalFormat.format(gioHang.getSanpham().getGia_ban()) + " đ");
            viewHolder.txtGiaCart.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.txtGiaKMCart.setText(decimalFormat.format(gioHang.getSanpham().getGia_khuyen_mai()) + " đ");
        }

        viewHolder.btnValue.setText(gioHang.getSoluong() + "");

        int sl = Integer.parseInt(viewHolder.btnValue.getText().toString());
        if (sl >= 20) {
            viewHolder.btnCong.setVisibility(View.INVISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        } else if (sl <= 1) {
            viewHolder.btnTru.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.btnCong.setVisibility(View.VISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        }

        viewHolder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl_moi = Integer.parseInt(viewHolder.btnValue.getText().toString()) + 1;
                int sl_hientai = MainActivity.listGioHang.get(position).getSoluong();
                double gia_hientai = MainActivity.listGioHang.get(position).getTonggia();
                MainActivity.listGioHang.get(position).setSoluong(sl_moi);
                double gia_moi = (gia_hientai * sl_moi) / sl_hientai;
                MainActivity.listGioHang.get(position).setTonggia(gia_moi);

                CartFragment.EventUtil();
                if(sl_moi > 20){
                    viewHolder.btnCong.setVisibility(View.INVISIBLE);
                    viewHolder.btnTru.setVisibility(View.VISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(sl_moi));
                }else {
                    viewHolder.btnCong.setVisibility(View.VISIBLE);
                    viewHolder.btnTru.setVisibility(View.VISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(sl_moi));
                }
            }
        });

        viewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl_moi = Integer.parseInt(viewHolder.btnValue.getText().toString()) - 1;
                int sl_hientai = MainActivity.listGioHang.get(position).getSoluong();
                double gia_hientai = MainActivity.listGioHang.get(position).getTonggia();
                MainActivity.listGioHang.get(position).setSoluong(sl_moi);
                double gia_moi = (gia_hientai * sl_moi) / sl_hientai;
                MainActivity.listGioHang.get(position).setTonggia(gia_moi);

                CartFragment.EventUtil();
                if(sl_moi <2){
                    viewHolder.btnCong.setVisibility(View.VISIBLE);
                    viewHolder.btnTru.setVisibility(View.INVISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(sl_moi));
                }else {
                    viewHolder.btnCong.setVisibility(View.VISIBLE);
                    viewHolder.btnTru.setVisibility(View.VISIBLE);
                    viewHolder.btnValue.setText(String.valueOf(sl_moi));
                }
            }
        });

        viewHolder.txtTenSPCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(gioHang.getSanpham());
            }
        });

        return convertView;
    }


    public interface ItemClickListener {
        public void onItemClick(SanPham sanPham);
    }
}






















