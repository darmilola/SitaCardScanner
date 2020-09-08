package com.sita.android.sitaproject.Fragments;



import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;


import com.google.zxing.client.android.CaptureActivity;
import com.sita.android.sitaproject.Activities.Registration;
import com.sita.android.sitaproject.Models.citizeninfo;
import com.sita.android.sitaproject.R;
import com.sita.android.sitaproject.Activities.admin_userinformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import android.support.v7.app.AlertDialog;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class homefragment extends Fragment implements ScreenShotable {

    private static final String SCANNER = "com.google.zxing.client.android.SCAN";
    private static final String SCAN_FORMATS = "UPC_A,UPC_E,EAN_8,EAN_13,CODE_39,CODE_93,CODE_128,ITF,Codabar,QR";
    private static final String SCAN_MODE = "ONE_D_MODE";
    private static final int REQUEST_CODE = 1;
    private static final String SearchURL = "http://odsgcard.com.ng/android_project/adminsearchcitizen.php";


    private  View containerview;
    ScrollView mscrollView;
    Bitmap bitmap;
    String pinnummber;
    TextView NewRegistration,FingerprintAuth,BarcodeAuth,PincodeAuth;
    AlertDialog b,c;

    public homefragment() {
        // Required empty public constructor
    }

    public static homefragment newInstance(){

        homefragment fragment= new homefragment();
        return fragment;
    }


    @Override
    public void onViewCreated(View view,  Bundle savedinstancestate){
        super.onViewCreated(view,savedinstancestate);
        this.containerview=view.findViewById(R.id.container);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



                View v=inflater.inflate(R.layout.fragment_adminhomefragment, container, false);

                mscrollView=v.findViewById(R.id.homescroll);
                NewRegistration=v.findViewById(R.id.newregisterationtext);
                FingerprintAuth=v.findViewById(R.id.fingerprintauth);
                BarcodeAuth=v.findViewById(R.id.barcodeauthtext);
                PincodeAuth=v.findViewById(R.id.pincodtextview);


        Typeface customfont= Typeface.createFromAsset(getActivity().getAssets(),"Anteb-SemiLight.otf");
                NewRegistration.setTypeface(customfont);
                FingerprintAuth.setTypeface(customfont);
                BarcodeAuth.setTypeface(customfont);
                PincodeAuth.setTypeface(customfont);



        FingerprintAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displaydialog("coming soon");
            }
        });
        NewRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(),Registration.class);

                startActivity(intent);
            }
        });


        BarcodeAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CaptureActivity.class);
                intent.setAction(SCANNER);
                intent.putExtra("SCAN_FORMATS",SCAN_FORMATS);
                intent.putExtra("SAVE_HISTORY",true);
                //intent.putExtra("SCAN_MODE",SCAN_MODE);

                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        PincodeAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View DialogView = inflater.inflate(R.layout.pinnumbersearchdialog,null);
                alertdialog.setView(DialogView);
                alertdialog.setCancelable(true);
                c = alertdialog.create();
                c = alertdialog.show();
                TextView title = DialogView.findViewById(R.id.pinnumbersearchdialogtitle);
                Button search = DialogView.findViewById(R.id.pinnumbersearchdialogbutton);
                EditText pin = DialogView.findViewById(R.id.pinnumbersearchedittext);
                pin.setSingleLine(true);

                Typeface customfont = Typeface.createFromAsset(getActivity().getAssets(), "Anteb-SemiLight.otf");
                search.setTypeface(customfont);
                title.setTypeface(customfont);
                pin.setTypeface(customfont);

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String searchpin = pin.getText().toString();

                        if (searchpin.equalsIgnoreCase("")) {
                             displaydialog("Please enter the ORIN");
                        } else {
                            pinnummber = searchpin;
                            c.dismiss();
                            CitizenPinnumberSearch citizenPinnumberSearch = new CitizenPinnumberSearch();
                            citizenPinnumberSearch.execute();
                        }
                    }
                });



            }
        });

        return v;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == REQUEST_CODE){



            if(resultCode == RESULT_OK){

                pinnummber = data.getStringExtra("SCAN_RESULT");

               CitizenPinnumberSearch citizenPinnumberSearch = new CitizenPinnumberSearch();
               citizenPinnumberSearch.execute();


            }

            else if(resultCode == RESULT_CANCELED){
                Log.e("canceled", "onActivityResult: ");

            }
        }

    }

    class CitizenPinnumberSearch extends AsyncTask {
        String prompt;


        protected void onPreExecute() {
            super.onPreExecute();

            AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View DialogView = inflater.inflate(R.layout.citizenauthdialog,null);
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

            String jsonString = new citizenSearchJsonParser().SelectData(SearchURL);

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");



                    if (status.equalsIgnoreCase("wrongpin")) {
                        prompt = "Wrong Pin Number";
                        return prompt;
                    }

                    if (status.equalsIgnoreCase("success")) {

                        JSONArray jsonArray = info.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            JSONObject j = jsonArray.getJSONObject(i);
                            String orin = j.getString("pin_number");
                            String formnumber = j.getString("form_number");
                            String surname = j.getString("sur_name");
                            String othername = j.getString("other_name");
                            String firstname = j.getString("first_name");
                            String gender = j.getString("gender");
                            String address = j.getString("residential_address");
                            String residentiallga = j.getString("residential_lga_name");
                            String phonenumber = j.getString("phone_number1");
                            String occupation = j.getString("job_details");
                            String picturepath = j.getString("profilepicture");
                            String maritalstatus = j.getString("marital_status");
                            String lga_id = j.getString("residential_lga_id");
                            String occupation_id = j.getString("occupation_id");
                            citizeninfo citizeninfo = new citizeninfo(formnumber, firstname, surname, othername, residentiallga, gender, phonenumber, maritalstatus, occupation, picturepath,orin,address);
                            citizeninfo.setLga_id(lga_id);
                            citizeninfo.setOccupationid(occupation_id);
                            return citizeninfo;


                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(Object result) {


            if (result == null) {
                b.dismiss();
                //Toast.makeText(getContext(), "Unable to connect Please try Again", Toast.LENGTH_LONG).show();
              displaydialog("Unable to connect Please try Again");
            }

            if (result != null) {
                if (result instanceof citizeninfo) {
                    b.dismiss();


                    ArrayList<citizeninfo> info = new ArrayList<>();
                    info.add((citizeninfo) result);
                    Intent intent = new Intent(getContext(),admin_userinformation.class);
                    intent.putParcelableArrayListExtra("info",info);
                    startActivity(intent);
                }


                if (result.toString().equalsIgnoreCase(prompt)) {
                    b.dismiss();
                    //Toast.makeText(getContext(), prompt, Toast.LENGTH_LONG)
                      //      .show();
                        displaydialog(prompt);

                }
            }
        }

    }
        private Boolean isNetworkAvailable() {

            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        public class citizenSearchJsonParser {

            public String SelectData(String url) {

                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(50, TimeUnit.SECONDS)
                            .writeTimeout(50, TimeUnit.SECONDS)
                            .readTimeout(50, TimeUnit.SECONDS)
                            .build();

                    RequestBody formBody = new FormBody.Builder()
                            .add("orin", pinnummber)
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

        @Override
        public void takeScreenShot() {

            Thread thread = new Thread() {
                @Override
                public void run() {

                    WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
                    final DisplayMetrics displayMetrics = new DisplayMetrics();
                    manager.getDefaultDisplay().getMetrics(displayMetrics);
                    int height = displayMetrics.heightPixels;
                    int width = displayMetrics.widthPixels;
                    bitmap = Bitmap.createBitmap(width, height
                            , Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    // containerview.draw(canvas);
                    // Main2Fragment.this.bitmap=bitmap;
                }

            };
            thread.start();


        }


        @Override
        public Bitmap getBitmap() {
            return bitmap;
        }
    private void displaydialog(String message){
        android.support.v7.app.AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder = new android.support.v7.app.AlertDialog.Builder(getContext(),android.R.style.Theme_Material_Dialog_Alert);
        }
        else{
            builder = new android.support.v7.app.AlertDialog.Builder(getContext());
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
