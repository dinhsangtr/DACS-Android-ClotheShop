package com.doan.shop.fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.doan.shop.R;
import com.doan.shop.adapter.DanhMucAdapter;
import com.doan.shop.adapter.DanhMucChaAdapter;
import com.doan.shop.model.DanhMuc;
import com.doan.shop.util.CheckConnection;
import com.doan.shop.util.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Category_Fragment extends Fragment implements DanhMucAdapter.ItemClickListener{
    private static final String  ARG_PARAM1 = "p1";
    private static final String  ARG_PARAM2 = "p2";

    private String mParam1;
    private String mParam2;

    private ArrayList<DanhMuc> listDMuc;
    private DanhMucAdapter danhMucAdapter;
    private RecyclerView recyclerviewDMucCon;
    private ImageView imageDanhMuc;

    public Category_Fragment() {
    }

    public static Category_Fragment newInstance(String id_danh_muc_cha, String ten){
        Category_Fragment fragment = new Category_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, id_danh_muc_cha);
        args.putString(ARG_PARAM2, ten);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_, container, false);
        recyclerviewDMucCon = view.findViewById(R.id.recyclerviewDMucCon);
        imageDanhMuc = getActivity().findViewById(R.id.imageDanhMuc);
        Toolbar toolbarDMuc = (Toolbar)view.findViewById(R.id.toolbarDanhMuc);
        toolbarDMuc.setTitle(mParam2);

        getDuLieuDMucCon();

        if (CheckConnection.haveNetworkConnection(getActivity().getApplicationContext())) {
            listDMuc = new ArrayList<>();
            danhMucAdapter = new DanhMucAdapter(listDMuc, getActivity().getApplicationContext(), this);
            recyclerviewDMucCon.setHasFixedSize(true);
            //
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerviewDMucCon.setLayoutManager(mLayoutManager);


            //?
            recyclerviewDMucCon.addItemDecoration(new Category_Pr_Fragment.GridSpacingItemDecoration(1, dpToPx(8), true));
            recyclerviewDMucCon.setItemAnimator(new DefaultItemAnimator());
            recyclerviewDMucCon.setAdapter(danhMucAdapter);
            //
            recyclerviewDMucCon.setNestedScrollingEnabled(false);

        } else {
            CheckConnection.Show_Toast(getActivity().getApplicationContext(), "Vui lòng kiểm tra lại kết nối!");
        }

        return view;
    }


    @Override
    public void onItemClick(DanhMuc danhMuc) {
        Fragment fragment = SanPhamFragment.newInstance(danhMuc.getId_danh_muc());
        FragmentTransaction ft = getActivity()
                .getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.layoutDMCon, fragment, "sp");
        ft.addToBackStack(null);
        ft.commit();
    }

    private void getDuLieuDMucCon() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URL_DMucCon + mParam1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id_danh_muc = 0;
                    String ten_danh_muc = "";
                    String hinh_anh = "";

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id_danh_muc = jsonObject.getInt("id_danh_muc");
                            ten_danh_muc = jsonObject.getString("ten_danh_muc");
                            hinh_anh = Server.URL_ImgDMucCha + jsonObject.getString("hinh_anh");
                            listDMuc.add(new DanhMuc(id_danh_muc, ten_danh_muc, hinh_anh));
                            danhMucAdapter.notifyDataSetChanged();
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




    //design
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
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
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