package com.sita.android.sitaproject.Fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sita.android.sitaproject.Activities.Registration;
import com.sita.android.sitaproject.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class firstpage extends Fragment {

    private String title;


    public firstpage() {
        // Required empty public constructor
    }

    public static firstpage newInstance() {

        firstpage myfirstpage = new firstpage();

        return myfirstpage;

    }

    public interface Onfirstnamepass {

        void passFirstname(String data);
    }

    public interface OnFormNumberPass{

        void passFormNumber(String data);
    }

    public interface onSurnamePass{


        void passSurname(String data);
    }

    public interface onOthernamePass{

        void passOthername(String data);
    }

    public interface onOrinPass{

        void passOrin(String data);
    }
    TextView formnumbertitle,orintitle,surnametitle,firstnametitle,othernametitle,registrationTitle;
    EditText firstname;
    EditText surnname;
    EditText formnumber;
    EditText othername;
    EditText orinvalue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /// Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_firstpage, container, false);
        firstname = v.findViewById(R.id.firstname);
        surnname=v.findViewById(R.id.surname);
        formnumber=v.findViewById(R.id.formnumber);
        othername=v.findViewById(R.id.othername);
        orinvalue = v.findViewById(R.id.orinnumbervalue);
        orintitle = v.findViewById(R.id.orinnumbertitle);
        firstname.setSingleLine(true);
        surnname.setSingleLine(true);
        othername.setSingleLine(true);
        formnumber.setSingleLine(true);


         firstname.addTextChangedListener(firstnamewatcher);
         surnname.addTextChangedListener(surnamewatcher);
         othername.addTextChangedListener(otherNamewatcher);
         formnumber.addTextChangedListener(formNumberwatcher);
         orinvalue.addTextChangedListener(orinwatcher);

         formnumbertitle= v.findViewById(R.id.formnumbertext);
         surnametitle=v.findViewById(R.id.surnametext);
         firstnametitle=v.findViewById(R.id.firstnametext);
         othernametitle=v.findViewById(R.id.othernametext);




        Typeface customfont= Typeface.createFromAsset(getActivity().getAssets(),"Anteb-SemiLight.otf");

        formnumbertitle.setTypeface(customfont);
        surnametitle.setTypeface(customfont);
        firstnametitle.setTypeface(customfont);
        othernametitle.setTypeface(customfont);
        orintitle.setTypeface(customfont);
        orinvalue.setTypeface(customfont);

        firstname.setTypeface(customfont);
        surnname.setTypeface(customfont);
        othername.setTypeface(customfont);
        othername.setTypeface(customfont);

        return v;
    }

    private final TextWatcher orinwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
             String data = orinvalue.getText().toString();
             orinPasser.passOrin(data);
        }
    };
    private final TextWatcher firstnamewatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {



                String data = firstname.getText().toString();

                firstnamepasser.passFirstname(data);


        }
    };

    private final TextWatcher surnamewatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {




                String data = surnname.getText().toString();
                surnamePasser.passSurname(data);


        }
    };


    private final TextWatcher formNumberwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {






                String data = formnumber.getText().toString();
                formNumberPasser.passFormNumber(data);


        }
    };


    private final TextWatcher otherNamewatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {



                String data = othername.getText().toString();
                othernamePasser.passOthername(data);


        }
    };


    Onfirstnamepass firstnamepasser;
    onSurnamePass   surnamePasser;
    OnFormNumberPass formNumberPasser;
    onOthernamePass   othernamePasser;
    onOrinPass orinPasser;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        firstnamepasser=(Onfirstnamepass)context;
        surnamePasser=(onSurnamePass)context;
        formNumberPasser=(OnFormNumberPass)context;
        othernamePasser=(onOthernamePass)context;
        orinPasser = (onOrinPass)context;



    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        super.setUserVisibleHint(isVisibleToUser);
        if(!isVisibleToUser){

        }
        if(isVisibleToUser){
            Registration.AuthFirstPage();
            Log.e("visible", "setUserVisibleHint: " );


        }

    }




}
