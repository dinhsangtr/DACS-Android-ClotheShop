package com.doan.shop.fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.doan.shop.R;
import com.doan.shop.adapter.DanhMucChaAdapter;
import com.doan.shop.model.DanhMucCha;
import com.doan.shop.util.CheckConnection;
import com.doan.shop.util.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Category_Pr_Fragment extends Fragment implements DanhMucChaAdapter.ItemClickListener {
    private ArrayList<DanhMucCha> listDMucCha;
    private DanhMucChaAdapter dmcAdapter;
    private RecyclerView recyclerviewDMC;
    private ImageView imageViewDMC;


    TextView test;

    public Category_Pr_Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pr_category, container, false);

        recyclerviewDMC = view.findViewById(R.id.recyclerviewDMC);
        imageViewDMC = getActivity().findViewById(R.id.imageViewDMC);
        test = (TextView) view.findViewById(R.id.test);
        getDuLieuDMucCha();
        //Danh muc cha
        if (CheckConnection.haveNetworkConnection(getActivity().getApplicationContext())) {
            listDMucCha = new ArrayList<>();
            dmcAdapter = new DanhMucChaAdapter(listDMucCha, getActivity().getApplicationContext(), this);
            recyclerviewDMC.setHasFixedSize(true);
            //
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerviewDMC.setLayoutManager(mLayoutManager);


            //?
            recyclerviewDMC.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
            recyclerviewDMC.setItemAnimator(new DefaultItemAnimator());
            recyclerviewDMC.setAdapter(dmcAdapter);
            //
            recyclerviewDMC.setNestedScrollingEnabled(false);

        } else {
            CheckConnection.Show_Toast(getActivity().getApplicationContext(), "Vui lòng kiểm tra lại kết nối!");
        }

        /*
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
            }
        });
        */


        return view;
    }


    private void getDuLieuDMucCha() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.URL_DMucCha, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id_danh_muc_cha = 0;
                    String ten_danh_muc_cha = "";
                    String hinh_anh = "";

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id_danh_muc_cha = jsonObject.getInt("id_danh_muc_cha");
                            ten_danh_muc_cha = jsonObject.getString("ten_danh_muc_cha");
                            hinh_anh = Server.URL_ImgDMucCha + jsonObject.getString("hinh_anh");
                            listDMucCha.add(new DanhMucCha(id_danh_muc_cha, ten_danh_muc_cha, hinh_anh));
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

    @Override
    public void onItemClick(DanhMucCha danhMucCha) {
        Fragment fragment = Category_Fragment.newInstance(String.valueOf(danhMucCha.getId_danh_muc_cha()), danhMucCha.getTen_danh_muc_cha());
        FragmentTransaction ft = getActivity()
                .getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.layout_DanhMucCha, fragment, "categoty");
        ft.addToBackStack(null);
        ft.commit();
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
