package com.doan.shop.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.doan.shop.R;
import com.doan.shop.util.DataLocalManager;

public class IntroActivity extends AppCompatActivity {

    private Button btnDangNhap;
    private TextView btnSkip;
    private View mView;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Mapping();

        if (!DataLocalManager.getFirstInstall()){
            DataLocalManager.setFirstInstall(true);
        }else {
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
        }

        /*//INTRO
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String firstStart = prefs.getString("firstStart", "");

        if (firstStart.equals("Yes")){

        }else {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("firstStart", "Yes");
            editor.apply();
        }
        //END-INTRO
*/

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(IntroActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Mapping() {
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        btnSkip = (TextView) findViewById(R.id.btnSkip);
    }

}