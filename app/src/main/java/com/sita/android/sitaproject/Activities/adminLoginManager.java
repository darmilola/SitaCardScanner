package com.sita.android.sitaproject.Activities;

import android.util.Log;

import com.sita.android.sitaproject.Models.adminUser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class adminLoginManager {

    private OkHttpClient client = new OkHttpClient();
    private String LoginURL  = "http://odsgcard.com.ng/Login.php";

    public void AdminLogin(final adminUser user){

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username",user.getUserName())
                .addFormDataPart("password",user.getPassWord())
                .build();
        Request request = new Request.Builder()
                .url(LoginURL)
                .post(requestBody)
                .build();

          Call call = client.newCall(request);

          call.enqueue(new Callback() {
              @Override
              public void onFailure(Call call, IOException e) {

              }

              @Override
              public void onResponse(Call call, Response response) throws IOException {

                 try{

                     String resp = response.body().string();
                     Log.e("response",resp );
                     if(response.isSuccessful()){
                         Log.e("successful", "successful");
                     }

                     else{

                     }
                 }
                 catch (IOException E){
                     Log.e("exception", "onResponse: ");
                 }

              }

          });

        }
    }

