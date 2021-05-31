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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.shop.R;
import com.doan.shop.fragment.Category_Fragment;
import com.doan.shop.fragment.Category_Pr_Fragment;
import com.doan.shop.model.DanhMucCha;
import com.doan.shop.util.CheckConnection;
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
       // private LinearLayout Rlayout;
        private TextView txtTenDanhMucCha; //ten
        private ImageView imageViewDMC;//anh


        public ViewHolder(View view) {
            super(view);
           // Rlayout = (LinearLayout) view.findViewById(R.id.Rlayout);
            imageViewDMC = (ImageView) view.findViewById(R.id.imageViewDMC);
            txtTenDanhMucCha = (TextView) view.findViewById(R.id.txtTenDanhMucCha);
        }


    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.row_parent_cate, null);
        //v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_parent_cate, null);
        ViewHolder itemHolder = new ViewHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhMucCha danhMucCha = listDMucCha.get(position);

        holder.txtTenDanhMucCha.setText(danhMucCha.getTen_danh_muc_cha());
        Picasso.with(context).load(danhMucCha.getHinh_anh())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(holder.imageViewDMC);

        holder.imageViewDMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(listDMucCha.get(position));
               // Toast.makeText(context,"Test click danh muc : "+holder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
                /*
                if (CheckConnection.haveNetworkConnection(context.getApplicationContext())) {
                    Toast.makeText(context,"Test click danh muc : "+holder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
                   /* AppCompatActivity activity =(AppCompatActivity)v.getContext().getApplicationContext().getApplicationContext();
                    Category_Fragment category_fragment = new Category_Fragment();

                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.layout_DanhMucCha, category_fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    /*
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace()
                            .addToBackStack(null)
                            .commit();
                } else {
                    CheckConnection.Show_Toast(context.getApplicationContext(), "Vui lòng kiểm tra lại kết nối!");
                }
                */
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
