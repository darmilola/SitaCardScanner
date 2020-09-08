package com.sita.android.sitaproject.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sita.android.sitaproject.Fragments.secondpage;
import com.sita.android.sitaproject.Models.MyAppGlideModule;
import com.sita.android.sitaproject.Models.OccupationRecyclerViewAdapter;
import com.sita.android.sitaproject.Models.citizeninfo;
import com.sita.android.sitaproject.Models.localgovernmentadapter;
import com.sita.android.sitaproject.Models.localgovernmentmodel;
import com.sita.android.sitaproject.Models.occupations;
import com.sita.android.sitaproject.R;
//import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class citizenProfileInfoActivity extends AppCompatActivity {

    TextView formnumbertitle, edit, surnametitle,firstnametitle,othernametitle,occupationtitle,phonetitle,lgatitle,gendertitle,maritaltitle,orintitle,addresstitle;
    EditText formnumber, surname,firstname,othername,occupation,phone,lga,gender,marital,orin,address;
    ImageView profileimage;
    Toolbar profileinfotoolbar;

    String mformnumber, msurname,mfirstname,mothername,moccupation,mphone,mlga,mgender,mmarital,morin,maddress;
    String  occupationid,lgaid;
    AlertDialog DialogHandler;
    String prompt, occupation_id,localgovernment_id;
    ArrayList<occupations> occupationsArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    OccupationRecyclerViewAdapter occupationRecyclerViewAdapter;
    private static final String OccupationURL = "http://odsgcard.com.ng/OccupationGetter.php";
    private static final String LocalGovernmentURL = "http://odsgcard.com.ng/localgovernmentgetter.php";
    AlertDialog b,c;
    citizeninfo citizeninfo;
    ArrayList<citizeninfo>  citizeninfos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_citizen_profile_info);
        InitializeView();
        setOnclicklistener();
        if(getIntent().getStringExtra("citizen") != null){
            edit.setVisibility(View.GONE);
        }


        citizeninfos = getIntent().getParcelableArrayListExtra("info");
        citizeninfo = citizeninfos.get(0);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.uploadimageicon);
        requestOptions.error(R.drawable.uploadimageicon);
        Glide.with(getApplicationContext())
                .setDefaultRequestOptions(requestOptions)
                .load(citizeninfo.getPicturepath())
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(profileimage);
        surname.setText(citizeninfo.getSurname());
        firstname.setText(citizeninfo.getFirstname());
        othername.setText(citizeninfo.getOthername());
        occupation.setText(citizeninfo.getOccupation());
        gender.setText(citizeninfo.getGender());
        marital.setText(citizeninfo.getMaritalstatus());
        phone.setText(citizeninfo.getPhonenumber());
        lga.setText(citizeninfo.getResidentiallga());
        orin.setText(citizeninfo.getOrin());
        address.setText(citizeninfo.getAddress());
        formnumber.setText(citizeninfo.getFormnumber());
        lgaid = citizeninfo.getLga_id();
        occupationid = citizeninfo.getOccupationid();




    }

    private void setOnclicklistener(){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getText().toString().equalsIgnoreCase("Edit")){
                    edit.setText("Apply");

                    firstname.setEnabled(true);
                    surname.setEnabled(true);
                    othername.setEnabled(true);
                    lga.setEnabled(true);
                    gender.setEnabled(true);
                    phone.setEnabled(true);
                    marital.setEnabled(true);
                    occupation.setEnabled(true);
                    orin.setEnabled(false);
                    address.setEnabled(true);
                    formnumber.setEnabled(true);
                }
                else{
                    edit.setText("Edit");
                    firstname.setEnabled(false);
                    surname.setEnabled(false);
                    othername.setEnabled(false);
                    lga.setEnabled(false);
                    gender.setEnabled(false);
                    phone.setEnabled(false);
                    marital.setEnabled(false);
                    occupation.setEnabled(false);
                    orin.setEnabled(false);
                    address.setEnabled(false);
                    formnumber.setEnabled(false);

                    mfirstname = firstname.getText().toString();
                    msurname = surname.getText().toString();
                    mothername = othername.getText().toString();
                    mlga = lga.getText().toString();
                    mgender = gender.getText().toString();
                    mphone = phone.getText().toString();
                    mmarital = marital.getText().toString();
                    moccupation = occupation.getText().toString();
                    morin = orin.getText().toString();
                    maddress = address.getText().toString();
                    mformnumber = formnumber.getText().toString();
                    edittask edittask = new edittask();
                    edittask.execute();

                }
            }
        });

        lga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    localgovernmentGetter localgovernmentGetter = new localgovernmentGetter();
                    localgovernmentGetter.execute();

            }
        });

        occupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    OccupationGetter occupationGetter = new OccupationGetter();
                    occupationGetter.execute();

            }
        });
    }
    private void InitializeView() {
        surnametitle = findViewById(R.id.profilesurnametitle);
        surname = findViewById(R.id.profilesurname);
        firstname = findViewById(R.id.profilefirstname);
        firstnametitle = findViewById(R.id.profilefirstnametitle);
        othername = findViewById(R.id.profileothername);
        othernametitle = findViewById(R.id.profileothernametitle);
        occupation = findViewById(R.id.profileoccupation);
        occupationtitle = findViewById(R.id.profileoccupationtitle);
        phone = findViewById(R.id.profilephonenumber);
        formnumber = findViewById(R.id.profileformnumber);
        formnumbertitle = findViewById(R.id.profileformnumbertitle);
        phonetitle = findViewById(R.id.profilephonenumbertitle);
        lga = findViewById(R.id.profileLGA);
        lgatitle = findViewById(R.id.profileLGAtitle);
        gender = findViewById(R.id.profilegender);
        gendertitle = findViewById(R.id.profilegendertitle);
        marital = findViewById(R.id.profilemaritalstatus);
        maritaltitle = findViewById(R.id.profilemaritalstatustitle);
        profileimage = findViewById(R.id.profileimage);
        orintitle = findViewById(R.id.orintitle);
        orin = findViewById(R.id.orinvalue);
        address = findViewById(R.id.addressvalue);
        addresstitle = findViewById(R.id.addresstitle);
        profileinfotoolbar = findViewById(R.id.profileinformationtoolbar);
        edit = findViewById(R.id.profileinformationedit);
        firstname.setEnabled(false);
        surname.setEnabled(false);
        othername.setEnabled(false);
        lga.setEnabled(false);
        gender.setEnabled(false);
        phone.setEnabled(false);
        marital.setEnabled(false);
        occupation.setEnabled(false);
        orin.setEnabled(false);
        address.setEnabled(false);
        formnumber.setEnabled(false);
        occupation.setFocusable(false);
        lga.setFocusable(false);
        setSupportActionBar(profileinfotoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        profileinfotoolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

    }

    public class edittask extends AsyncTask{
         String prompt;
        String url = "http://odsgcard.com.ng/android_project/editcitizeninfo.php";
        @Override
        protected void onPreExecute(){
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(citizenProfileInfoActivity.this);
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View DialogView = inflater.inflate(R.layout.editprogressdialog,null);
            alertdialog.setView(DialogView);
            alertdialog.setCancelable(false);
            b = alertdialog.create();
            b = alertdialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            if(!isNetworkAvailable()){
                prompt = "No Network Connection";
                return prompt;
            }

            String jsonString = new editJsonParser().SelectData(url,mformnumber, msurname, mothername, mfirstname, mgender,lgaid,mphone,occupationid,mmarital,morin,maddress);

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");


                    if (status.equalsIgnoreCase("editsuccessful")) {
                        prompt = "successful";
                        return prompt;
                    }
                }catch (JSONException E){
                }
            }

                    return null;
        }
        protected void onPostExecute(Object result) {
             b.dismiss();
            //Toast.makeText(citizenProfileInfoActivity.this, result.toString(), Toast.LENGTH_LONG).show();
            if(result == null){
                displaydialog("Cannot Edit please try again");
            }
            else{
                if(prompt.equalsIgnoreCase("successful")){
                    displaydialog("Edit Successful");
                }
                else if(prompt.equalsIgnoreCase("No Network Connection")){
                    displaydialog("No Network Connection");
                }


            }
        }


    }

    public class editJsonParser {

        public String SelectData(String url,String formnumber, String msurname, String otherName, String firstname, String Gender, String LocalGovernmentid,
                                 String PhoneNumber, String Occupationid, String maritalstatus,String orin,String address) {

            try {


                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("orin", orin)
                        .add("surname", msurname)
                        .add("othername", otherName)
                        .add("firstname", firstname)
                        .add("gender", Gender)
                        .add("residentiallgaid", LocalGovernmentid)
                        .add("phonenumber", PhoneNumber)
                        .add("occupationid", Occupationid)
                        .add("residentialaddress",address)
                        .add("maritalstatus", maritalstatus)
                        .add("formnumber",formnumber)


                        .build();

                Request request = new Request.Builder().url(url).post(formBody).build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                // JsonObject jsonObject =new JsonObject(data);
                //return JsonObject;
                return data;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public String getStringImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] imagebytes = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imagebytes, Base64.DEFAULT);
        return encodedImage;
    }



    private Boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null && activeNetworkInfo.isConnected();
    }
    private void displaydialog(String message){
        AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog_Alert);
        }
        else{
            builder = new AlertDialog.Builder(this);
        }
        builder.setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){

        if(menuItem.getItemId() == android.R.id.home){

           super.onBackPressed();

        }


        return super.onOptionsItemSelected(menuItem);
    }

    public class OccupationGetter extends AsyncTask {
        String prompt;

        @Override
        protected void onPreExecute() {

            //Toast.makeText(getContext(), "Fetching Occupations Please Wait", Toast.LENGTH_LONG).show();
            //displaydialog("Fetching Occupations Please Wait...");
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(citizenProfileInfoActivity.this);
            LayoutInflater inflater = LayoutInflater.from(citizenProfileInfoActivity.this);
            View DialogView = inflater.inflate(R.layout.loadingoccupationdialog,null);
            alertdialog.setView(DialogView);
            alertdialog.setCancelable(false);
            c = alertdialog.create();
            c = alertdialog.show();
        }


        @Override
        protected Object doInBackground(Object[] objects) {

            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }

            String jsonString = new OccupationJsonParser().SelectData(OccupationURL);

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if (status.equalsIgnoreCase("Error")) {

                        prompt = "Error in connection";
                    }
                    if (status.equalsIgnoreCase("Successful")) {

                        JSONArray jsonArray = info.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); ++i) {

                            JSONObject j = jsonArray.getJSONObject(i);

                            String occupation = j.getString("occupation_name");
                            String id = j.getString("id");
                            occupations occupations = new occupations(occupation, id);

                            occupationsArrayList.add(occupations);
                        }

                        return occupationsArrayList;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }


        protected void onPostExecute(Object result) {

            c.dismiss();

            if (result == null) {
                //Toast.makeText(getContext(), "Unable to fetch occupations Please try Again", Toast.LENGTH_LONG).show();
                //Occupation.setEnabled(true);
                // DialogHandler.dismiss();
                displaydialog("Unable to fetch occupations Please try Again");
            }
            if (result != null) {
                if (result instanceof ArrayList) {
                    // Toast.makeText(getContext(),result.toString(),Toast.LENGTH_LONG).show();
                    occupationsArrayList = (ArrayList<occupations>) result;


                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(citizenProfileInfoActivity.this);
                    LayoutInflater layoutInflater = LayoutInflater.from(citizenProfileInfoActivity.this);
                    final View occupationsdialog = layoutInflater.inflate(R.layout.occupationdialog, null);
                    alertdialog.setView(occupationsdialog);
                    alertdialog.setCancelable(false);
                    DialogHandler = alertdialog.create();
                    DialogHandler = alertdialog.show();


                    occupationRecyclerViewAdapter = new OccupationRecyclerViewAdapter(getApplicationContext(), occupationsArrayList, clickedItemIndex -> {

                        occupations current = occupationsArrayList.get(clickedItemIndex);
                        occupation_id = current.getId();
                        occupation.setText(current.getOccupation());
                       occupationid = occupation_id;
                        DialogHandler.dismiss();


                    });
                    recyclerView = (RecyclerView) occupationsdialog.findViewById(R.id.occupationdialogrecyclerview);
                    recyclerView.setAdapter(occupationRecyclerViewAdapter);
                    RecyclerView.LayoutManager mLayoutManager =
                            new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setHasFixedSize(true);


                }
                if (result.toString().equalsIgnoreCase(prompt)) {

                    //Toast.makeText(getContext(), prompt, Toast.LENGTH_LONG)
                    //      .show();
                    displaydialog(prompt);

                }
            }

        }
    }
    public class localgovernmentGetter extends AsyncTask {
        ArrayList<localgovernmentmodel> localgovernmentmodelArrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {

            AlertDialog.Builder alertdialog = new AlertDialog.Builder(citizenProfileInfoActivity.this);
            LayoutInflater inflater = LayoutInflater.from(citizenProfileInfoActivity.this);
            View DialogView = inflater.inflate(R.layout.loadinglocalgovernmentdialog,null);
            alertdialog.setView(DialogView);
            alertdialog.setCancelable(false);
            b = alertdialog.create();
            b = alertdialog.show();
        }


        @Override
        protected Object doInBackground(Object[] objects) {

            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String jsonString = new localgovernmentJsonParser().SelectData(LocalGovernmentURL);
            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if (status.equalsIgnoreCase("Error")) {

                        prompt = "Error in connection";
                    }
                    if (status.equalsIgnoreCase("Successful")) {

                        JSONArray jsonArray = info.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); ++i) {

                            JSONObject j = jsonArray.getJSONObject(i);

                            String name = j.getString("lga_name");
                            String id = j.getString("lga_id");
                            localgovernmentmodel localgovernmentmodel = new localgovernmentmodel(name, id);

                            localgovernmentmodelArrayList.add(localgovernmentmodel);

                        }

                        return localgovernmentmodelArrayList;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return jsonString;
        }

        protected void onPostExecute(Object result) {

            b.dismiss();
            if (result == null) {
                // Toast.makeText(getContext(), "Unable to fetch local governments Please try Again", Toast.LENGTH_LONG).show();
                displaydialog("Unable to fetch local governments Please try Again");
                // DialogHandler.dismiss();
            }
            if (result != null) {
                if (result instanceof ArrayList) {
                    // Toast.makeText(getContext(),result.toString(),Toast.LENGTH_LONG).show();
                    ArrayList<localgovernmentmodel> localgovernmentmodels = (ArrayList<localgovernmentmodel>) result;

                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(citizenProfileInfoActivity.this);
                    LayoutInflater layoutInflater = LayoutInflater.from(citizenProfileInfoActivity.this);
                    final View localgovernmentdialog = layoutInflater.inflate(R.layout.localgovernmentdialog, null);
                    alertdialog.setView(localgovernmentdialog);
                    alertdialog.setCancelable(true);
                    DialogHandler = alertdialog.create();
                    DialogHandler = alertdialog.show();


                    localgovernmentadapter adapter  = new localgovernmentadapter(localgovernmentmodels,getApplicationContext(),(int clickedItemIndex) -> {

                        localgovernmentmodel current = localgovernmentmodels.get(clickedItemIndex);
                        localgovernment_id = current.getId();
                        lga.setText(current.getLocalGovernmentName());
                        lgaid = localgovernment_id;
                        DialogHandler.dismiss();


                    });
                    recyclerView = (RecyclerView)localgovernmentdialog.findViewById(R.id.localgovernmentdialogrecyclerview);
                    recyclerView.setAdapter(adapter);
                    RecyclerView.LayoutManager mLayoutManager =
                            new LinearLayoutManager(citizenProfileInfoActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setHasFixedSize(true);


                }
                if (result.toString().equalsIgnoreCase(prompt)) {

                    //Toast.makeText(getContext(), prompt, Toast.LENGTH_LONG)
                    //.show();
                    displaydialog(prompt);


                }
            }
        }

    }

    public class OccupationJsonParser {

        public String SelectData(String url) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                // RequestBody formBody = new FormBody.Builder().build();

                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                // JsonObject jsonObject =new JsonObject(data);
                //return JsonObject;
                return data;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public class localgovernmentJsonParser {

        public String SelectData(String url) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                // RequestBody formBody = new FormBody.Builder().build();

                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                // JsonObject jsonObject =new JsonObject(data);
                //return JsonObject;
                return data;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }


}
