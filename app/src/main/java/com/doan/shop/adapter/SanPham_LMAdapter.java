package com.doan.shop.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.shop.Interface.ILoadMore;
import com.doan.shop.R;
import com.doan.shop.model.DanhMuc;
import com.doan.shop.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


//xử lý việc tải thêm dữ liệu
// bằng cách set sự kiện onScrollListener cho RecyclerView ở hàm khởi tạo của Adapter
public class SanPham_LMAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0, VIEW_TYPE_LOADING = 1;
    int visibleThreshold = 2;
    int lastVisibleItem, totalItemCount, visibleItemCount;
    ILoadMore loadMore;
    boolean isLoading;
    Activity activity;

    Context context;
    ArrayList<SanPham> arrayListSanPham;
    private SanPham_LMAdapter.ItemClickListener clickListener;


    public SanPham_LMAdapter(RecyclerView recyclerView, Activity activity, ArrayList<SanPham> sanphams, SanPham_LMAdapter.ItemClickListener clickListener) {
        this.activity = activity;
        this.arrayListSanPham = sanphams;
        this.clickListener = clickListener;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                visibleItemCount = linearLayoutManager.getChildCount();
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                //if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                if (!isLoading && totalItemCount <= (firstVisibleItem + visibleItemCount)) {
                    if (loadMore != null)
                        Log.v("...",
                                "lastVisibleItem: " + lastVisibleItem
                                        + ", totalItemCount: " + totalItemCount
                                        + ", visibleThreshold:" + visibleThreshold
                                        + ", visibleItemCount:" + visibleItemCount
                                        + ", firstVisibleItem" + firstVisibleItem
                                        + ", dx:" + dx
                                        + ", dy:" + dy);

                    isLoading = true;
                    loadMore.onLoadMore();
                }
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, price, priceKM; //ten, gia
        public ImageView thumbnail;//hinh san pham
        public CardView card_viewSP1;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title1);
            price = (TextView) view.findViewById(R.id.price1);
            priceKM = (TextView) view.findViewById(R.id.priceKM1);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail1);
            card_viewSP1 = (CardView) view.findViewById(R.id.card_viewSP1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return arrayListSanPham.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_item_sp, parent, false);
            return new ViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progressbar, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            SanPham sanPham = arrayListSanPham.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.title.setText(arrayListSanPham.get(position).getTen_san_pham());

            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            if (arrayListSanPham.get(position).getGia_ban()
                    == arrayListSanPham.get(position).getGia_khuyen_mai()) {
                viewHolder.price.setText(decimalFormat.format(arrayListSanPham.get(position).getGia_ban()) + " đ");
            } else {
                viewHolder.price.setText(decimalFormat.format(arrayListSanPham.get(position).getGia_ban()) + " đ");
                viewHolder.price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.priceKM.setText(decimalFormat.format(arrayListSanPham.get(position).getGia_khuyen_mai()) + " đ");
            }

            Picasso.get().load(arrayListSanPham.get(position).getHinh_anh_sp())
                    .placeholder(R.drawable.load)
                    .error(R.drawable.error)
                    .into(viewHolder.thumbnail);

            viewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(sanPham);
                }
            });
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return arrayListSanPham.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public interface ItemClickListener {
        public void onItemClick(SanPham sanPham);
    }

}
