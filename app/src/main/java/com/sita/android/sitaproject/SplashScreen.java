package com.sita.android.sitaproject;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sita.android.sitaproject.Activities.LoginPage;
import com.sita.android.sitaproject.Activities.adminHomepage;
import com.sita.android.sitaproject.Fragments.AdminLogin;
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

public class SplashScreen extends AppCompatActivity {

    CountDownTimer countDownTimer = new CountDownTimer(5000,1000);
    Intent intent;
    ProgressBar splashprogress;
    String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        splashprogress = findViewById(R.id.splashprogressbar);
        ObjectAnimator progressanimator = ObjectAnimator.ofInt(splashprogress,"progress",50);
        progressanimator.setDuration(5000);
        progressanimator.setInterpolator(new AccelerateDecelerateInterpolator());
        progressanimator.start();
        countDownTimer.start();
        intent = new Intent(SplashScreen.this, LoginPage.class);

    }

    public class CountDownTimer extends android.os.CountDownTimer {


        public CountDownTimer(long millisInFuture, long countDownInterval) {

            super(millisInFuture, countDownInterval);

        }

        @Override
        public void onTick(long millisUntilFinished) {


        }

        @Override
        public void onFinish() {



            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
            username = preferences.getString("username","");
            password = preferences.getString("password","");
            if(!username.equalsIgnoreCase("")&& !password.equalsIgnoreCase("")){
                new adminLoginHandler().execute();

            }
            else {
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }


        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();

    }




    @Override
    protected void onResume(){
        super.onResume();
        countDownTimer.start();

    }

    public class adminLoginHandler extends AsyncTask {

        String resp;
        String data;
        private  static final String LoginURL = "http://odsgcard.com.ng/android_project/AdminLogin.php";
        String prompt;

        protected void onPreExecute() {
            super.onPreExecute();







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


            //Toast.makeText(getContext(), result.toString(), Toast.LENGTH_LONG).show();

            //Toast.makeText(getContext(),result.toString(),Toast.LENGTH_LONG).show();

            if(result == null){
                Toast.makeText(SplashScreen.this,"Unable to login Please try Again",Toast.LENGTH_LONG).show();

            }

            if(result != null){

                if(result instanceof adminUser ) {
                    Intent intent = new Intent(SplashScreen.this, adminHomepage.class);
                    ArrayList<adminUser> admininfo = new ArrayList<>();
                    admininfo.add((adminUser)result);
                    intent.putParcelableArrayListExtra("info", admininfo);
                    startActivity(intent);
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
                        .add("password", password)
                        .add("username",username)
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

    private Boolean isNetworkAvailable(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null && activeNetworkInfo.isConnected();
    }
}
