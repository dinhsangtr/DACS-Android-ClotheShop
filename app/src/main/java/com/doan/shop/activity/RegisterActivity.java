package com.doan.shop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doan.shop.R;
import com.doan.shop.util.Server;

public class RegisterActivity extends AppCompatActivity {
    TextView btnSkipRegister;
    EditText txtUsername, txtHoTen, txtSdt, txtPassword, txtRePassword;
    Button btnDangKy;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Mapping();

        btnSkipRegister.setOnClickListener(v -> finish());

        //test
        txtUsername.setText("dinhsang");
        txtHoTen.setText("dinhsang");
        txtSdt.setText("0978529631");
        txtPassword.setText("123");
        txtRePassword.setText("123");
        //end-test

        btnDangKy.setOnClickListener(v -> {
            if (txtUsername.getText().length() != 0
                    && txtHoTen.getText().length() != 0
                    && txtSdt.getText().length() != 0
                    && txtPassword.getText().length() != 0
                    && txtRePassword.getText().length() != 0) {
                if (txtRePassword.getText().toString().equals(txtPassword.getText().toString())) {

                    //InsertUser(txtUsername.getText().toString(), txtHoTen.getText().toString(), txtSdt.getText().toString(), txtPassword.getText().toString());
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    String url = Server.URL_InsertUser
                            + "?username=" + txtUsername.getText().toString()
                            + "&hoten=" + txtHoTen.getText().toString()
                            + "&sdt=" + txtSdt.getText().toString()
                            + "&password=" + txtPassword.getText().toString();
                    Log.d("url: ", url);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("checkok: ", response);
                            int check = Integer.parseInt(response);
                            if (check == 0) {
                                Toast.makeText(getApplicationContext(), "Tên đăng nhập hoặc số điện thoại bị trùng", Toast.LENGTH_SHORT).show();
                            } else if (check == 1) {
                                Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                //finish(); //tat activity nay khi dk thanh cong
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, "Error !", Toast.LENGTH_SHORT).show();
                            Log.d("AAA", error.toString());
                        }
                    });
                    requestQueue.add(stringRequest);

                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng xác nhận lại mật khẩu", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

    }

/*
    private void InsertUser(String username, String ho_ten, String sdt, String pw) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        String url = Server.URL_InsertUser
                + "?username=" + username
                + "&hoten=" + ho_ten
                + "&sdt=" + sdt
                + "&password=" + pw;
        // StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            if (response != null) {


                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);
                        int check = jsonObject.getInt("check");
                        listcheck.add(new Check(check));
                        MainActivity.checks = listcheck;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("TAG: ", response.toString());
                // resp = check; Log.d("resp:" , resp+"");
                    /*
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        check = jsonObject.getString("check");
                        resp = check;
                        Log.d("resp:" , resp+"");
                    }*/
            /*}
        }, error -> {

        });
        Toast.makeText(getApplicationContext(), "Test" + MainActivity.checks.size(), Toast.LENGTH_SHORT).show();
        requestQueue.add(jsonArrayRequest);
        // requestQueue.add(stringRequest);
    }*/



    private void Mapping() {
        txtUsername = (EditText) findViewById(R.id.txtUsernameRe);
        txtHoTen = (EditText) findViewById(R.id.txtHoTen);
        txtSdt = (EditText) findViewById(R.id.txtSdt);
        txtPassword = (EditText) findViewById(R.id.txtPasswordRe);
        txtRePassword = (EditText) findViewById(R.id.txtRePassword);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        btnSkipRegister = (TextView) findViewById(R.id.btnSkipRegister);
    }


}