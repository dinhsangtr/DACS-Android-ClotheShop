package com.doan.shop.activity;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.doan.shop.fragment.OrderFragment;
import com.doan.shop.R;
import com.doan.shop.fragment.UserFragment;
import com.doan.shop.fragment.HomeFragment;
import com.doan.shop.intro.Intro1;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.doan.shop.fragment.HelpFragment;
/*import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnChangeListener;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnLeftOutListener;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;
*/
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       loadFragment(new HomeFragment());

    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()){
                case R.id.nav_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_order:
                    fragment = new OrderFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_help:
                    fragment = new HelpFragment();
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Intro1 intro1= Intro1.newIstance();
        transaction.replace(R.id.frame_container, intro1);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}