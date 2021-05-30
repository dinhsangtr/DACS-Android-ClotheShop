package com.doan.shop.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doan.shop.R;
import com.doan.shop.adapter.DanhMucChaAdapter;
import com.doan.shop.model.DanhMucCha;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {
    private ArrayList<DanhMucCha> listDMucCha;
    DanhMucChaAdapter dmcAdapter;

    public CategoryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        return view;
    }
}