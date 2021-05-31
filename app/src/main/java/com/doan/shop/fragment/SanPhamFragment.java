package com.doan.shop.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doan.shop.R;


public class SanPhamFragment extends Fragment {

   

    public SanPhamFragment() {
        // Required empty public constructor
    }

    public static SanPhamFragment newInstance(int param){
        SanPhamFragment fragment = new SanPhamFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_san_pham, container, false);
        return  view;
    }
}