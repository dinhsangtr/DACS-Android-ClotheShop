package com.doan.shop.intro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doan.shop.R;

public class MainIntro extends Fragment {



    public MainIntro() {
        // Required empty public constructor
    }
    public static MainIntro newIstance(){
        MainIntro mainIntro = new MainIntro();
        return mainIntro;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_intro,container,false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        return view;
    }
}