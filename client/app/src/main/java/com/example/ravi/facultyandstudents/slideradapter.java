package com.example.ravi.facultyandstudents;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by ravi on 30/7/18.
 */

public class slideradapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public slideradapter(Context context){
        this.context=context;
    }
    public int[] slide_images={


    };
    public String[] heading={

    };
    public String[] description={

    };
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);
        //  ImageView slideimg=(ImageView) view.findViewById(R.id.)
        //similarly all the templates

        //slideimg.setImageResource(slide_images[position]);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}

