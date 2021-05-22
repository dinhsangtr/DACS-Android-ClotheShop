package com.doan.shop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.shop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private ListView listView;

    public HomeFragment(){
    }

    public static HomeFragment newInstance(String param1, String param2){
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionViewFlipper();
    }

    //slider
    private void ActionViewFlipper(){
        ArrayList<String> slider = new ArrayList<>();
        slider.add("https://p.w3layouts.com/demos_new/template_demo/20-06-2017/elite_shoppy-demo_Free/143933984/web/images/banner1.jpg");
        slider.add("https://p.w3layouts.com/demos_new/template_demo/20-06-2017/elite_shoppy-demo_Free/143933984/web/images/banner2.jpg");
        slider.add("https://p.w3layouts.com/demos_new/template_demo/20-06-2017/elite_shoppy-demo_Free/143933984/web/images/banner3.jpg");
        slider.add("https://p.w3layouts.com/demos_new/template_demo/20-06-2017/elite_shoppy-demo_Free/143933984/web/images/banner4.jpg");

        for (int i = 0; i< slider.size(); i++){
            ImageView imageView = new ImageView(getContext());
            Picasso.with(getContext()).load(slider.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        //set thoi gian chay
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }
    //anhs xaj
    private void Mapping(){
        recyclerView = (RecyclerView) recyclerView.findViewById(R.id.recyclerview);
        recyclerView1 = (RecyclerView) recyclerView1.findViewById(R.id.recyclerview1);
        viewFlipper = (ViewFlipper) viewFlipper.findViewById(R.id.viewflipper);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        return view;
    }


}