package com.sita.android.sitaproject.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sita.android.sitaproject.R;

public class LoginPage extends AppCompatActivity {

    TabLayout tabLayout;
    TextView logindesign,leftdesign,rightdesign,todesign,youraccountdesgn;
   static TextView mode;
   int flag = 0;
//    Context context= getApplicationContext();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.loginpage);
        WindowManager.LayoutParams layout = getWindow().getAttributes();
       // final LinearLayout linearLayout=findViewById( R.id.loginpagebackground);
        layout.screenBrightness = 7F;
        FragmentPagerAdapter adapter;
        final ViewPager viewPager=(ViewPager)findViewById(R.id.loginviewpager);
        viewPager.setCurrentItem(0);

        adapter=new LoginAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


         mode= findViewById(R.id.adminlogin);
        TextView logintitle= findViewById(R.id.logintitle);

        Typeface customfont= Typeface.createFromAsset(getAssets(),"Anteb-SemiLight.otf");
        Typeface customfont2= Typeface.createFromAsset(getAssets(),"AntebBold.otf");
        mode.setTypeface(customfont);
        logintitle.setTypeface(customfont2);

       mode.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(flag == 0){
                    int pos= viewPager.getCurrentItem();
                   viewPager.setCurrentItem(pos+1);
                   mode.setText(R.string.Not_admin);
                   flag=1;
               }

               else if(flag == 1){
                    int pos= viewPager.getCurrentItem();
                   viewPager.setCurrentItem(pos-1);
                   mode.setText(R.string.Admin);
                   flag = 0;
               }


               }
       });

       }


    @Override
    public void onBackPressed(){


    }

    public static void setModeEnable(boolean state){
        mode.setEnabled(state);
    }
       public static void setMode(int Mode){

        mode.setText(Mode);
       }

}
