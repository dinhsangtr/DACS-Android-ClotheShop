package com.doan.shop.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doan.shop.R;
import com.doan.shop.model.User;
import com.doan.shop.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ThanhToanActivity extends AppCompatActivity {

    EditText edtHoTenTT, edtDiaChiTT, edtEmailTT, edtPhoneTT, edtCodeSale, edtGhiChuCart1;
    RadioButton radio_cod, radio_atm, radio_zalo, rdo_giaohang, rdo_selected;
    Button btnXacNhanCoupon, btnThanhToanCart;
    Toolbar toolbarTT;
    TextView txtThanhTien, txtPhiShip, txtSale, txtTongTien;
    RadioGroup rdogroup;

    double tongtien = 0.0, thanhtien = 0.0;
    static Double phiShip = 15000.0;
    static Double giamGia = 0.0;
    String pthucttoan = "";

    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        Mapping();
        ActionToolbar();
        LoadData();

        btnXacNhanCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Chờ cập nhật", Toast.LENGTH_SHORT).show();
            }
        });


        btnThanhToanCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id_user = String.valueOf(MainActivity.user.get(0).getId_user());
                final String ho_ten = edtHoTenTT.getText().toString();
                final String sdt = edtPhoneTT.getText().toString();
                final String email = edtEmailTT.getText().toString();
                final String so_luong = String.valueOf(MainActivity.listGioHang.size());
                final String tong_tien = String.valueOf(tongtien);
                final String noi_nhan = edtDiaChiTT.getText().toString();
                final String ghi_chu = edtGhiChuCart1.getText().toString();
                final String hinh_thuc_thanh_toan = pthucttoan;
                final String coupon_code = edtCodeSale.getText().toString();
                String urlAddBill = Server.URL_AddBill;
                String urlAddBillDetail = Server.URL_AddBillDetail;
                if (edtHoTenTT.getText().length() > 0
                        && edtHoTenTT.getText().length() > 0
                        && edtHoTenTT.getText().length() > 0
                        && edtHoTenTT.getText().length() > 0) {


                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlAddBill, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (Integer.parseInt(response) > 0) {
                                Log.d("R1: ", response);

                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, urlAddBillDetail, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("R2: ", response);
                                        if (response.equals("1")) {
                                            MainActivity.listGioHang.clear();
                                            Toast.makeText(getApplicationContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ThanhToanActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Nullable
                                    @org.jetbrains.annotations.Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        /*JSONArray jsonArray = new JSONArray();
                                        for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("id_san_pham", MainActivity.listGioHang.get(0).getSanpham().getId_san_pham());
                                                jsonObject.put("so_luong", MainActivity.listGioHang.get(0).getSoluong());
                                                jsonObject.put("gia_ban", MainActivity.listGioHang.get(0).getTonggia());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());*/

                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("id_san_pham", String.valueOf(MainActivity.listGioHang.get(0).getSanpham().getId_san_pham()));
                                        hashMap.put("so_luong", String.valueOf(MainActivity.listGioHang.get(0).getSoluong()));
                                        hashMap.put("gia_ban", String.valueOf(MainActivity.listGioHang.get(0).getTonggia()));
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            } else {
                                Toast.makeText(getApplicationContext(), "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Nullable
                        @org.jetbrains.annotations.Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("id_user", id_user);
                            hashMap.put("ho_ten", ho_ten);
                            hashMap.put("sdt", sdt);
                            hashMap.put("email", email);
                            hashMap.put("so_luong", so_luong);
                            hashMap.put("tong_tien", tong_tien);
                            hashMap.put("noi_nhan", noi_nhan);
                            hashMap.put("ghi_chu", ghi_chu);
                            hashMap.put("hinh_thuc_thanh_toan", hinh_thuc_thanh_toan);
                            hashMap.put("coupon_code", coupon_code);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);


                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin giao hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //Load
    @SuppressLint("SetTextI18n")
    private void LoadData() {
        edtHoTenTT.setText(MainActivity.user.get(0).getHo_ten());
        edtDiaChiTT.setText(MainActivity.user.get(0).getDia_chi());
        edtEmailTT.setText(MainActivity.user.get(0).getEmail());
        edtPhoneTT.setText(MainActivity.user.get(0).getSdt());

        for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
            thanhtien += MainActivity.listGioHang.get(i).getTonggia();
        }
        tongtien = thanhtien + phiShip - giamGia;


        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtThanhTien.setText(decimalFormat.format(thanhtien) + "đ");
        //txtPhiShip.setText("Miễn phí");//test
        //tongtien = MainActivity.listGioHang.get(0).getTonggia() - phiShip - giamGia;
        txtTongTien.setText(decimalFormat.format(tongtien) + "đ");

        if (rdogroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
        } else {
            // get selected radio button from radioGroup
            int selectedId = rdogroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            rdo_selected = (RadioButton) findViewById(selectedId);
            pthucttoan = rdo_selected.getText().toString();
        }

    }

    //Toolbar
    private void ActionToolbar() {
        setSupportActionBar(toolbarTT);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTT.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Mapping() {
        edtHoTenTT = (EditText) findViewById(R.id.edtHoTenTT);
        edtDiaChiTT = (EditText) findViewById(R.id.edtDiaChiTT);
        edtEmailTT = (EditText) findViewById(R.id.edtEmailTT);
        edtPhoneTT = (EditText) findViewById(R.id.edtPhoneTT);
        edtCodeSale = (EditText) findViewById(R.id.edtCodeSale);
        edtGhiChuCart1 = (EditText) findViewById(R.id.edtGhiChuCart1);
        radio_cod = (RadioButton) findViewById(R.id.radio_cod);
        radio_atm = (RadioButton) findViewById(R.id.radio_atm);
        radio_zalo = (RadioButton) findViewById(R.id.radio_zalo);
        rdo_giaohang = (RadioButton) findViewById(R.id.rdo_giaohang);
        btnXacNhanCoupon = (Button) findViewById(R.id.btnXacNhanCoupon);
        btnThanhToanCart = (Button) findViewById(R.id.btnThanhToanCart);
        toolbarTT = (Toolbar) findViewById(R.id.toolbarTT);

        txtThanhTien = (TextView) findViewById(R.id.txtTien);
        txtPhiShip = (TextView) findViewById(R.id.txtShip);
        txtSale = (TextView) findViewById(R.id.txtSale);
        txtTongTien = (TextView) findViewById(R.id.txtTongTien);
        rdogroup = (RadioGroup) findViewById(R.id.rdogroup);
    }


}