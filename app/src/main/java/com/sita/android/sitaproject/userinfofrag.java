package com.sita.android.sitaproject;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import yalantis.com.sidemenu.interfaces.ScreenShotable;


/**
 * A simple {@link Fragment} subclass.
 */
public class userinfofrag extends Fragment implements ScreenShotable {

    Bitmap bitmap;

    public userinfofrag() {
        // Required empty public constructor
    }

    public static userinfofrag newInstance() {
        userinfofrag userinfofrag = new userinfofrag();
        return userinfofrag;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adminuserinformation, container, false);
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                // WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                //final DisplayMetrics displayMetrics = new DisplayMetrics();
                //manager.getDefaultDisplay().getMetrics(displayMetrics);
                //int height = displayMetrics.heightPixels;
                //int width = displayMetrics.widthPixels;
                //bitmap = Bitmap.createBitmap(width, height
                //      , Bitmap.Config.ARGB_8888);
                //Canvas canvas = new Canvas(bitmap);
                // containerview.draw(canvas);
                // Main2Fragment.this.bitmap=bitmap;
                bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.backarrow);

            }

        };

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
