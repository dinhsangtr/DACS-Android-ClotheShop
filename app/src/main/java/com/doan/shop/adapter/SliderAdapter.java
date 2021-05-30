package com.doan.shop.adapter;

import android.content.Context;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.ImageLoader;
import com.doan.shop.R;
import com.doan.shop.model.Slider;
import com.doan.shop.util.CustomVolleyRequest;
import com.doan.shop.util.Server;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends PagerAdapter {
    Context context;
    private LayoutInflater layoutInflater;
    private List<Slider> listSlider;
    private ImageLoader imageLoader;

   public SliderAdapter(List<Slider> listSlider , Context context){
       this.listSlider = listSlider;
       this.context = context;
   }

    @Override
    public int getCount() {
        return listSlider.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position){
       layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View view = layoutInflater.inflate(R.layout.custom_layout, null);

       Slider slider = listSlider.get(position);

       ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
       imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
       imageLoader.get(Server.URL_ImgSlider + slider.getLink(), ImageLoader.getImageListener(imageView, R.drawable.banner1, android.R.drawable.ic_dialog_alert));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    Toast.makeText(context, "Slide 1 Clicked", Toast.LENGTH_SHORT).show();
                } else if(position == 1){
                    Toast.makeText(context, "Slide 2 Clicked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Slide 3 Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);

    }

}
