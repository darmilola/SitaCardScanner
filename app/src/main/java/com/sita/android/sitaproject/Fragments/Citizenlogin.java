package com.sita.android.sitaproject.Fragments;


import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sita.android.sitaproject.Activities.LoginPage;
import com.sita.android.sitaproject.Activities.citizenhomepage;
import com.sita.android.sitaproject.Models.citizeninfo;
import com.sita.android.sitaproject.R;
import com.sita.android.sitaproject.Models.userInfo;

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

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Citizenlogin extends Fragment {


    private static final String SCANNER = "com.google.zxing.client.android.SCAN";
    private static final String SCAN_FORMATS = "UPC_A,UPC_E,EAN_8,EAN_13,CODE_39,CODE_93,CODE_128,ITF,Codabar,QR";
    private static final String SCAN_MODE = "ONE_D_MODE";
    private static final int REQUEST_CODE = 1;
    private static final String loginurl = "http://odsgcard.com.ng/android_project/SearchCitizenByPinnumber.php";
    TextView pinnumbertitle;
    Button   citizenlogin;
    TextView or;
    Button   scanbarcode;
    EditText pinnumberInput,phonenumbervalue;
    String pinnumber,phonenumber;
    AlertDialog b;
    String contents;
    View v;
    int flag=0;

    public Citizenlogin() {
        // Required empty public constructor
    }

    public static Citizenlogin newInstance(){

        Citizenlogin citizenlogin= new Citizenlogin();
        return  citizenlogin;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        v  = inflater.inflate(R.layout.fragment_citizenlogin, container, false);
        Typeface customfont= Typeface.createFromAsset(getActivity().getAssets(),"Anteb-SemiLight.otf");
        Typeface customfont2= Typeface.createFromAsset(getActivity().getAssets(),"AntebBold.otf");
        InitializeView();
        pinnumbertitle.setTypeface(customfont2);
        citizenlogin.setTypeface(customfont);
        pinnumberInput.setTypeface(customfont);
        phonenumbervalue.setTypeface(customfont);





        citizenlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pinnumberInput.getText().toString().trim().equalsIgnoreCase("")){
                    //Toast.makeText(getContext(), "Please Input your Pin", Toast.LENGTH_SHORT).show();
                    displaydialog("Please Input your Pin or Phonenumber");
                }
                else {
                    pinnumber = pinnumberInput.getText().toString();
                    phonenumber = phonenumbervalue.getText().toString();
                    citizenLoginHandler Handler = new citizenLoginHandler();
                    Handler.execute();
                }


            }
        });


        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_CODE){



            if(resultCode == RESULT_OK){

                pinnumber = data.getStringExtra("SCAN_RESULT");

                citizenLoginHandler Handler = new citizenLoginHandler();
                Handler.execute();

            }

            else if(resultCode == RESULT_CANCELED){


            }
        }



    }


    public class citizenLoginHandler extends AsyncTask {


        String prompt;
       @Override
       protected void onPreExecute(){
           super.onPreExecute();
           AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());
           LayoutInflater inflater = LayoutInflater.from(getContext());
           View DialogView = inflater.inflate(R.layout.logindialog,null);
           alertdialog.setView(DialogView);
           alertdialog.setCancelable(false);
           b = alertdialog.create();
           b = alertdialog.show();
           citizenlogin.setEnabled(false);

           LoginPage.setModeEnable(false);
       }



        @Override
        protected Object doInBackground(Object[] objects) {



             if(!isNetworkAvailable()){
                  prompt = "No Network Connection";
                  return prompt;
             }



            //Log.e("url", LoginURL[0] );

            String jsonString = new JsonParser().SelectData(loginurl);
           // String s = jsonString.toString();


            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");



                    if (status.equalsIgnoreCase("wrongpin")) {
                        prompt = "Incorrect Orin or Phone number";
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

                            citizeninfo citizeninfo = new citizeninfo(formnumber, firstname, surname, othername, residentiallga, gender, phonenumber, maritalstatus, occupation, picturepath,orin,address);
                            return citizeninfo;


                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return jsonString;
        }


        protected void onPostExecute(Object result) {
          b.dismiss();
           citizenlogin.setEnabled(true);
           LoginPage.setModeEnable(true);


            //Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG).show();
          if(result == null){
              // Toast.makeText(getContext(),"Unable to connect Please try Again",Toast.LENGTH_LONG).show();
           displaydialog("Unable to connect Please try Again");
           }

            if (result != null) {
                if (result instanceof citizeninfo) {
                    b.dismiss();
                    Intent intent = new Intent(getContext(), citizenhomepage.class);
                    ArrayList<citizeninfo> info = new ArrayList<>();
                    info.add((citizeninfo) result);
                    intent.putParcelableArrayListExtra("info", info);
                    intent.putExtra("auth", "auth");
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


    public class JsonParser {

        public String SelectData(String url) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("orin", pinnumber)
                        .add("phone",phonenumber)
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

    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if(!isVisibleToUser){

        }
        if(isVisibleToUser){

        }

    }

    private Boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null && activeNetworkInfo.isConnected();
    }

    private void displaydialog(String message){
        AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(getContext(),android.R.style.Theme_Material_Dialog_Alert);
        }
        else{
            builder = new AlertDialog.Builder(getContext());
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

 public void onResume() {



    super.onResume();
}

    private void InitializeView(){
        pinnumbertitle= v.findViewById(R.id.pinnumbertitle);
        citizenlogin= v.findViewById(R.id.citizenlogin);

        pinnumberInput = v.findViewById(R.id.userpin);
        phonenumbervalue = v.findViewById(R.id.userphonenumber);


    }




}
