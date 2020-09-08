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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sita.android.sitaproject.Activities.adminHomepage;
import com.sita.android.sitaproject.Activities.LoginPage;
import com.sita.android.sitaproject.R;
import com.sita.android.sitaproject.Models.adminUser;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminLogin extends Fragment{

    private  static final String LoginURL = "http://sita.epizy.com/AdminLogin.php";
    View v;
    Button Login;
    TextView forgotpass;
    Typeface customfont;
    EditText username;
    EditText password;
    String AdminUserName;
    String AdminPassword;
    AlertDialog b;

    String status = null;
    String prompt;

    public AdminLogin() {
        // Required empty public constructor
    }

    public static AdminLogin newInstance(){
        AdminLogin adminLogin = new AdminLogin();
        return adminLogin;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


            v = inflater.inflate(R.layout.fragment_admin_login, container, false);
            InitilalizeView();
            Login.setTypeface(customfont);

            username.setTypeface(customfont);
            password.setTypeface(customfont);
            username.setSingleLine(true);
            password.setSingleLine(true);



                Login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (username.getText().toString().trim().equalsIgnoreCase("") || password.getText().toString().trim().equalsIgnoreCase("")) {
                            //Toast.makeText(getContext(), "Please Input Username or Password", Toast.LENGTH_SHORT).show();
                           displaydialog("Please Input Username or Password");
                        } else {

                            AdminUserName = username.getText().toString();
                            AdminPassword = password.getText().toString();

                            Toast.makeText(getContext(),AdminUserName+" "+AdminPassword,Toast.LENGTH_LONG).show();
                            adminLoginHandler handler = new adminLoginHandler();
                            handler.execute();
                        }
                    }
                });



                return v;
    }


    public class adminLoginHandler extends AsyncTask {

        String resp;
        String data;

        protected void onPreExecute() {
            super.onPreExecute();


            AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View DialogView = inflater.inflate(R.layout.logindialog, null);
            alertdialog.setView(DialogView);
            alertdialog.setCancelable(false);
            b = alertdialog.create();
            b = alertdialog.show();


            LoginPage.setModeEnable(false);


        }


        @Override
        protected Object doInBackground(Object[] objects) {


            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }


            String jsonString = new AdminLoginJsonParser().SelectData(LoginURL);

            if(jsonString != null){
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");


                    if(status.equalsIgnoreCase("wronguserameorpassword")){
                        prompt = "Wrong Username Or Password";
                        return prompt;
                    }
                    if(status.equalsIgnoreCase("loggedin")){

                        JSONArray jsonArray =info.getJSONArray("data");

                        for (int i =0; i<jsonArray.length(); ++i){
                            JSONObject j = jsonArray.getJSONObject(i);
                            String username = j.getString("user_name");
                            String password = j.getString("pass_word");
                            String adminstatus = j.getString("status");
                            adminUser adminUser = new adminUser(username,password,adminstatus);
                            return adminUser;
                        }

                    }


                }
                catch (JSONException e){

                }

            }
            return null;

        }


        protected void onPostExecute(Object result) {
            b.dismiss();
            LoginPage.setModeEnable(true);
            //Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG).show();

            //Toast.makeText(getContext(),result.toString(),Toast.LENGTH_LONG).show();

            if(result == null){
                //Toast.makeText(getContext(),"Unable to login Please try Again",Toast.LENGTH_LONG).show();
                displaydialog("Unable to login Please try Again");
            }

            if(result != null){

                if(result instanceof adminUser ) {
                    Intent intent = new Intent(getContext(), adminHomepage.class);
                    ArrayList<adminUser> admininfo = new ArrayList<>();
                    admininfo.add((adminUser)result);
                    intent.putParcelableArrayListExtra("info", admininfo);

                    if(admininfo.get(0).getStatus().equalsIgnoreCase("1")){
                        //Toast.makeText(getContext(), "Login Permission Denied", Toast.LENGTH_LONG).show();
                        displaydialog("Login Permission Denied");
                    }
                    else {
                        startActivity(intent);
                    }
                }

                if (result.toString().equalsIgnoreCase(prompt)) {

                    // Toast.makeText(getContext(), prompt, Toast.LENGTH_LONG)
                    //       .show();
                    displaydialog(prompt);

                }
            }

        }


    }

    public class AdminLoginJsonParser{

        public String SelectData(String url){

            try{
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();


                RequestBody formBody = new FormBody.Builder()
                        .add("password", AdminPassword)
                        .add("username",AdminUserName)
                        .build();

                Request request = new Request.Builder().url(url).post(formBody).build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                // JsonObject jsonObject =new JsonObject(data);
                //return JsonObject;
                return data;

            } catch (IOException e) {
                e.printStackTrace();

                System.out.println("Message  "+ e.getMessage());
                System.out.println("Local Message  "+ e.getMessage());
            }
            return null;
        }
    }

    private Boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null && activeNetworkInfo.isConnected();
    }

    private void InitilalizeView(){

         Login=v.findViewById(R.id.signinbutton);

         username = v.findViewById(R.id.username);
         password = v.findViewById(R.id.mypassword);
         customfont= Typeface.createFromAsset(getActivity().getAssets(),"Anteb-SemiLight.otf");

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

}
