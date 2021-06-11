package com.doan.shop.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doan.shop.R;
import com.doan.shop.adapter.SanPhamLQAdapter;
import com.doan.shop.model.GioHang;
import com.doan.shop.model.SanPham;
import com.doan.shop.util.CheckConnection;
import com.doan.shop.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSPActivity extends AppCompatActivity implements SanPhamLQAdapter.ItemClickListener {
    Toolbar toolbarCTSP_Act;
    ImageView imgSP;
    TextView txtTenSP, txtGia, txtGiaKM, txtMota;
    Button btnAddCart, btnTT;

    //San pham lien quan
    RecyclerView recyclerViewSPLQ;
    private ArrayList<SanPham> listSanPham;
    private SanPhamLQAdapter sanPhamLQAdapter;
    private int idSP = 0;

    //thong tin
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
    //SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
    //

    public ChiTietSPActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);
        Mapping();
        ActionToolbar();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {

            GetThongTinSanPham();
            GetDataSPLQ();
            listSanPham = new ArrayList<>();
            sanPhamLQAdapter = new SanPhamLQAdapter(this, listSanPham, this);
            recyclerViewSPLQ.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
            recyclerViewSPLQ.setLayoutManager(linearLayoutManager);
            recyclerViewSPLQ.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(8), true));
            recyclerViewSPLQ.setItemAnimator(new DefaultItemAnimator());
            recyclerViewSPLQ.setAdapter(sanPhamLQAdapter);

            EventButton();
        } else {
            CheckConnection.Show_Toast(getApplicationContext(), "Vui lòng kiểm tra lại kết nối!");
        }

    }

    private void EventButton() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.listGioHang.size() > 0) {
                    boolean exist = false;
                    Log.d("So luong sp trong gio: ", "" + listSanPham.size());
                    for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
                        if (MainActivity.listGioHang.get(i).getSanpham().getId_san_pham() == id_san_pham) {
                            MainActivity.listGioHang.get(i).setSoluong(MainActivity.listGioHang.get(i).getSoluong() + 1);
                            MainActivity.listGioHang.get(i).setTonggia(MainActivity.listGioHang.get(i).getTonggia() + gia_khuyen_mai);
                            Log.d("SL_S: ", String.valueOf(MainActivity.listGioHang.get(i).getSoluong() + 1));
                            Log.d("Gia_S: ", String.valueOf(MainActivity.listGioHang.get(i).getTonggia() + gia_khuyen_mai));
                            exist = true;
                        }
                    }

                    if (exist == false) {
                        int soluong = 1;
                        double giamoi = soluong * gia_khuyen_mai;
                        MainActivity.listGioHang.add(new GioHang(soluong, giamoi, sanPham));
                    }
                } else {
                    int soluong = 1;
                    double giamoi = soluong * gia_khuyen_mai;
                    MainActivity.listGioHang.add(new GioHang(soluong, giamoi, sanPham));
                    Log.d("SL: ", String.valueOf(giamoi));
                }
                Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ!", Toast.LENGTH_SHORT).show();
            }
        });

        btnTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTT.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void Mapping() {
        toolbarCTSP_Act = (Toolbar) findViewById(R.id.toolbarCTSP_Act);
        imgSP = (ImageView) findViewById(R.id.imgSP);
        txtTenSP = (TextView) findViewById(R.id.txtTenSP);
        txtGia = (TextView) findViewById(R.id.txtGia);
        txtGiaKM = (TextView) findViewById(R.id.txtGiaKM);
        txtMota = (TextView) findViewById(R.id.txtMota);
        btnAddCart = (Button) findViewById(R.id.btnAddCart);
        btnTT = (Button) findViewById(R.id.btnTT);

        recyclerViewSPLQ = (RecyclerView) findViewById(R.id.recyclerviewSPLQ);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarCTSP_Act);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCTSP_Act.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetThongTinSanPham() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id_san_pham = sanPham.getId_san_pham();
        ten_san_pham = sanPham.getTen_san_pham();
        id_danh_muc = sanPham.getId_danh_muc();
        id_thuong_hieu = sanPham.getId_thuong_hieu();
        mo_ta = sanPham.getMo_ta();
        kich_thuoc = sanPham.getKich_thuoc();
        gia_ban = sanPham.getGia_ban();
        gia_khuyen_mai = sanPham.getGia_khuyen_mai();
        hinh_anh_sp = sanPham.getHinh_anh_sp();
        id_mau_sac = sanPham.getId_mau_sac();
        ten_mau = sanPham.getTen_mau();
        code = sanPham.getCode();
        hinh_anh_sp_ms = sanPham.getHinh_anh_sp_ms();

        idSP = id_san_pham;

        Picasso.get().load(hinh_anh_sp)
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(imgSP);
        imgSP.setClipToOutline(true);

        txtTenSP.setText(ten_san_pham);

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        if (gia_ban == gia_khuyen_mai) {
            txtGia.setText(decimalFormat.format(gia_ban) + " đ");
        } else {
            txtGia.setText(decimalFormat.format(gia_ban) + " đ");
            txtGia.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            txtGiaKM.setText(decimalFormat.format(gia_khuyen_mai) + " đ");
        }

        txtMota.setText(mo_ta);

    }

    private void GetDataSPLQ() { //sanpham lien quan
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.URL_SPLienQuan;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                            hinh_anh_sp_ms = jsonObject.getString("hinh_anh_sp_ms");

                            listSanPham.add(new SanPham(id_san_pham,
                                    ten_san_pham, id_danh_muc,
                                    id_thuong_hieu, mo_ta,
                                    kich_thuoc, gia_ban,
                                    gia_khuyen_mai,
                                    Server.URL_ImgSanPham + hinh_anh_sp,
                                    id_mau_sac, ten_mau, code,
                                    hinh_anh_sp_ms));
                            sanPhamLQAdapter.notifyDataSetChanged();
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
        });
        requestQueue.add(stringRequest);
    }


    @Override
    public void onItemClick(SanPham sanPham) {
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
/*
                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }*/
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

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}