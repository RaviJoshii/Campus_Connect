package com.example.ravi.facultyandstudents;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
            R.drawable.notes2,
            R.drawable.notice2,
            R.drawable.dis,
            R.drawable.attendance2,
            R.drawable.wifi2,



    };
    public String[] heading={ "NOTES SECTION","ANNOUNCEMENT","DISCUSSION FORUM","ATTENDANCE SYSTEM","WIFI CHAT APPLICATION"
    };
    public String[] description={
            "Now Teacher can upload there notes and Notes/e-books and Students can Fetch those stuffs Simultaneously.Now Enjoy Easy Sharing of Documents",
            "Now Teachers can Upload Official Notices/Announcement and students can easily see that",
            "Now Teachers and Students can Together discuss on particular topic remotely over this platform",
            "Now Teachers can take the full use of the smart attendance system and would be easy task for further evaluation ",
            "Now Teachers and Students can communicate with heir classmate and faculty within wifi range & without internet"

    };
    @Override
    public int getCount() {
        return heading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
       return view ==(LinearLayout) object;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView slideimg=(ImageView) view.findViewById(R.id.slde_image);
        TextView slideheading=(TextView) view.findViewById(R.id.slide_heading);
        TextView slidedesc=(TextView) view.findViewById(R.id.slide_desc);
       slideimg.setImageResource(slide_images[position]);
       slidedesc.setText(description[position]);
        slideheading.setText(heading[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}

