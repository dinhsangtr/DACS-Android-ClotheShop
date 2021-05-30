package com.doan.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.doan.shop.R;
import com.doan.shop.model.DanhMucCha;

import java.util.ArrayList;
import java.util.List;

public class DanhMucChaAdapter extends BaseAdapter {

    ArrayList<DanhMucCha> listDMucCha;
    Context context;

    public DanhMucChaAdapter(ArrayList<DanhMucCha> listDMucCha, Context context) {
        this.listDMucCha = listDMucCha;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listDMucCha.size();
    }

    @Override
    public Object getItem(int position) {
        return listDMucCha.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView txtTenDanhMucCha;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup root;
            convertView = inflater.inflate(R.layout.row_parent_cate,null, false);
            viewHolder.txtTenDanhMucCha = (TextView)convertView.findViewById(R.id.txtTenDanhMucCha);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DanhMucCha dMucCha = (DanhMucCha)getItem(position);
        viewHolder.txtTenDanhMucCha.setText(dMucCha.getTen_danh_muc_cha());

        return null;
    }
}
