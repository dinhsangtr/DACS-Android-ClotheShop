package com.doan.shop.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doan.shop.R;
import com.doan.shop.activity.LoginActivity;
import com.doan.shop.activity.MainActivity;
import com.doan.shop.activity.RegisterActivity;
import com.doan.shop.util.Server;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    //chua dang nhap
    LinearLayout lnDangNhap;
    Button btnDangNhapFrag;

    //dang nhap roi
    LinearLayout lnDangNhapR;
    TextView txtUSName, txtName, txtBirth, txtAddress, txtEmail, txtPhone;
    Button btnChangeInfo, btnChangePw, btnLogOut;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        Mapping(view);
        CheckData();

        btnDangNhapFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setTitle("Thay đổi thông tin");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_changeinfo);

                Button btnOKInfo = (Button) dialog.findViewById(R.id.btnOKInfo);
                EditText edtHoTen = (EditText) dialog.findViewById(R.id.edtHoTen);
                EditText edtBirth = (EditText) dialog.findViewById(R.id.edtBirth);
                EditText edtAddress = (EditText) dialog.findViewById(R.id.edtAddress);
                EditText edtEmail = (EditText) dialog.findViewById(R.id.edtEmail);
                EditText edtPhone = (EditText) dialog.findViewById(R.id.edtPhone);

                edtHoTen.setText(MainActivity.user.get(0).getHo_ten());
                edtBirth.setText(getDate1(Long.parseLong(MainActivity.user.get(0).getNgay_sinh())));
                edtAddress.setText(MainActivity.user.get(0).getDia_chi());
                edtEmail.setText(MainActivity.user.get(0).getEmail());
                edtPhone.setText(MainActivity.user.get(0).getSdt());

                edtBirth.addTextChangedListener(new TextWatcher() {
                    private String current = "";
                    private String yyyymmdd = "YYYYMMDD";
                    private Calendar cal = Calendar.getInstance();

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!s.toString().equals(current)) {
                            String clean = s.toString().replaceAll("[^\\d.]", "");
                            String cleanC = current.replaceAll("[^\\d.]", "");

                            int cl = clean.length();
                            int sel = cl;
                            for (int i = 2; i <= cl && i < 6; i += 2) {
                                sel++;
                            }
                            //Fix for pressing delete next to a forward slash
                            if (clean.equals(cleanC)) sel--;

                            if (clean.length() < 8) {
                                clean = clean + yyyymmdd.substring(clean.length());
                            } else {
                                //This part makes sure that when we finish entering numbers
                                //the date is correct, fixing it otherwise
                                int year = Integer.parseInt(clean.substring(0, 4));
                                int mon = Integer.parseInt(clean.substring(4, 6));
                                int day = Integer.parseInt(clean.substring(6, 8));

                                if (mon > 12) mon = 12;
                                cal.set(Calendar.MONTH, mon - 1);

                                year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                                cal.set(Calendar.YEAR, year);
                                // ^ first set year for the line below to work correctly
                                //with leap years - otherwise, date e.g. 29/02/2012
                                //would be automatically corrected to 28/02/2012

                                day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                                clean = String.format("%02d%02d%02d", year, mon, day);
                            }

                            clean = String.format("%s-%s-%s", clean.substring(0, 4),
                                    clean.substring(4, 6),
                                    clean.substring(6, 8));

                            sel = sel < 0 ? 0 : sel;
                            current = clean;
                            edtBirth.setText(current);
                            edtBirth.setSelection(sel < current.length() ? sel : current.length());


                        }

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                btnOKInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edtHoTen.getText().length() != 0
                                && edtBirth.getText().length() != 0
                                && edtAddress.getText().length() != 0
                                && edtEmail.getText().length() != 0
                                && edtPhone.getText().length() != 0) {
                            //Toast.makeText(getContext(), "Chờ update", Toast.LENGTH_SHORT).show();
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            String url = Server.URL_UpdateInfo
                                    + "?username=" + MainActivity.user.get(0).getUsername()
                                    + "&hoten=" + edtHoTen.getText().toString()
                                    + "&ngaysinh=" + edtBirth.getText().toString()
                                    + "&diachi=" + edtAddress.getText().toString()
                                    + "&email=" + edtEmail.getText().toString()
                                    + "&sdt=" + edtPhone.getText().toString();
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("ecececece", response + "");
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                            requestQueue.add(stringRequest);
                            Toast.makeText(getContext(), "Thay đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        } else {
                            Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });

        btnChangePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setTitle("Đổi mật khẩu");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_changepw);


                Button btnOKPw = (Button) dialog.findViewById(R.id.btnOKPw);
                EditText edtOldPw = (EditText) dialog.findViewById(R.id.edtOldPw);
                EditText edtNewPw = (EditText) dialog.findViewById(R.id.edtNewPw);
                EditText edtReNewPw = (EditText) dialog.findViewById(R.id.edtReNewPw);


                btnOKPw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edtOldPw.getText().length() != 0
                                && edtNewPw.getText().length() != 0
                                && edtReNewPw.getText().length() != 0) {
                            if(edtNewPw.getText().toString().equals(edtReNewPw.getText().toString())){
                                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                String url = Server.URL_ChangePW
                                        + "?username=" + MainActivity.user.get(0).getUsername()
                                        + "&password=" + edtOldPw.getText().toString()
                                        + "&newpassword=" + edtNewPw.getText().toString();
                                Log.d("url: ", url);
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("checkok: ", response);
                                        int check = Integer.parseInt(response);
                                        if (check == 0) {
                                            Toast.makeText(getActivity().getApplicationContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                                        } else if (check == 1) {
                                            Toast.makeText(getActivity().getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getActivity().getApplicationContext(), "Error !", Toast.LENGTH_SHORT).show();
                                        Log.d("AAA", error.toString());
                                    }
                                });
                                requestQueue.add(stringRequest);
                            }



                        } else {
                            Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc muốn đăng xuất?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.user.remove(0);
                        // LoginActivity.listUser.remove(0);
                        lnDangNhap.setVisibility(View.VISIBLE);
                        lnDangNhapR.setVisibility(View.INVISIBLE);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        return view;
    }


    //Convert TimeStamp to Date
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    private String getDate1(long time) {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("yyyy-MM-dd", cal).toString();
        return date;
    }

    //
    private void Mapping(View view) {
        lnDangNhap = (LinearLayout) view.findViewById(R.id.lnDangNhap);
        lnDangNhapR = (LinearLayout) view.findViewById(R.id.lnDangNhapR);
        btnDangNhapFrag = (Button) view.findViewById(R.id.btnDangNhapFrag);
        //
        txtUSName = (TextView) view.findViewById(R.id.txtUSName);
        txtName = (TextView) view.findViewById(R.id.txtName);
        txtBirth = (TextView) view.findViewById(R.id.txtBirth);
        txtAddress = (TextView) view.findViewById(R.id.txtAddress);
        txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        txtPhone = (TextView) view.findViewById(R.id.txtPhone);
        btnChangeInfo = (Button) view.findViewById(R.id.btnChangeInfo);
        btnChangePw = (Button) view.findViewById(R.id.btnChangePw);
        btnLogOut = (Button) view.findViewById(R.id.btnLogOut);
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        txtUSName.setText(MainActivity.user.get(0).getUsername());
        txtName.setText(MainActivity.user.get(0).getHo_ten());
        txtBirth.setText(getDate(Long.parseLong(MainActivity.user.get(0).getNgay_sinh())));
        txtAddress.setText(MainActivity.user.get(0).getDia_chi());
        txtEmail.setText(MainActivity.user.get(0).getEmail());
        txtPhone.setText(MainActivity.user.get(0).getSdt());
    }


    private void CheckData() {
        if (MainActivity.user.size() <= 0) {
            lnDangNhap.setVisibility(View.VISIBLE);
            lnDangNhapR.setVisibility(View.INVISIBLE);
        } else {
            lnDangNhap.setVisibility(View.INVISIBLE);
            lnDangNhapR.setVisibility(View.VISIBLE);

            loadData();
        }

    }
}