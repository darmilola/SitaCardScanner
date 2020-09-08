package com.sita.android.sitaproject.Fragments;
import com.sita.android.sitaproject.FingerPrintClasses.FingerprintTemplate;
import com.sita.android.sitaproject.FingerPrintClasses.FingerprintMatcher;
import com.sita.android.sitaproject.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;



import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.*/

public class FourthPage extends Fragment {


    public FourthPage() {
        // Required empty public constructor
    }


    public static FourthPage newInstance() {

        FourthPage fourthPage = new FourthPage();

        return fourthPage;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v=inflater.inflate(R.layout.fragment_fourth_page, container, false);
        Button check= v.findViewById(R.id.checkfinger);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FingerprintTemplate probe = null;
                FingerprintTemplate canditate= null;
                double score;





                        Bitmap f1= BitmapFactory.decodeResource(getResources(),R.drawable.samplefingerprint);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        f1.compress(Bitmap.CompressFormat.PNG,100,stream);
                        byte[] probeImage= stream.toByteArray();



                        Bitmap f2= BitmapFactory.decodeResource(getResources(),R.drawable.samplefingerprint);
                        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                        f2.compress(Bitmap.CompressFormat.PNG,100,stream2);
                        byte[] candidateImage= stream2.toByteArray();
                        //File fileprobe= new File(Paths.get("samole"));
                        //byte[] probeImage = FingerPrint.readImage(Paths.get("Finget"))
                        //byte[] candidateImage = Files.readAllBytes(Paths.get("samplefingerprint.png"));

                      probe = new FingerprintTemplate()
                                .dpi(500)
                                .create(probeImage);
                         canditate = new FingerprintTemplate()
                                .dpi(500)

                                .create(candidateImage);

                        score   = new FingerprintMatcher()
                                .index(probe)
                                .match(canditate);
                        Toast.makeText(getContext(),Double.toString(score), Toast.LENGTH_LONG).show();







            }


        });
        return v;
    }

}
