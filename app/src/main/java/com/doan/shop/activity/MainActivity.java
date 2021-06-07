package com.doan.shop.activity;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.doan.shop.R;
import com.doan.shop.fragment.HomeFragment;
import com.doan.shop.fragment.Category_Pr_Fragment;
import com.doan.shop.fragment.CartFragment;
import com.doan.shop.fragment.NotiFragment;
import com.doan.shop.fragment.UserFragment;
import com.doan.shop.model.GioHang;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<GioHang> listGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (listGioHang != null) {

        } else {
            listGioHang = new ArrayList<>();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new HomeFragment());

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_category:
                    fragment = new Category_Pr_Fragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_cart:
                    fragment = new CartFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_noti:
                    fragment = new NotiFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_user:
                    fragment = new UserFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {

        // load fragment
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                long sleep = (long) (Math.random() * 1000L);
                try {
                    Thread.sleep(sleep);
                } catch (Exception exc) {
                }
            }
        });
        thread.start();
    }
}