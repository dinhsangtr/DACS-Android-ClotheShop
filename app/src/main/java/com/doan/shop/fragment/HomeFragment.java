package com.doan.shop.fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.doan.shop.R;
import com.doan.shop.adapter.SanPhamAdapter;
import com.doan.shop.adapter.SliderAdapter;
import com.doan.shop.model.SanPham;
import com.doan.shop.model.Slider;
import com.doan.shop.util.CheckConnection;
import com.doan.shop.util.CustomVolleyRequest;
import com.doan.shop.util.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    //slider
    private LinearLayout slideDotspanel;
    private CircleIndicator circleIndicator;
    private ViewPager viewPager;
    private ImageView[] dots;
    private int dotscount;
    private Timer timer;
    //
    private RecyclerView recyclerView; //noi bat
    private RecyclerView recyclerView1; //moi
    private RequestQueue rq;
    private List<Slider> listSlider;
    private SliderAdapter sliderAdapter;
    //sp noi bat
    private ArrayList<SanPham> mangSP;
    private SanPhamAdapter sanphamAdapter;//khuon mau
    //sp moi
    private ArrayList<SanPham> mangSP1;
    private SanPhamAdapter sanphamAdapter1;//khuon mau



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
        circleIndicator = view.findViewById(R.id.circleIndicator);
        getDuLieuSP_NB();
        getDuLieuSP_M();
        //kiem tra ket noi
        if (CheckConnection.haveNetworkConnection(getActivity().getApplicationContext())) {

            //Slider
            rq = Volley.newRequestQueue(getActivity().getApplicationContext());
            listSlider = new ArrayList<>();
            viewPager = (ViewPager) view.findViewById(R.id.view_pager);
            slideDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);
            getDuLieuSlider();

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for(int i = 0; i< dotscount; i++){
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.banner2));
                    }
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.banner1));
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });



            //view sp noi bat
            //mapping
            mangSP = new ArrayList<>();
            sanphamAdapter = new SanPhamAdapter(getActivity().getApplicationContext(), mangSP);
            recyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(sanphamAdapter);
            //
            recyclerView.setNestedScrollingEnabled(false);

            //view sp noi bat
            mangSP1 = new ArrayList<>();
            sanphamAdapter1 = new SanPhamAdapter(getActivity().getApplicationContext(), mangSP1);
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
        return view;
    }

    //Slider
    private void getDuLieuSlider() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.URL_Slider, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        Slider slider = new Slider();
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            slider.setId_slider(jsonObject.getInt("id_slider"));
                            slider.setLink(jsonObject.getString("link"));
                            slider.setMo_ta_1(jsonObject.getString("mo_ta_1"));
                            slider.setMo_ta_2(jsonObject.getString("mo_ta_2"));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        listSlider.add(slider);
                    }
                    sliderAdapter = new SliderAdapter(listSlider, getActivity().getApplicationContext());
                    viewPager.setAdapter(sliderAdapter);

                    circleIndicator.setViewPager(viewPager);
                    sliderAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

                    dotscount = sliderAdapter.getCount();
                    dots = new ImageView[dotscount];

                    autoSlideImage(dotscount);

                    for(int i = 0; i < dotscount; i++){

                        dots[i] = new ImageView(getActivity().getApplicationContext());
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.banner2));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        params.setMargins(8, 0, 8, 0);

                        slideDotspanel.addView(dots[i], params);

                    }
                    dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.banner1));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        CustomVolleyRequest.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonArrayRequest);
    }

    private void autoSlideImage(int dotscount){
        if (listSlider == null || listSlider.isEmpty() || viewPager == null){
            return;
        }

        if(timer == null){
            timer = new Timer();
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() { //
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = dotscount - 1;
                        if (currentItem < totalItem){
                            currentItem ++;
                            viewPager.setCurrentItem(currentItem);
                        }else{
                            viewPager.setCurrentItem(0);
                        }

                    }
                });
            }
        }, 500, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null){
            timer.cancel();
            timer = null;
        }
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