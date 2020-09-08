package com.sita.android.sitaproject.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.sita.android.sitaproject.Models.citizeninfo;
import com.sita.android.sitaproject.R;
import com.sita.android.sitaproject.citizenSocialBenefit;

import java.util.ArrayList;

public class admin_userinformation extends AppCompatActivity {
    TextView socialbenefit,taxinfo,healthinfo,profileinfo,toolbartitle;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_userinformation);
        ArrayList<citizeninfo> citizeninfoArrayList = getIntent().getParcelableArrayListExtra("info");
        toolbartitle = findViewById(R.id.citizeninformationtoolbarTitle);
        socialbenefit= findViewById(R.id.adminnewsocialbenefittext);
        taxinfo= findViewById(R.id.admintaxinfotext);
        healthinfo= findViewById(R.id.adminhealthinfotext);
        profileinfo=findViewById(R.id.adminprofiletextview);
        toolbar = findViewById(R.id.citizeninfotoolbar);
        setSupportActionBar(toolbar);

        Typeface customfont= Typeface.createFromAsset(getAssets(),"Anteb-SemiLight.otf");
        socialbenefit.setTypeface(customfont);
        taxinfo.setTypeface(customfont);
        healthinfo.setTypeface(customfont);
        profileinfo.setTypeface(customfont);
        toolbartitle.setTypeface(customfont);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.White_color), PorterDuff.Mode.SRC_ATOP);

        profileinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_userinformation.this, citizenProfileInfoActivity.class);

                if(citizeninfoArrayList != null) {
                    intent.putParcelableArrayListExtra("info",citizeninfoArrayList);

                }
                startActivity(intent);
            }
        });

        socialbenefit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displaydialog("Coming soon");
                //Intent intent = new Intent(admin_userinformation.this,citizenSocialBenefit.class);
                //startActivity(intent);
            }
        });

        healthinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaydialog("Coming soon");
            }
        });

        taxinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaydialog("coming soon");
            }
        });




    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        if(menuItem.getItemId() == android.R.id.home){

            Intent intent = new Intent(admin_userinformation.this,adminHomepage.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public void onBackPressed(){


    }
    private void displaydialog(String message){
        android.support.v7.app.AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder = new android.support.v7.app.AlertDialog.Builder(admin_userinformation.this,android.R.style.Theme_Material_Dialog_Alert);
        }
        else{
            builder = new android.support.v7.app.AlertDialog.Builder(admin_userinformation.this);
        }
        builder.setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }



}
