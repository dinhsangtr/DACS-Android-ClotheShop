package com.doan.shop.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.doan.shop.R;
import com.doan.shop.adapter.sanphamAdapter;
import com.doan.shop.model.SanPham;
import com.doan.shop.util.CheckConnection;
import com.doan.shop.util.MyApplication;
import com.doan.shop.util.Server;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private ListView listView;

    //sp noi bat
    private ArrayList<SanPham> mangSP;
    private sanphamAdapter sanphamAdapter;//khuon mau
    //sp moi
    private ArrayList<SanPham> mangSP1;
    private sanphamAdapter sanphamAdapter1;//khuon mau


    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView1 = view.findViewById(R.id.recyclerview1);
        viewFlipper = view.findViewById(R.id.viewflipper);

        getDuLieuSP_NB();
        getDuLieuSP_M();
        //kiem tra ket noi
        if (CheckConnection.haveNetworkConnection(getActivity().getApplicationContext())) {
            //Slider
            int[] arrayHinh = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
            for (int i = 0; i < arrayHinh.length; i++) {
                ImageView imageView = new ImageView(getContext());
                //Picasso.with(getContext()).load(arrayHinh[i]).into(imageView);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(arrayHinh[i]);
                viewFlipper.addView(imageView);
            }
            //set thoi gian chay
            viewFlipper.setFlipInterval(5000);
            viewFlipper.startFlipping();
            Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
            Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
            viewFlipper.setInAnimation(animation_slide_in);
            viewFlipper.setOutAnimation(animation_slide_out);
            //end//Slider

            //view sp noi bat
            //mapping
            mangSP = new ArrayList<>();
            sanphamAdapter = new sanphamAdapter(getActivity().getApplicationContext(), mangSP);
            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(sanphamAdapter);
            //
            recyclerView.setNestedScrollingEnabled(false);

            //view sp noi bat
            //mapping
            mangSP1 = new ArrayList<>();
            sanphamAdapter1 = new sanphamAdapter(getActivity().getApplicationContext(), mangSP1);
            recyclerView1.setHasFixedSize(true);

            RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(getActivity(), 2);
            recyclerView1.setLayoutManager(mLayoutManager1);
            recyclerView1.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
            recyclerView1.setItemAnimator(new DefaultItemAnimator());
            recyclerView1.setAdapter(sanphamAdapter1);
            //
            recyclerView1.setNestedScrollingEnabled(false);
        } else {
            CheckConnection.Show_Toast(getActivity().getApplicationContext(), "Vui lòng kiểm tra lại kết nối!");
        }


        //


        return view;
    }

    //Get San Pham Noi Bat
    private void getDuLieuSP_NB() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URL_SP_NoiBat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id_san_pham = 0;
                    String ten_san_pham = "";
                    int id_danh_muc = 0;
                    int id_thuong_hieu = 0;
                    String mo_ta = "";
                    String kich_thuoc = "";
                    Double gia_ban = 0.0;
                    Double gia_khuyen_mai = 0.0;
                    String hinh_anh_sp = "";
                    int id_mau_sac = 0;
                    String ten_mau = "";
                    String code = "";
                    String hinh_anh_sp_ms = "";

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id_san_pham = jsonObject.getInt("id_san_pham");
                            ten_san_pham = jsonObject.getString("ten_san_pham");
                            id_danh_muc = jsonObject.getInt("id_danh_muc");
                            id_thuong_hieu = jsonObject.getInt("id_thuong_hieu");
                            mo_ta = jsonObject.getString("mo_ta");
                            kich_thuoc = jsonObject.getString("kich_thuoc");
                            gia_ban = jsonObject.getDouble("gia_ban");
                            gia_khuyen_mai = jsonObject.getDouble("gia_khuyen_mai");
                            hinh_anh_sp = jsonObject.getString("hinh_anh_sp");
                            id_mau_sac = jsonObject.getInt("id_mau_sac");
                            ten_mau = jsonObject.getString("ten_mau");
                            code = jsonObject.getString("code");
                            hinh_anh_sp_ms = jsonObject.getString("hinh_anh_sp_ms");

                            mangSP.add(new SanPham(id_san_pham,
                                    ten_san_pham, id_danh_muc,
                                    id_thuong_hieu, mo_ta,
                                    kich_thuoc, gia_ban,
                                    gia_khuyen_mai,
                                    Server.URL_ImgSanPham + hinh_anh_sp,
                                    id_mau_sac, ten_mau, code,
                                    hinh_anh_sp_ms));
                            sanphamAdapter.notifyDataSetChanged();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    //Get San Pham Moi
    private void getDuLieuSP_M() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URL_SP_Moi, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id_san_pham = 0;
                    String ten_san_pham = "";
                    int id_danh_muc = 0;
                    int id_thuong_hieu = 0;
                    String mo_ta = "";
                    String kich_thuoc = "";
                    Double gia_ban = 0.0;
                    Double gia_khuyen_mai = 0.0;
                    String hinh_anh_sp = "";
                    int id_mau_sac = 0;
                    String ten_mau = "";
                    String code = "";
                    String hinh_anh_sp_ms = "";

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id_san_pham = jsonObject.getInt("id_san_pham");
                            ten_san_pham = jsonObject.getString("ten_san_pham");
                            id_danh_muc = jsonObject.getInt("id_danh_muc");
                            id_thuong_hieu = jsonObject.getInt("id_thuong_hieu");
                            mo_ta = jsonObject.getString("mo_ta");
                            kich_thuoc = jsonObject.getString("kich_thuoc");
                            gia_ban = jsonObject.getDouble("gia_ban");
                            gia_khuyen_mai = jsonObject.getDouble("gia_khuyen_mai");
                            hinh_anh_sp = jsonObject.getString("hinh_anh_sp");
                            id_mau_sac = jsonObject.getInt("id_mau_sac");
                            ten_mau = jsonObject.getString("ten_mau");
                            code = jsonObject.getString("code");
                            hinh_anh_sp_ms = jsonObject.getString("hinh_anh_sp_ms");

                            mangSP1.add(new SanPham(id_san_pham,
                                    ten_san_pham, id_danh_muc,
                                    id_thuong_hieu, mo_ta,
                                    kich_thuoc, gia_ban,
                                    gia_khuyen_mai,
                                    Server.URL_ImgSanPham + hinh_anh_sp,
                                    id_mau_sac, ten_mau, code,
                                    hinh_anh_sp_ms));
                            sanphamAdapter1.notifyDataSetChanged();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}