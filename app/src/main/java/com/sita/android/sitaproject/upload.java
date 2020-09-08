package com.sita.android.sitaproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sita.android.sitaproject.Fragments.Citizenlogin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class upload extends AppCompatActivity {


    Uri filePath;
    Bitmap bitmap;
    ImageView imageView;

    String pinnumber="556565gggh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_testing);

        imageView = findViewById(R.id.testingimage);
        Button button = findViewById(R.id.registertesting);
        Button upload = findViewById(R.id.uploadtesting);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select profile picture"), 0);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadImage uploadImage = new UploadImage();
                uploadImage.execute(bitmap);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();

            try {
                String s = filePath.toString();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            }
    }


    public String getStringImage(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
        byte[] imagebytes = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imagebytes,Base64.DEFAULT);
        return  encodedImage;
    }




        class UploadImage extends AsyncTask<Bitmap, Void, String> {
            String data;
            String url;


            @Override
            protected String doInBackground(Bitmap... bitmaps) {

                Bitmap bitmap = bitmaps[0];
                String uploadImage = getStringImage(bitmap);

               /* try {
                    data = "?image=" + URLEncoder.encode(uploadImage, "UTF-8");
                    data += "&pinnumber=" +URLEncoder.encode(pinnumber,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/

                url = "http://sita.epizy.com/profilepictureupload.php";


                Log.e("uri", url );

                String jsonObject = new JsonParser().SelectData(url,uploadImage,pinnumber);
                return jsonObject;

            }

            protected void onPostExecute(String result) {

                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                //Intent intent= new Intent(getContext(), Homepage.class);
                //startActivity(intent);
            }
        }





    public class JsonParser{

        public String SelectData(String url,String image,String pin){

            try{
                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormBody.Builder()
                        .add("image",image)
                        .add("pinnumber",pin)
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






}