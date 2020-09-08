package com.sita.android.sitaproject.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.sita.android.sitaproject.Models.citizeninfo;
import com.sita.android.sitaproject.R;
import com.sita.android.sitaproject.Activities.citizenProfileInfoActivity;

import java.util.ArrayList;

import yalantis.com.sidemenu.interfaces.ScreenShotable;


/**
 * A simple {@link Fragment} subclass.
 */
public class userinfofragment extends Fragment implements ScreenShotable {

 View containerView;
 Bitmap bitmap;
 static ArrayList<citizeninfo> info = new ArrayList<>();

 TextView socialbenefit,taxinfo,healthinfo,profileinfo;
    public static userinfofragment newInstance(ArrayList<citizeninfo> citizeninfoArrayList ){
        userinfofragment userinfofragment= new userinfofragment();
        info = citizeninfoArrayList;
        return userinfofragment;

    }

    public userinfofragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedinstancestate){
        super.onViewCreated(view,savedinstancestate);
        this.containerView=view.findViewById(R.id.userinfocontainer);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_adminuserinformation, container, false);

        socialbenefit= view.findViewById(R.id.newsocialbenefittext);
        taxinfo= view.findViewById(R.id.taxinfotext);
        healthinfo= view.findViewById(R.id.healthinfotext);
        profileinfo=view.findViewById(R.id.profiletextview);

        Typeface customfont= Typeface.createFromAsset(getActivity().getAssets(),"Anteb-SemiLight.otf");
        socialbenefit.setTypeface(customfont);
        taxinfo.setTypeface(customfont);
        healthinfo.setTypeface(customfont);
        profileinfo.setTypeface(customfont);

        if(info.size() == 0){
            socialbenefit.setEnabled(false);
            taxinfo.setEnabled(false);
            healthinfo.setEnabled(false);
            profileinfo.setEnabled(false);
        }


        profileinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), citizenProfileInfoActivity.class);
                intent.putParcelableArrayListExtra("info",info);
                startActivity(intent);
            }
        });

        return view;
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
