package com.sita.android.sitaproject.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sita.android.sitaproject.Activities.Registration;
import com.sita.android.sitaproject.R;

import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Thirdpagefragment extends Fragment {
     private static int PICK_IMAGE_REQUEST=0;

     ImageView UploadImage;
     Button    UploadButton;
     Uri filePath;
     View view;
     ContentResolver resolver;
     private Bitmap bitmap;

    public interface OnpassProfilePicture{

        public void passPicture(Bitmap picture);
    }

    public Thirdpagefragment() {
        // Required empty public constructor
    }

    public static Thirdpagefragment newInstance(){

        Thirdpagefragment mythirdpagefragment= new Thirdpagefragment();

        return  mythirdpagefragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view = inflater.inflate(R.layout.fragment_thirdpagefragment, container, false);
        Typeface customfont= Typeface.createFromAsset(getActivity().getAssets(),"Anteb-SemiLight.otf");

         InitializeView();
        UploadButton.setTypeface(customfont);
        Log.e("here", "getItem5: ");


         UploadButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                     ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},1);
                 }
                 else {
                     SelectImage();

                 }
             }
         });


        return view;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //do stuffs permission granted
                   SelectImage();
                }
                else{
                    //permissions denied
                }
        }

    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            UploadImage.setImageBitmap(photo);
            PicturepPasser.passPicture(photo);
        }

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            filePath = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                UploadImage.setImageBitmap(bitmap);

                PicturepPasser.passPicture(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
    OnpassProfilePicture PicturepPasser;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        PicturepPasser=(OnpassProfilePicture)context;
       // Registration.nextbutton.setEnabled(false);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if(!isVisibleToUser){

        }
        if(isVisibleToUser){
            Registration.AuthThirdPage();
            Log.e("visible", "setUserVisibleHint: " );
        }



    }

    private void InitializeView(){
        UploadButton=view.findViewById(R.id.uploadimagebutton);
        UploadImage= view.findViewById(R.id.imageupload);

    }
   private void SelectImage(){

                final CharSequence[] options = {"Take Photo","Choose From Gallery","cancel"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Select Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(options[which].equals("Take Photo")){
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent,0);
                        }
                        else if(options[which].equals("Choose From Gallery")){
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto,1);

                        }
                        else if(options[which].equals("Cancel")){
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }


    }

