package com.sita.android.sitaproject;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class androidphptesting extends AppCompatActivity {
    EditText matricinput;
    TextView matric;
    TextView name;
    TextView gpa;
    TextView level;
    Button  search;

    String link2="http://reqres.in/api/users/2";
    String link = "http://damilolaakinterinwa.000webhostapp.com/a.php";
    OkHttpClient client = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_androidphptesting);
         matricinput= findViewById(R.id.matricnumberinput);
         matric= findViewById(R.id.matricnumber);
         name= findViewById(R.id.name);
         gpa= findViewById(R.id.gpa);
         level= findViewById(R.id.level);
          search= findViewById(R.id.searchbutton);



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = matricinput.getText().toString();

                okHttphandler handler = new okHttphandler();
                handler.execute(link);

            }
        });


    }


    public class okHttphandler extends AsyncTask {




        @Override
        protected String doInBackground(Object[] params) {
            OkHttpClient client = new OkHttpClient();
            Response response;
            Request.Builder builder = new Request.Builder();
            builder.url((String) params[0]);
           // Toast.makeText(getApplicationContext(),(String)params[0],Toast.LENGTH_LONG);
            Request request = builder.build();


            try {

                 response = client.newCall(request).execute();

                return response.body().toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }


        @Override

        protected void onPostExecute(Object result){
            name.setText(result.toString());

        }
        }

    }