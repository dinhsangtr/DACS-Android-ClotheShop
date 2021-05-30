package com.doan.shop.fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.doan.shop.R;
import com.doan.shop.adapter.DanhMucChaAdapter;
import com.doan.shop.model.DanhMucCha;
import com.doan.shop.model.SanPham;
import com.doan.shop.util.CheckConnection;
import com.doan.shop.util.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {
    private ArrayList<DanhMucCha> listDMucCha;
    private DanhMucChaAdapter dmcAdapter;
    private RecyclerView recyclerviewDMC;

    public CategoryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerviewDMC = view.findViewById(R.id.recyclerviewDMC);

        getDuLieuSP_NB();
        //Danh muc cha
        if (CheckConnection.haveNetworkConnection(getActivity().getApplicationContext())) {
            listDMucCha = new ArrayList<>();
            dmcAdapter = new DanhMucChaAdapter(listDMucCha, getActivity().getApplicationContext());
            recyclerviewDMC.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerviewDMC.setLayoutManager(mLayoutManager);
            recyclerviewDMC.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
            recyclerviewDMC.setItemAnimator(new DefaultItemAnimator());
            recyclerviewDMC.setAdapter(dmcAdapter);
            //
            recyclerviewDMC.setNestedScrollingEnabled(false);
        } else {
            CheckConnection.Show_Toast(getActivity().getApplicationContext(), "Vui lòng kiểm tra lại kết nối!");
        }
        return view;
    }

    private void getDuLieuSP_NB() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URL_DMucCha, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id_danh_muc_cha = 0;
                    String ten_danh_muc_cha = "";

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id_danh_muc_cha = jsonObject.getInt("id_danh_muc_cha");
                            ten_danh_muc_cha = jsonObject.getString("ten_danh_muc_cha");

                            listDMucCha.add(new DanhMucCha(id_danh_muc_cha, ten_danh_muc_cha));
                            dmcAdapter.notifyDataSetChanged();
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