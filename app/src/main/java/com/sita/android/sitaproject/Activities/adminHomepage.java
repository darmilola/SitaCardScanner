package com.sita.android.sitaproject.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
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

import com.sita.android.sitaproject.Fragments.History;
import com.sita.android.sitaproject.Fragments.FrequenlyAskedQuestion;
import com.sita.android.sitaproject.Models.adminUser;
import com.sita.android.sitaproject.Models.citizeninfo;
import com.sita.android.sitaproject.R;
import com.sita.android.sitaproject.Fragments.homefragment;
import com.sita.android.sitaproject.userinfofrag;
import com.sita.android.sitaproject.Fragments.userinfofragment;

import java.util.ArrayList;
import java.util.List;


import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;




    public class adminHomepage extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {

        private DrawerLayout drawerLayout;
        private ActionBarDrawerToggle drawerToggle;
        private FloatingActionButton Fab;

        private homefragment hf;
        private ViewAnimator viewAnimator;
        private LinearLayout linearLayout;
        Toolbar toolbar;
        TextView mtitle;
        List<SlideMenuItem> list;
        userinfofragment infofragment;
        FragmentManager fm=getSupportFragmentManager();
        int decidePos = 0;

        int flag = 1;
        ArrayList<adminUser> admininfoArrayList = new ArrayList<>();
        TextView NewRegistration,FingerprintAuth,BarcodeAuth,PincodeAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_adminhomepage);
            admininfoArrayList = getIntent().getParcelableArrayListExtra("info");
            storeLoginCredentialInSharedPref(admininfoArrayList.get(0).getUserName(),admininfoArrayList.get(0).getPassWord());
           list = new ArrayList<>();



               hf = homefragment.newInstance();
               getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, hf)
                       .commit();

            drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
            drawerLayout.setScrimColor(Color.TRANSPARENT);
            linearLayout=(LinearLayout)findViewById(R.id.left_drawer);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.closeDrawers();
                }
            });

            setActionBar();
            createMenuList();

            viewAnimator = new ViewAnimator<>(adminHomepage.this,list,hf,drawerLayout,this);

        }

        private void createMenuList() {

            SlideMenuItem menuItem0= new SlideMenuItem("Close",R.drawable.colorsmallcanceldrawericon);

            SlideMenuItem menuItem1= new SlideMenuItem("home",R.drawable.newnewhomedrawer);

           // SlideMenuItem menuItem3= new SlideMenuItem("history",R.drawable.newcolorednewtruehistory);

            SlideMenuItem menuItem4= new SlideMenuItem("FAQ",R.drawable.newnewinfo);
//

            //    SlideMenuItem menuItem6= new SlideMenuItem("Close",R.drawable.bighome);
            //  SlideMenuItem menuItem7= new SlideMenuItem("Close",R.drawable.bighome);


            //list.add(menuItem0);
            //list.add(menuItem1);





            //list.add(menuItem3);

            //list.add(menuItem4);
            ;
            //list.add(menuItem5);
            //list.add(menuItem6);
            //list.add(menuItem7);


        }


        private void setActionBar(){

            toolbar= (Toolbar)findViewById(R.id.toolbar);


            mtitle=(TextView)toolbar.findViewById(R.id.toolbarTitle);
            Typeface customfont= Typeface.createFromAsset(getAssets(),"Anteb-SemiLight.otf");
            mtitle.setTypeface(customfont);
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            getSupportActionBar().setTitle("");

            drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                    R.string.Login_button_text,R.string.app_name){
                @Override
                public void onDrawerClosed(View view){
                    super.onDrawerClosed(view);
                    linearLayout.removeAllViews();
                    linearLayout.invalidate();


                }

                @Override
                public void onDrawerSlide(View drawerView,float slideOffset){

                    super.onDrawerSlide(drawerView,slideOffset);
                    if(slideOffset>0.6){

                        if(linearLayout.getChildCount()==0){

                            viewAnimator.showMenuContent();
                        }

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

                Intent intent = new Intent(adminHomepage.this,LoginPage.class);
                removeLoginCredentialFromSharedpref();
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



                case "home":

                    if(flag == 1){
                        userinfofrag fr1= userinfofrag.newInstance();
                        return fr1;
                    }

                    else {
                        flag = 1;
                        mtitle.setText("Home");
                        //return replacetoHomefragment(screenShotable, position);
                    }


            //    case "history":

              //      if(flag == 3){

                //        userinfofrag fr3= userinfofrag.newInstance();
                  //      return fr3;
                    //}

                    //else {
                      //  flag = 3;
                        //mtitle.setText("History");
                        //return replaceToHistoryFragment(screenShotable, position);
                    //}
                case "FAQ":

                    if(flag == 4){
                        userinfofrag fr4= userinfofrag.newInstance();
                        return fr4;
                    }

                    else {
                        flag = 4;
                        mtitle.setText("FAQ");

                    }
            }


            return null;
        }

        private ScreenShotable replaceToHistoryFragment(ScreenShotable screenShotable,int position){
          /*  View view =findViewById(R.id.content_frame);
            int radius= Math.max(view.getWidth(),view.getHeight());
            SupportAnimator animator= ViewAnimationUtils.createCircularReveal(view,0,position,0,radius);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
*/
           // findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(),screenShotable.getBitmap()));
           //animator.start();

            History history= History.newInstance();
            fm.beginTransaction().replace(R.id.content_frame,history).commit();

            return history;
            }




/*                private ScreenShotable replacetoHomefragment(ScreenShotable screenShotable, int position){

                    View view =findViewById(R.id.content_frame);
                    int radius= Math.max(view.getWidth(),view.getHeight());
                    SupportAnimator animator= ViewAnimationUtils.createCircularReveal(view,0,position,0,radius);
                    animator.setInterpolator(new AccelerateInterpolator());
                    animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

                    //findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(),screenShotable.getBitmap()));
                    //animator.start();

                    homefragment hf= homefragment.newInstance();
                    fm.beginTransaction().replace(R.id.content_frame,hf).commit();

                    return hf;

                }*/

/*                private ScreenShotable replacetoFaq(ScreenShotable screenShotable, int position){


                    View view =findViewById(R.id.content_frame);
                    int radius= Math.max(view.getWidth(),view.getHeight());
                    SupportAnimator animator= ViewAnimationUtils.createCircularReveal(view,0,position,0,radius);
                    animator.setInterpolator(new AccelerateInterpolator());
                    animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

                    //findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(),screenShotable.getBitmap()));
                   // animator.start();
                    FrequenlyAskedQuestion frequenlyAskedQuestion= FrequenlyAskedQuestion.newInstance();
                    fm.beginTransaction().replace(R.id.content_frame,frequenlyAskedQuestion).commit();
                    return frequenlyAskedQuestion;
                }*/


        //private



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
        private void storeLoginCredentialInSharedPref(String username,String password){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(adminHomepage.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("username",username);
            editor.putString("password",password);
            editor.commit();
        }
        private void removeLoginCredentialFromSharedpref(){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(adminHomepage.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username");
            editor.remove("password");
            editor.commit();
        }

    }
