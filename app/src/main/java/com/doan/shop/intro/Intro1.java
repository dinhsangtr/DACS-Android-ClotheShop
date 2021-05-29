package com.doan.shop.intro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doan.shop.R;


public class Intro1 extends Fragment {


    public Intro1() {

    }

    public static Intro1 newIstance(){
        Intro1 intro1 = new Intro1();

        return intro1 ;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro1, container, false);
    }
}