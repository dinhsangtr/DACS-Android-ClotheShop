package com.doan.shop.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.doan.shop.R;
import com.doan.shop.activity.ChiTietSPActivity;
import com.doan.shop.activity.LoginActivity;
import com.doan.shop.activity.MainActivity;
import com.doan.shop.activity.ThanhToanActivity;
import com.doan.shop.adapter.GioHangAdapter;
import com.doan.shop.model.SanPham;
import com.doan.shop.util.CheckConnection;

import java.text.DecimalFormat;


public class CartFragment extends Fragment implements GioHangAdapter.ItemClickListener {
    ListView lvCart;
    TextView txtThongBaoCart;
    TextView txtGhiChuCart;
    static TextView txtTongGiaCart;
    Button btnTTThanhToanCart;

    GioHangAdapter gioHangAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        Mapping(view);
        gioHangAdapter = new GioHangAdapter(getActivity(), MainActivity.listGioHang, this);
        lvCart.setAdapter(gioHangAdapter);
        if (CheckConnection.haveNetworkConnection(getActivity().getApplicationContext())) {
            CheckData();
            EventUtil();
            CatchOnItemListView();
            EventButton();
        } else {
            CheckConnection.Show_Toast(getActivity().getApplicationContext(), "Vui lòng kiểm tra lại kết nối!");
        }
        return view;
    }

    private void EventButton() {
        btnTTThanhToanCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.listGioHang.size() > 0) {
                    if (MainActivity.user.size() <= 0) {
                        Dialog dialog = new Dialog(getContext());
                        dialog.setTitle("Thông báo");
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.dialog_dangnhap);

                        Button btnDNhap = (Button) dialog.findViewById(R.id.btnDNhap);
                        btnDNhap.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        });

                        dialog.show();
                    } else {
                        Intent intent = new Intent(getActivity().getApplicationContext(), ThanhToanActivity.class);
                        startActivity(intent);
                    }
                } else {
                    CheckConnection.Show_Toast(getActivity(), "Giỏ hàng của bạn chưa có sản phẩm");
                }

            }
        });
    }

    private void CatchOnItemListView() {
        lvCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.listGioHang.size() <= 0) {
                            txtThongBaoCart.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.listGioHang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventUtil();
                            if (MainActivity.listGioHang.size() <= 0) {
                                txtThongBaoCart.setVisibility(View.VISIBLE);
                            } else {
                                txtThongBaoCart.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                CartFragment.EventUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventUtil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public static void EventUtil() {
        double tongtien = 0;
        for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
            tongtien += MainActivity.listGioHang.get(i).getTonggia();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongGiaCart.setText(decimalFormat.format(tongtien) + "đ");

    }


    private void Mapping(View view) {
        lvCart = (ListView) view.findViewById(R.id.lvCart);
        txtThongBaoCart = (TextView) view.findViewById(R.id.txtThongBaoCart);
        //txtGhiChuCart = (TextView) view.findViewById(R.id.txtGhiChuCart);
        txtTongGiaCart = (TextView) view.findViewById(R.id.txtTongGiaCart);
        btnTTThanhToanCart = (Button) view.findViewById(R.id.btnTTThanhToanCart);
    }

    private void CheckData() {
        if (MainActivity.listGioHang.size() <= 0) {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBaoCart.setVisibility(View.VISIBLE);
            lvCart.setVisibility(View.INVISIBLE);
        } else {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBaoCart.setVisibility(View.INVISIBLE);
            lvCart.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onItemClick(SanPham sanPham) {
        Thread thread = new Thread(() -> {
            Intent intent = new Intent(getActivity(), ChiTietSPActivity.class);
            intent.putExtra("thongtinsanpham", sanPham);
            startActivity(intent);
        });
        thread.start();


        long sleep = (long) (Math.random() * 1000L);
        try {
            Thread.sleep(sleep);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}