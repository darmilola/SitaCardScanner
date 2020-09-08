package com.sita.android.sitaproject.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sita.android.sitaproject.Fragments.FrequenlyAskedQuestion;
import com.sita.android.sitaproject.Fragments.citizenuserinformation;
import com.sita.android.sitaproject.Fragments.homefragment;
import com.sita.android.sitaproject.Fragments.userinfofragment;
import com.sita.android.sitaproject.Models.citizeninfo;
import com.sita.android.sitaproject.R;
import com.sita.android.sitaproject.userinfofrag;

import java.util.ArrayList;
import java.util.List;


import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class citizenhomepage extends AppCompatActivity  implements ViewAnimator.ViewAnimatorListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list= new ArrayList<>();
    private citizenuserinformation fragment;
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;
    Toolbar toolbar;
    TextView mtitle;
    int flag=2;
    ArrayList<citizeninfo> citizeninfoArrayList;
    TextView NewRegistration,FingerprintAuth,BarcodeAuth,PincodeAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizenhomepage);


        citizeninfoArrayList = getIntent().getParcelableArrayListExtra("info");
        fragment = citizenuserinformation.newInstance(citizeninfoArrayList);

        getSupportFragmentManager().beginTransaction().replace(R.id.citizen_content_frame,fragment)
                .commit();

        drawerLayout= findViewById(R.id.citizendrawerlayout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout=(LinearLayout)findViewById(R.id.citizen_left_drawer);




        linearLayout.setOnClickListener(v -> drawerLayout.closeDrawers());



        setActionBar();
        createMenuList();

        viewAnimator = new ViewAnimator<>(citizenhomepage.this,list,fragment,drawerLayout,this);
    }





    private void createMenuList() {

        SlideMenuItem menuItem0= new SlideMenuItem("Close",R.drawable.colorsmallcanceldrawericon);


        SlideMenuItem menuItem2= new SlideMenuItem("user",R.drawable.newnewuserinfoicon);


        SlideMenuItem menuItem4= new SlideMenuItem("FAQ",R.drawable.newnewinfo);
//

        //    SlideMenuItem menuItem6= new SlideMenuItem("Close",R.drawable.bighome);
        //  SlideMenuItem menuItem7= new SlideMenuItem("Close",R.drawable.bighome);


        list.add(menuItem0);

        list.add(menuItem2);


        list.add(menuItem4);

        //list.add(menuItem5);
        //list.add(menuItem6);
        //list.add(menuItem7);


    }



    private void setActionBar() {

        toolbar = (Toolbar) findViewById(R.id.citizentoolbar);
        mtitle = (TextView) toolbar.findViewById(R.id.citizentoolbarTitle);
        Typeface customfont = Typeface.createFromAsset(getAssets(), "Anteb-SemiLight.otf");
        mtitle.setTypeface(customfont);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.Login_button_text, R.string.app_name) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();


            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                super.onDrawerSlide(drawerView, slideOffset);


                    if (linearLayout.getChildCount() == 0) {

                        viewAnimator.showMenuContent();


                }
            }



            @Override
            public void onDrawerOpened(View drawerView){

                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
    }



    @Override protected void onPostCreate(Bundle savedInstanceState){

        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();

    }

    @Override public void onConfigurationChanged(Configuration newConfig){

        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override

    public boolean onOptionsItemSelected(MenuItem item){
        if(drawerToggle.onOptionsItemSelected(item)){

            return true;

        }


        if(item.getItemId() == R.id.action_logout){

            Intent intent = new Intent(citizenhomepage.this,LoginPage.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()){

            case "Close":
                drawerLayout.closeDrawers();

                userinfofrag fr= userinfofrag.newInstance();
                return fr;

                case "user":

                  if(flag == 2){
                      userinfofrag fr1= userinfofrag.newInstance();
                      return fr1;
                  }

                  else {
                      flag = 2;
                      mtitle.setText("Information");
                      //return replaceToUserInfofrag(screenShotable, position);
                  }


            case "FAQ":
                if(flag == 4) {
                    userinfofrag fr2= userinfofrag.newInstance();
                    return fr2;
                }

                else {
                    flag = 4;
                    mtitle.setText("FAQ");
    //                    return replacetoFaq(screenShotable, position);

                }
        }


        return null;
    }

   /* private ScreenShotable replaceToUserInfofrag(ScreenShotable screenShotable,int position){
        View view =findViewById(R.id.citizen_content_frame);
        int radius= Math.max(view.getWidth(),view.getHeight());
        SupportAnimator animator= ViewAnimationUtils.createCircularReveal(view,0,position,0,radius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        //findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(),screenShotable.getBitmap()));
        //animator.start();
        citizenuserinformation citizenuserinformation =  com.sita.android.sitaproject.Fragments.citizenuserinformation.newInstance(citizeninfoArrayList);
        getSupportFragmentManager().beginTransaction().replace(R.id.citizen_content_frame,citizenuserinformation).commit();
        return citizenuserinformation;

    }*/


   /* private ScreenShotable replacetoFaq(ScreenShotable screenShotable, int position){


        View view =findViewById(R.id.citizen_content_frame);
        int radius= Math.max(view.getWidth(),view.getHeight());
        SupportAnimator animator= ViewAnimationUtils.createCircularReveal(view,0,position,0,radius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        //findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(),screenShotable.getBitmap()));
        // animator.start();
        FrequenlyAskedQuestion frequenlyAskedQuestion= FrequenlyAskedQuestion.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.citizen_content_frame,frequenlyAskedQuestion).commit();

        return frequenlyAskedQuestion;
    }*/

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    @Override
    public void onBackPressed(){


    }
}
