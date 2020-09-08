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

import com.sita.android.sitaproject.Activities.citizenProfileInfoActivity;
import com.sita.android.sitaproject.Models.citizeninfo;
import com.sita.android.sitaproject.R;

import java.util.ArrayList;

import yalantis.com.sidemenu.interfaces.ScreenShotable;


/**
 * A simple {@link Fragment} subclass.
 */
public class citizenuserinformation extends Fragment implements ScreenShotable {

    View containerView;
    Bitmap bitmap;
   public static ArrayList<citizeninfo> citizeninfos;


    TextView socialbenefit,taxinfo,healthinfo,profileinfo;

    public citizenuserinformation() {
        // Required empty public constructor
    }

    public static citizenuserinformation newInstance(ArrayList<citizeninfo> infos){
        citizenuserinformation citizenuserinformation = new citizenuserinformation();
        citizeninfos = infos;
        return citizenuserinformation;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedinstancestate){
        super.onViewCreated(view,savedinstancestate);
        this.containerView=view.findViewById(R.id.Citizenuserinfocontainer);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_citizenuserinformation, container, false);

        socialbenefit= view.findViewById(R.id.newcitizensocialbenefittext);
        taxinfo= view.findViewById(R.id.citizentaxinfotext);
        healthinfo= view.findViewById(R.id.citizenhealthinfotext);
        profileinfo=view.findViewById(R.id.citizenprofiletextview);

        Typeface customfont= Typeface.createFromAsset(getActivity().getAssets(),"Anteb-SemiLight.otf");
        socialbenefit.setTypeface(customfont);
        taxinfo.setTypeface(customfont);
        healthinfo.setTypeface(customfont);
        profileinfo.setTypeface(customfont);

       /* socialbenefit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), citizenSocialBenefit.class);

                startActivity(intent);
            }
        });*/

        profileinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), citizenProfileInfoActivity.class);
                intent.putParcelableArrayListExtra("info",citizeninfos);
                intent.putExtra("citizen","citizen");
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
