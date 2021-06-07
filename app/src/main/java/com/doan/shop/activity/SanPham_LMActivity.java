package com.doan.shop.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doan.shop.Interface.ILoadMore;
import com.doan.shop.R;
import com.doan.shop.adapter.SanPham_LMAdapter;
import com.doan.shop.model.SanPham;
import com.doan.shop.util.CheckConnection;
import com.doan.shop.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SanPham_LMActivity extends AppCompatActivity implements SanPham_LMAdapter.ItemClickListener {

    private static final String ARG_PARAM1 = "p1";
    private static final String ARG_PARAM2 = "p2";

    private String mParam1;
    private String mParam2;
    private int idSP = 0;
    private String tenLoai;
    private int idLoai = 1; //idLoai

    private ArrayList<SanPham> listSanPham;
    private SanPham_LMAdapter sanPhamAdapter;
    private Toolbar toolbar;
    private TextView txtToolBarSP;
    private RecyclerView recyclerviewSP;
    private boolean isLoading = false;


    public SanPham_LMActivity() {
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

            //toolbar
            toolbar.setTitle(null);
            txtToolBarSP.setText(tenLoai);


            //
            listSanPham = new ArrayList<>();
            //sanPhamAdapter = new SanPham_LMAdapter(getApplicationContext(), listSanPham);

            recyclerviewSP.setHasFixedSize(true);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            LinearLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

            recyclerviewSP.setLayoutManager(mLayoutManager);
            sanPhamAdapter = new SanPham_LMAdapter(recyclerviewSP, this, listSanPham, this);

            recyclerviewSP.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
            recyclerviewSP.setItemAnimator(new DefaultItemAnimator());
            recyclerviewSP.setAdapter(sanPhamAdapter);
            recyclerviewSP.setNestedScrollingEnabled(false);

            //loadMore
            sanPhamAdapter.setLoadMore(new ILoadMore() {
                @Override
                public void onLoadMore() {
                    //if (listSanPham.size() <= 50)
                    // Bạn có thể change max giá trị load ở đây ,
                    // load tới số lượng như này thì có kéo nữa cũng không load nữa ,
                    // bỏ điều kiện này đi thì nó cứ thế mà load
                    //{

                    // Add 1 null
                    // Quay lại cái Adapter của chúng ta mà thấy , nếu gặp item null thì nó sẽ coi đó là Loading View
                    recyclerviewSP.post(new Runnable() {
                        @Override
                        public void run() {
                            listSanPham.add(null);
                            sanPhamAdapter.notifyItemInserted(listSanPham.size() - 1);// Báo với adapter là có sự thay đổi
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listSanPham.remove(listSanPham.size() - 1);
                            sanPhamAdapter.notifyItemRemoved(listSanPham.size());

                            //Random more data
                            int visible = listSanPham.size() - 1;
                            int end = visible + 6;
                            GetDataSanPham(idLoai, visible, end);
                            //sanPhamAdapter.notifyDataSetChanged();
                            sanPhamAdapter.setLoaded();
                        }
                    }, 1500); // Time to load
                    // } else {
                    //     Toast.makeText(SanPham_LMActivity.this, "Load data completed !", Toast.LENGTH_SHORT).show();
                    // }
                }
            });


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
                        for (int i = 0; i < 6; i++) {
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
                        int lengthJ = jsonArray.length();
                        Log.v("...", "Response.length1=" + lengthJ);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) /*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idSP", String.valueOf(idSP));
                return param;
            }
        }*/;
        requestQueue.add(stringRequest);
    }


    //loadMore
    private void GetDataSanPham(int idLOAI, int visible, int end) { //visible:tong item hien co; end: gia tri tong truoc do cong them
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
                        int lengthJ = jsonArray.length();
                        Log.v("...", "Response.length3=" + lengthJ);
                        int x = end;
                        if (x > lengthJ) {
                            x = lengthJ - 1;
                        }
                        Log.v("...", "x" + x);
                        for (int i = visible; i < x; i++) {
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
                            hinh_anh_sp_ms = jsonObject.getString("hinh_anh_sp_ms");

                            listSanPham.add(new SanPham(id_san_pham,
                                    ten_san_pham, id_danh_muc,
                                    id_thuong_hieu, mo_ta,
                                    kich_thuoc, gia_ban,
                                    gia_khuyen_mai,
                                    Server.URL_ImgSanPham + hinh_anh_sp,
                                    id_mau_sac, ten_mau, code,
                                    hinh_anh_sp_ms));

                        }
                        sanPhamAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })/* {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idSP", String.valueOf(idSP));
                return param;
            }
        }*/;

        requestQueue.add(stringRequest);
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

    @Override
    public void onItemClick(SanPham sanPham) {

        //Toast.makeText(getApplicationContext(), "text", Toast.LENGTH_SHORT).show();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), ChiTietSPActivity.class);
                intent.putExtra("thongtinsanpham", sanPham);
                intent.putExtra("idSP", sanPham.getId_san_pham());
                startActivity(intent);
            }
        });
        thread.start();


        long sleep = (long) (Math.random() * 1000L);
        try {
            Thread.sleep(sleep);
        } catch (Exception exc) {
        }

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
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}