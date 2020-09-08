package com.sita.android.sitaproject.Activities;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.sita.android.sitaproject.R;
import com.sita.android.sitaproject.Fragments.Thirdpagefragment;
import com.sita.android.sitaproject.Fragments.firstpage;
import com.sita.android.sitaproject.Fragments.secondpage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Registration extends AppCompatActivity implements firstpage.Onfirstnamepass,firstpage.onOrinPass
,firstpage.onOthernamePass,firstpage.OnFormNumberPass,firstpage.onSurnamePass,secondpage.OnPassGender,
        secondpage.OnPassLocalGovernmentid,secondpage.OnPassMaritalStatus,secondpage.OnPassOccupationid,secondpage.OnPhoneNumberPass,secondpage.OnPassFlag,
secondpage.OnPassLocalGovernmentFlag,secondpage.OnPassMaritalFlag,secondpage.OnPassResidentialAddress,Thirdpagefragment.OnpassProfilePicture {
    static Button nextbutton;
    static String firstname = null;
    static String otherName = null;
    static String surname = null;
    static String formNumber = null;
    int flag = 0;
    static int maritalflag = 0;
    int localGovernmentflag = 0;
    static Bitmap profilepicture = null;
    static String Gender = null;
    static String Occupationid = null;
    static String LocalGovernmentid = null;
    static String MaritalStatus = null;
    static String PhoneNumber = null;
    static String residentialaddress = null;
    static String orin = null;
    ViewPager pager;
    StepperIndicator indicator;
    ImageView backimage;
    Button toolbarbutton;
    TextView registrationTitle;
    static int FlagDetectIfPageOneIsfree = 0;
    static int FlagDetectIfPageTwoIsfree = 0;
    static int FlagDetectIfPageThreeIsfree = 0;
    static int AmInPageThreeFlag = 0;
    AlertDialog b;

    String url = "http://sita.epizy.com/Registercitizen.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        FragmentPagerAdapter adapter;
        InitializeView();


        pager = findViewById(R.id.myvewpager);
        Typeface customfont = Typeface.createFromAsset(getAssets(), "Anteb-SemiLight.otf");
        nextbutton.setTypeface(customfont);
        registrationTitle.setTypeface(customfont);
        pager.setCurrentItem(0);
        adapter = new MypagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(5);
        indicator.setViewPager(pager, true);
        indicator.setStepCount(3);
        // pager.setCurrentItem();


        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nextbutton.getText().toString().equalsIgnoreCase("submit") && nextbutton.isEnabled()) {
                    CitizenRegisterTask citizenRegisterTask = new CitizenRegisterTask();
                    citizenRegisterTask.execute();


                } else {
                    int pos = pager.getCurrentItem();
                    pager.setCurrentItem(pos + 1);
                }
            }
        });

        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = pager.getCurrentItem();

                pager.setCurrentItem(pos - 1);

                if (pos - 1 == -1) {
                    firstname = null;
                    otherName = null;
                    surname = null;
                    formNumber = null;


                    localGovernmentflag = 0;
                    profilepicture = null;
                    Gender = null;
                    Occupationid = null;
                    LocalGovernmentid = null;
                    MaritalStatus = null;
                    PhoneNumber = null;
                    Intent i = new Intent(Registration.this, adminHomepage.class);
                    startActivity(i);

                }


            }
        });


    }


    @Override
    public void passFirstname(String data) {
        Log.e(" Log", data);

        firstname = data;

        AuthFirstPage();
    }

    @Override
    public void passFormNumber(String data) {

        formNumber = data;
        AuthFirstPage();

    }

    @Override
    public void passSurname(String data) {
        surname = data;
        AuthFirstPage();
    }

    @Override
    public void passOthername(String data) {

        otherName = data;
        AuthFirstPage();
    }

    @Override
    public void PassPhoneNumber(String data) {

        PhoneNumber = data;

        AuthSecondPage();
    }

    @Override
    public void passOccupationid(String data) {

        Occupationid = data;

        AuthSecondPage();
    }

    @Override
    public void passLocalGovernmentid(String data) {
            LocalGovernmentid = data;
            AuthSecondPage();

    }

    @Override
    public void passMaritalStatus(String data) {

        if (data == "Marital Status") {
            MaritalStatus = "";
            AuthSecondPage();
        } else {

            MaritalStatus = data;
            AuthSecondPage();
        }
    }

    @Override
    public void passGender(String data) {

        Gender = data;
        AuthSecondPage();
    }
    @Override
    public void passResidentialAddress(String data) {
         residentialaddress = data;
         AuthSecondPage();
    }
    @Override
    public void passFlag(int Data) {
        flag = Data;

    }

    @Override
    public void passMaritalFlag(int data) {
        maritalflag = data;

    }

    @Override
    public void passLocalGovernment(int data) {

        localGovernmentflag = data;
    }

    @Override
    public void passPicture(Bitmap picture) {

        profilepicture = picture;
        AuthThirdPage();
    }

    @Override
    public void passOrin(String data) {
        orin = data;
        AuthFirstPage();
    }


    private class MypagerAdapter extends FragmentPagerAdapter {


        public MypagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:

                    // return secondpage.newInstance();
                    return firstpage.newInstance();

                case 1:

                    return secondpage.newInstance();
                case 2:


                    // nextbutton.setBackgroundResource(R.drawable.buttondesign);
                    return Thirdpagefragment.newInstance();
                //case 3:
                //nextbutton.setEnabled(false);

                //return  FourthPage.newInstance();
                default:
                    return null;

            }


        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public static void AuthFirstPage() {

        Registration.AmInPageThreeFlag = 1;
        if (Registration.AmInPageThreeFlag == 4) {
            Registration.nextbutton.setText("Submit");
        } else {
            Registration.nextbutton.setText("Next");
        }
        if (firstname != null && otherName != null && formNumber != null && surname != null && orin != null) {


            if (firstname.equalsIgnoreCase("") || otherName.equalsIgnoreCase("") || formNumber.equalsIgnoreCase("")
                    || surname.equalsIgnoreCase("")) {
                Log.e("never", "AuthSecondPage: ");
                Registration.nextbutton.setEnabled(false);
                Registration.nextbutton.setBackgroundResource(R.drawable.buttondesign);
                Registration.nextbutton.setText("Next");
            } else {
                Log.e("enter", "AuthFirstPage: ");
                Registration.nextbutton.setBackgroundResource(R.drawable.nextbuttondesign);
                Registration.nextbutton.setEnabled(true);
                Registration.FlagDetectIfPageOneIsfree = 1;
            }
        } else {
//            nextbutton.setBackgroundResource(R.drawable.buttondesign);
            //          nextbutton.setEnabled(false);
            Log.e("enterelse", "AuthFirstPage: ");
        }

    }

    public static void AuthSecondPage() {

        Registration.AmInPageThreeFlag = 2;
        if (Registration.AmInPageThreeFlag == 4) {
            Registration.nextbutton.setText("Submit");
        } else{
            Registration.nextbutton.setText("Next");
        }
        if (Gender != null && PhoneNumber != null && LocalGovernmentid != null && MaritalStatus != null && Occupationid != null && residentialaddress != null &&
              LocalGovernmentid != null  ) {
            Log.e("here1", "AuthSecondPage: ");

            Log.e("status", Integer.toString(maritalflag));

            if (Gender.equalsIgnoreCase("") || PhoneNumber.equalsIgnoreCase("")
                    || MaritalStatus.equalsIgnoreCase("") || LocalGovernmentid.equalsIgnoreCase("")
                    || Occupationid.equalsIgnoreCase("")|| residentialaddress.equalsIgnoreCase("")) {
                Log.e("here2", "AuthSecondPage: ");
                Registration.nextbutton.setEnabled(false);
                Registration.nextbutton.setText("Next");

                Registration.nextbutton.setBackgroundResource(R.drawable.buttondesign);
            } else {
                Registration.nextbutton.setBackgroundResource(R.drawable.nextbuttondesign);
                Registration.nextbutton.setEnabled(true);

                Registration.FlagDetectIfPageTwoIsfree = 2;
                Log.e("here3", "AuthSecondPage: ");

                Log.e("L and m", MaritalStatus + "  " + LocalGovernmentid);
            }

        } else {
            nextbutton.setEnabled(false);

            nextbutton.setBackgroundResource(R.drawable.buttondesign);

        }
    }

    public static void AuthThirdPage() {
        Registration.AmInPageThreeFlag = 4;

        if (Registration.AmInPageThreeFlag == 4) {
            Registration.nextbutton.setText("Submit");
        } else {
            Registration.nextbutton.setText("Next");
        }


        if (profilepicture != null) {
            Log.e("nonempty", "picture");
            Registration.nextbutton.setBackgroundResource(R.drawable.nextbuttondesign);
            Registration.nextbutton.setEnabled(true);
            Registration.FlagDetectIfPageThreeIsfree = 3;

            if (Registration.FlagDetectIfPageTwoIsfree == 2 && Registration.FlagDetectIfPageOneIsfree == 1) {
                Registration.nextbutton.setText("Submit");
            }
        } else {

            Registration.nextbutton.setEnabled(false);
            Registration.nextbutton.setBackgroundResource(R.drawable.buttondesign);
            Registration.nextbutton.setText("Submit");
            Log.e("empty", "picture");

        }


    }

    private void InitializeView() {
        pager = (ViewPager) findViewById(R.id.myvewpager);
        //nextbutton= findViewById(R.id.registrationnextbutton);
        backimage = findViewById(R.id.backimage);
        nextbutton = findViewById(R.id.toolbarbutton);
        registrationTitle = findViewById(R.id.newRegistrationtitle);
        nextbutton.setEnabled(false);

        indicator = findViewById(R.id.stepindicator);
    }

    @Override
    public void onBackPressed() {

    }

    public class RegisterJsonParser {

        public String SelectData(String url, String mformnumber, String msurname, String otherName, String firstname, String Gender, String LocalGovernmentid,
                                 String PhoneNumber, String Occupationid, String image, String maritalstatus,String orin,String address) {

            try {


                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("orin", orin)
                        .add("formnumber", mformnumber)
                        .add("surname", msurname)
                        .add("othername", otherName)
                        .add("firstname", firstname)
                        .add("gender", Gender)
                        .add("residentiallgaid", LocalGovernmentid)
                        .add("phonenumber", PhoneNumber)
                        .add("occupationid", Occupationid)
                        .add("residentialaddress",address)
                        .add("maritalstatus", maritalstatus)
                        .add("image", image)

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

    class CitizenRegisterTask extends AsyncTask {

        String prompt;



        @Override
        protected void onPreExecute(){

            AlertDialog.Builder alertdialog = new AlertDialog.Builder(Registration.this);
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            View DialogView = inflater.inflate(R.layout.progressdialog,null);
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
            String uploadImage = getStringImage(profilepicture);
                String jsonString = new RegisterJsonParser().SelectData(url, formNumber, surname, otherName, firstname, Gender, LocalGovernmentid, PhoneNumber, Occupationid, uploadImage, MaritalStatus,orin,residentialaddress);

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if(status.equalsIgnoreCase("orin has been used")){
                       prompt = "Orin has been used";
                       return prompt;
                    }
                    if(status.equalsIgnoreCase("uploadsuccessful")) {
                        prompt = "uploadsuccessful";
                        return prompt;
                    }

                    if(status.equalsIgnoreCase("error")) {

                        prompt = "Error Occured Please Try Again";

                        return prompt;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            return jsonString;
        }

        protected void onPostExecute(Object result) {
            b.dismiss();
            //Toast.makeText(Registration.this, result.toString(), Toast.LENGTH_LONG).show();
             if(result == null){
                //Toast.makeText(getApplicationContext(),"Unable to connect Please try Again",Toast.LENGTH_LONG).show();
                displaydialog("Unable to connect Please try Again");
            }
            if(result !=null) {

                    if(result.toString().equalsIgnoreCase("uploadsuccessful")){

                        //Toast.makeText(getApplicationContext(),"Citizen Registered Successfully",Toast.LENGTH_LONG).show();
                        //b.dismiss();

                        AlertDialog.Builder builder;
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                            builder = new AlertDialog.Builder(Registration.this,android.R.style.Theme_Material_Dialog_Alert);
                        }
                        else{
                            builder = new AlertDialog.Builder(Registration.this);
                        }
                        builder.setMessage("Citizen Registered Successfully")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        Intent intent = new Intent(Registration.this,adminHomepage.class);
                                        startActivity(intent);
                                    }
                                })
                        .setCancelable(false);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        }

                    else if(result.toString().equalsIgnoreCase("No Network Connection")){
                        //Toast.makeText(getApplicationContext(), "No NetWork Connection", Toast.LENGTH_LONG).show();
                        //b.dismiss();
                        displaydialog("No Network Connection");
                    }
                    else if(result.toString().equalsIgnoreCase("Orin has been used")){
                        //Toast.makeText(Registration.this, "Orin has been used", Toast.LENGTH_SHORT).show();
                        //b.dismiss();
                        displaydialog("Orin has been used");
                    }
                    else{
                         //b.dismiss();
                        //Toast.makeText(getApplicationContext(),"An Error Occured Please Try Again",Toast.LENGTH_LONG).show();
                         displaydialog("An Error Occured Please Try Again");
                    }


            }

        }
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
}