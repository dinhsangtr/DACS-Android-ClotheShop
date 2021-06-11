package com.doan.shop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doan.shop.R;
import com.doan.shop.adapter.SanPhamAdapter;
import com.doan.shop.model.SanPham;
import com.doan.shop.util.CheckConnection;
import com.doan.shop.util.Server;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SanPhamActivity extends AppCompatActivity {
/*
    private static final String ARG_PARAM1 = "p1";
    private static final String ARG_PARAM2 = "p2";

    private String mParam1;
    private String mParam2;
    private int idSP = 0;
    private String tenLoai;
    private int idLoai = 1; //idLoai

    private ArrayList<SanPham> listSanPham;
    private SanPhamAdapter sanPhamAdapter;
    private Toolbar toolbar;
    private TextView txtToolBarSP;
    private RecyclerView recyclerviewSP;
    private View footerView;
    private boolean isLoading = false;


    public SanPhamActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        Mapping();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            GetIDLoaiSP();
            ActionToolbar();
            GetDataSanPham(idLoai);
            LoadMore();
            //toolbar
            toolbar.setTitle(null);
            txtToolBarSP.setText(tenLoai);


            //
            listSanPham = new ArrayList<>();
            sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), listSanPham);

            recyclerviewSP.setHasFixedSize(true);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            recyclerviewSP.setLayoutManager(mLayoutManager);

            recyclerviewSP.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
            recyclerviewSP.setItemAnimator(new DefaultItemAnimator());
            recyclerviewSP.setAdapter(sanPhamAdapter);
            //
            recyclerviewSP.setNestedScrollingEnabled(false);

        } else {
            CheckConnection.Show_Toast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối!");
        }


    }

    //Anhs xaj
    public void Mapping() {
        recyclerviewSP = (RecyclerView) findViewById(R.id.recyclerviewSP_Act);
        toolbar = (Toolbar) findViewById(R.id.toolbarSP_Act);
        txtToolBarSP = (TextView) findViewById(R.id.txtToolBarSP_Act);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
    }

    //Lay ID LoaiSP tu Category_Fragment
    private void GetIDLoaiSP() {
        idLoai = getIntent().getIntExtra("idloaiSP", -1);
        tenLoai = getIntent().getStringExtra("tenLoaiSP");
        Log.d("giatriLoaiSP", idSP + "" + tenLoai);
    }

    //Lay SP theo IDLoaiSp
    private void GetDataSanPham(int idLOAI) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.URL_SPTheoLoai + String.valueOf(idLOAI);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
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
                            hinh_anh_sp_ms = "";//jsonObject.getString("hinh_anh_sp_ms");

                            listSanPham.add(new SanPham(id_san_pham,
                                    ten_san_pham, id_danh_muc,
                                    id_thuong_hieu, mo_ta,
                                    kich_thuoc, gia_ban,
                                    gia_khuyen_mai,
                                    Server.URL_ImgSanPham + hinh_anh_sp,
                                    id_mau_sac, ten_mau, code,
                                    hinh_anh_sp_ms));
                            sanPhamAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idSP", String.valueOf(idSP));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void LoadMore(){
        recyclerviewSP.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    //Toolbar
    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //Decor
    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private final int spanCount;
        private final int spacing;
        private final boolean includeEdge;

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
    /*
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    */
}