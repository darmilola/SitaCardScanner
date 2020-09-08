//package com.sita.android.sitaproject;
/*
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;

public class main extends AppCompatActivity {

    private static final String SCANNER = "com.google.zxing.client.android.SCAN";
    private static final String SCAN_FORMATS = "UPC_A,UPC_E,EAN_8,EAN_13,CODE_39,CODE_93,CODE_128,ITF,Codabar,QR";
    private static final String SCAN_MODE = "ONE_D_MODE";
    private static final int REQUEST_CODE = 1;
    String contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //Button button = findViewById(R.id.mybutton);

        /button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), CaptureActivity.class);
                intent.setAction(SCANNER);
                //intent.putExtra("SCAN_FORMATS",SCAN_FORMATS);
                intent.putExtra("SAVE_HISTORY",true);
               // intent.putExtra("flag",1);
                //intent.putExtra("SCAN_MODE",SCAN_MODE);

                startActivityForResult(intent,0);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //Citizenlogin citizenlogin =new Citizenlogin();
        //citizenlogin.onActivityResult( requestCode,  resultCode, data);
        //

        //Log.e("barcode", contents );

       if(requestCode == 0){

           Log.e("0", "onActivityResult: " );

           if(resultCode == RESULT_OK){

                contents = data.getStringExtra("SCAN_RESULT");
               Log.e("ok", contents );
               Toast.makeText(getApplicationContext(),contents,Toast.LENGTH_LONG).show();

           }

            else if(resultCode == RESULT_CANCELED){
               Log.e("canceled", "onActivityResult: ");

           }
       }

       if(contents != null){
           Toast.makeText(getApplicationContext(),contents,Toast.LENGTH_LONG);
       }



    }
}
*/