package com.sita.android.sitaproject.Fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.sita.android.sitaproject.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;


/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment implements ScreenShotable {

     Bitmap bitmap;
     public History() {
        // Required empty public constructor
    }


    public static History newInstance(){

        History fragment= new History();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void takeScreenShot() {


        Thread thread= new Thread(){
            @Override
            public void run(){

                WindowManager manager=(WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
                final DisplayMetrics displayMetrics=new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(displayMetrics);
                int height=displayMetrics.heightPixels;
                int width= displayMetrics.widthPixels;
                bitmap= Bitmap.createBitmap(width,height
                        ,Bitmap.Config.ARGB_8888);
                Canvas canvas=new Canvas(bitmap);
                //containerView.draw(canvas);
                // Main2Fragment.this.bitmap=bitmap;
            }

        };
        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
