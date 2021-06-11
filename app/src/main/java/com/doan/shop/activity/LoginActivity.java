package com.doan.shop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.doan.shop.model.SanPham;
import com.doan.shop.model.User;
import com.doan.shop.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.logging.Logger;

public class LoginActivity extends AppCompatActivity {
    TextView btnDangKy, btnSkipLogin;
    EditText txtUsername, txtPassword;
    Button btnDangNhap;
    String ch = "123";
    public static ArrayList<User> listUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Mapping();


        btnSkipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (txtUsername.getText().length() != 0 && txtPassword.getText().length() != 0) {
                    CheckUser(txtUsername.getText().toString(), txtPassword.getText().toString());

                    //CheckUserBool(txtUsername.getText().toString(), txtPassword.getText().toString());
                    //Log.d("response", ch + "");


                    if (listUser != null) {
                        Log.d("Test", "OK");
                    } else {
                        Log.d("Test", "NO");
                    }

                    if (listUser != null) {
                        Log.d("Test", "Login OK");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Tài khoản không đúng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                //Log.d("Test", "Yes");
            }
        });

    }


    private void CheckUserBool(String user, String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.URL_CheckUserBool + "?username=" + user + "&password=" + password;
        // ch = url;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ch = response;
                Log.d("ecececece", response + "");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

    }

    private void CheckUser(String user, String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Server.URL_CheckUser + "?username=" + user + "&password=" + password;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id_user = 0;
                String username = "";
                String password = "";
                String ho_ten = "";
                String ngay_sinh = null;
                String dia_chi = "";
                String email = "";
                String sdt = "";

                if (response != null ) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id_user = jsonObject.getInt("id_user");
                            username = jsonObject.getString("username");
                            password = jsonObject.getString("password");
                            ho_ten = jsonObject.getString("ho_ten");
                            ngay_sinh = jsonObject.getString("ngay_sinh");
                            dia_chi = jsonObject.getString("dia_chi");
                            email = jsonObject.getString("email");
                            sdt = jsonObject.getString("sdt");

                            listUser.add(new User(id_user, username, password, ho_ten, ngay_sinh, dia_chi, email, sdt));
                            MainActivity.user = listUser;
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

    private void getDataUser() {

    }

    private void Mapping() {
        btnDangKy = (TextView) findViewById(R.id.bntDangKy);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        // btnLoginByFB = (LoginButton) findViewById(R.id.btnLoginByFB);
        btnSkipLogin = (TextView) findViewById(R.id.btnSkipLogin);
    }


}