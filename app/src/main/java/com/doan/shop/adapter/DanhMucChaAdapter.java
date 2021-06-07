package com.doan.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.shop.R;
import com.doan.shop.model.DanhMucCha;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhMucChaAdapter extends RecyclerView.Adapter<DanhMucChaAdapter.ViewHolder> {
    Context context;
    ArrayList<DanhMucCha> listDMucCha;
    private  ItemClickListener clickListener;



    public DanhMucChaAdapter(ArrayList<DanhMucCha> listDMucCha, Context context,ItemClickListener clickListener) {
        this.listDMucCha = listDMucCha;
        this.context = context;
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTenDanhMucCha; //ten
        private ImageView imageViewDMC;//anh


        public ViewHolder(View view) {
            super(view);
            imageViewDMC = (ImageView) view.findViewById(R.id.imageViewDMC);
            txtTenDanhMucCha = (TextView) view.findViewById(R.id.txtTenDanhMucCha);
        }


    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.row_item_danhmuccha, null);
        //v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_parent_cate, null);
        ViewHolder itemHolder = new ViewHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhMucCha danhMucCha = listDMucCha.get(position);

        holder.txtTenDanhMucCha.setText(danhMucCha.getTen_danh_muc_cha());
        Picasso.get().load(danhMucCha.getHinh_anh())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(holder.imageViewDMC);

        holder.imageViewDMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickListener.onItemClick(listDMucCha.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDMucCha.size();
    }

    public interface ItemClickListener{
        public void onItemClick(DanhMucCha danhMucCha);
    }

}
