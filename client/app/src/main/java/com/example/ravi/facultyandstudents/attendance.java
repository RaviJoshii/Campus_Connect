package com.example.ravi.facultyandstudents;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ravi on 4/8/18.
 */

public class attendance extends Fragment {
    View myview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myview= inflater.inflate(R.layout.attendance,container,false);
        return myview;
    }

}