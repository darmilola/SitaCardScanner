package com.sita.android.sitaproject.Fragments;



import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sita.android.sitaproject.Activities.Registration;
import com.sita.android.sitaproject.Models.OccupationRecyclerViewAdapter;
import com.sita.android.sitaproject.Models.localgovernmentadapter;
import com.sita.android.sitaproject.Models.localgovernmentmodel;
import com.sita.android.sitaproject.Models.occupations;
import com.sita.android.sitaproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class secondpage extends Fragment /* implements  AdapterView.OnItemSelectedListener*/ {

    Spinner LocalGovernmentSpinner;
    Spinner MaritalSpinner;
    EditText PhoneNumber;
    EditText Occupation, residentialaddressvalue,localgovernmentvalue;
    RadioGroup GenderRgb;
    TextView genderTitle, residentialaddresstitle, phonetitle, occupationtitle, localgovernmenttitle, maritaltitle;
    View view;
    ArrayAdapter LocalGovernmentArray;
    ArrayAdapter maritalarray;
    TextView spinneritem;
    RadioButton maleradio, femaleradio;
    AlertDialog DialogHandler;
    String prompt, occupation_id,localgovernment_id;
    ArrayList<occupations> occupationsArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    OccupationRecyclerViewAdapter occupationRecyclerViewAdapter;
    private static final String OccupationURL = "http://odsgcard.com.ng/android_project/OccupationGetter.php";
    private static final String LocalGovernmentURL = "http://odsgcard.com.ng/android_project/localgovernmentgetter.php";
    AlertDialog b,c;
    int flag = 0;


    String[] maritaLstatus = {"Marital Status", "Single", "Married", "Divorced", "Separated", "Widowed"};

    public secondpage() {
        // Required empty public constructor
    }


    public static secondpage newInstance() {

        secondpage mysecondpage = new secondpage();
        Registration.AuthSecondPage();
        Log.e("new instance", "newInstance: ");

        return mysecondpage;
    }


    public interface OnPhoneNumberPass {

        void PassPhoneNumber(String data);
    }

    public interface OnPassOccupationid {

        void passOccupationid(String data);
    }

    public interface OnPassLocalGovernmentid {
        void passLocalGovernmentid(String data);
    }

    public interface OnPassMaritalStatus {
        void passMaritalStatus(String data);

    }

    public interface OnPassGender {
        void passGender(String data);
    }

    public interface OnPassResidentialAddress {
        void passResidentialAddress(String data);
    }

    public interface OnPassFlag {
        void passFlag(int Data);
    }

    public interface OnPassMaritalFlag {
        void passMaritalFlag(int data);
    }

    public interface OnPassLocalGovernmentFlag {
        void passLocalGovernment(int data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_secondpage, container, false);


        InitializeView();


        Occupation.setOnClickListener(v -> {

            Occupation.setEnabled(false);
            occupationsArrayList = new ArrayList<>();
            OccupationGetter occupationGetter = new OccupationGetter();
            occupationGetter.execute();


        });
          localgovernmentvalue.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  localgovernmentGetter localgovernmentGetter = new localgovernmentGetter();
                  localgovernmentGetter.execute();
              }
          });



        GenderRgb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                GetGender();
            }
        });


        if (isVisible()) {
            Registration.AuthSecondPage();
            Log.e("visible", "onCreateView: ");
        }

        return view;
    }


    private final TextWatcher PhoneNumberWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {


            String data = PhoneNumber.getText().toString();
            phoneNumberPasser.PassPhoneNumber(data);


        }
    };

    private final TextWatcher addressWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {


            String data = residentialaddressvalue.getText().toString();
            residentialAddressPasser.passResidentialAddress(data);


        }
    };


    private final TextWatcher OccuaptionWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {


            //String data = Occupation.getText().toString();
            occupationpasser.passOccupationid(occupation_id);
            Log.e("id passed", occupation_id);


        }
    };


    OnPhoneNumberPass phoneNumberPasser;
    OnPassOccupationid occupationpasser;
    OnPassLocalGovernmentid localGovernmentpasser;
    OnPassMaritalStatus maritalStatusPasser;
    OnPassGender GenderPasser;
    OnPassFlag FlagPasser;
    OnPassLocalGovernmentFlag localGovernmentFlagPasser;
    OnPassMaritalFlag MaritalFlagPasser;
    OnPassResidentialAddress residentialAddressPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        phoneNumberPasser = (OnPhoneNumberPass) context;
        occupationpasser = (OnPassOccupationid) context;
        localGovernmentpasser = (OnPassLocalGovernmentid) context;
        maritalStatusPasser = (OnPassMaritalStatus) context;
        GenderPasser = (OnPassGender) context;
        FlagPasser = (OnPassFlag) context;
        localGovernmentFlagPasser = (OnPassLocalGovernmentFlag) context;
        MaritalFlagPasser = (OnPassMaritalFlag) context;
        residentialAddressPasser = (OnPassResidentialAddress) context;
    }

    public void GetGender() {

        int GenderID = 0;
        String Gender;

        if (GenderRgb.getCheckedRadioButtonId() != -1) {
            GenderID = GenderRgb.getCheckedRadioButtonId();
        }
        Gender = ((RadioButton) GenderRgb.findViewById(GenderID)).getText().toString();
        GenderPasser.passGender(Gender);


    }

    public class MaritalStatusSelectedListener implements AdapterView.OnItemSelectedListener {
        int maritalflag = 0;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
            // String text= ((TextView) parent.getChildAt(0)).getText().toString();
            String text = MaritalSpinner.getSelectedItem().toString();
            maritalflag = 1;
            MaritalFlagPasser.passMaritalFlag(maritalflag);
            maritalStatusPasser.passMaritalStatus(text);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            maritalStatusPasser.passMaritalStatus("");

        }
    }

    public void InitializeView() {

        MaritalSpinner = view.findViewById(R.id.maritalspinner);
        PhoneNumber = view.findViewById(R.id.phonenumber);
        Occupation = view.findViewById(R.id.ocupation);
        GenderRgb = view.findViewById(R.id.genderradiogroup);
        genderTitle = view.findViewById(R.id.genderTitle);
        localgovernmenttitle = view.findViewById(R.id.localgovernmenttitle);
        localgovernmentvalue = view.findViewById(R.id.localgovernmentvalue);
        occupationtitle = view.findViewById(R.id.occupationtitle);
        phonetitle = view.findViewById(R.id.phonenumbertitle);
        maritaltitle = view.findViewById(R.id.maritalstatustitle);
        maleradio = view.findViewById(R.id.maleradiobutton);
        femaleradio = view.findViewById(R.id.female);
        spinneritem = view.findViewById(R.id.spinnertext);
        residentialaddressvalue = view.findViewById(R.id.residentialaddress);
        residentialaddresstitle = view.findViewById(R.id.residentialaddresstitle);

        maritalarray = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, maritaLstatus);

        maritalarray.setDropDownViewResource(R.layout.spinneritem);

        MaritalSpinner.setAdapter(maritalarray);
        MaritalSpinner.setOnItemSelectedListener(new MaritalStatusSelectedListener());


        PhoneNumber.addTextChangedListener(PhoneNumberWatcher);
        Occupation.addTextChangedListener(OccuaptionWatcher);
        residentialaddressvalue.addTextChangedListener(addressWatcher);
        Typeface customfont = Typeface.createFromAsset(getActivity().getAssets(), "Anteb-SemiLight.otf");
        PhoneNumber.setTypeface(customfont);
        PhoneNumber.setSingleLine(true);
        genderTitle.setTypeface(customfont);
        Occupation.setTypeface(customfont);
        localgovernmentvalue.setTypeface(customfont);
        localgovernmentvalue.setFocusable(false);
        Occupation.setFocusable(false);
        localgovernmenttitle.setTypeface(customfont);
        maritaltitle.setTypeface(customfont);
        occupationtitle.setTypeface(customfont);
        phonetitle.setTypeface(customfont);
        residentialaddressvalue.setTypeface(customfont);
        residentialaddresstitle.setTypeface(customfont);


    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {

        }
        if (isVisibleToUser) {
            Registration.AuthSecondPage();
            Log.e("visible", "setUserVisibleHint: ");


        }

    }

    public class OccupationGetter extends AsyncTask {


        @Override
        protected void onPreExecute() {

            //Toast.makeText(getContext(), "Fetching Occupations Please Wait", Toast.LENGTH_LONG).show();
             //displaydialog("Fetching Occupations Please Wait...");
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(getContext());
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View DialogView = inflater.inflate(R.layout.loadingoccupationdialog,null);
            alertdialog.setView(DialogView);
            alertdialog.setCancelable(false);
            c = alertdialog.create();
            c = alertdialog.show();
        }


        @Override
        protected Object doInBackground(Object[] objects) {

            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }

            String jsonString = new OccupationJsonParser().SelectData(OccupationURL);

            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if (status.equalsIgnoreCase("Error")) {

                        prompt = "Error in connection";
                    }
                    if (status.equalsIgnoreCase("Successful")) {

                        JSONArray jsonArray = info.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); ++i) {

                            JSONObject j = jsonArray.getJSONObject(i);

                            String occupation = j.getString("occupation_name");
                            String id = j.getString("id");
                            occupations occupations = new occupations(occupation, id);

                            occupationsArrayList.add(occupations);
                        }

                        return occupationsArrayList;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }


        protected void onPostExecute(Object result) {

            c.dismiss();
            Occupation.setEnabled(true);
            if (result == null) {
                //Toast.makeText(getContext(), "Unable to fetch occupations Please try Again", Toast.LENGTH_LONG).show();
                //Occupation.setEnabled(true);
                // DialogHandler.dismiss();
                displaydialog("Unable to fetch occupations Please try Again");
            }
            if (result != null) {
                if (result instanceof ArrayList) {
                    // Toast.makeText(getContext(),result.toString(),Toast.LENGTH_LONG).show();
                    occupationsArrayList = (ArrayList<occupations>) result;


                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                    final View occupationsdialog = layoutInflater.inflate(R.layout.occupationdialog, null);
                    alertdialog.setView(occupationsdialog);
                    alertdialog.setCancelable(false);
                    DialogHandler = alertdialog.create();
                    DialogHandler = alertdialog.show();


                    occupationRecyclerViewAdapter = new OccupationRecyclerViewAdapter(getActivity().getApplicationContext(), occupationsArrayList, clickedItemIndex -> {

                        occupations current = occupationsArrayList.get(clickedItemIndex);
                        occupation_id = current.getId();
                        Occupation.setText(current.getOccupation());

                        DialogHandler.dismiss();


                    });
                    recyclerView = (RecyclerView) occupationsdialog.findViewById(R.id.occupationdialogrecyclerview);
                    recyclerView.setAdapter(occupationRecyclerViewAdapter);
                    RecyclerView.LayoutManager mLayoutManager =
                            new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setHasFixedSize(true);


                }
                if (result.toString().equalsIgnoreCase(prompt)) {

                    //Toast.makeText(getContext(), prompt, Toast.LENGTH_LONG)
                      //      .show();
                     displaydialog(prompt);

                }
            }

        }
    }


    public class localgovernmentGetter extends AsyncTask {
        ArrayList<localgovernmentmodel> localgovernmentmodelArrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {

            android.support.v7.app.AlertDialog.Builder alertdialog = new android.support.v7.app.AlertDialog.Builder(getContext());
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View DialogView = inflater.inflate(R.layout.loadinglocalgovernmentdialog,null);
            alertdialog.setView(DialogView);
            alertdialog.setCancelable(false);
            b = alertdialog.create();
            b = alertdialog.show();
        }


        @Override
        protected Object doInBackground(Object[] objects) {

            if (!isNetworkAvailable()) {
                prompt = "No Network Connection";
                return prompt;
            }
            String jsonString = new localgovernmentJsonParser().SelectData(LocalGovernmentURL);
            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONObject info = jsonObject.getJSONObject("info");
                    String status = info.getString("status");

                    if (status.equalsIgnoreCase("Error")) {

                        prompt = "Error in connection";
                    }
                    if (status.equalsIgnoreCase("Successful")) {

                        JSONArray jsonArray = info.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); ++i) {

                            JSONObject j = jsonArray.getJSONObject(i);

                            String name = j.getString("lga_name");
                            String id = j.getString("lga_id");
                            localgovernmentmodel localgovernmentmodel = new localgovernmentmodel(name, id);

                            localgovernmentmodelArrayList.add(localgovernmentmodel);

                        }

                        return localgovernmentmodelArrayList;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return jsonString;
        }

        protected void onPostExecute(Object result) {

                  b.dismiss();
                  if (result == null) {
               // Toast.makeText(getContext(), "Unable to fetch local governments Please try Again", Toast.LENGTH_LONG).show();
                 displaydialog("Unable to fetch local governments Please try Again");
                // DialogHandler.dismiss();
            }
            if (result != null) {
                if (result instanceof ArrayList) {
                    // Toast.makeText(getContext(),result.toString(),Toast.LENGTH_LONG).show();
                    ArrayList<localgovernmentmodel> localgovernmentmodels = (ArrayList<localgovernmentmodel>) result;

                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(getActivity());
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                    final View localgovernmentdialog = layoutInflater.inflate(R.layout.localgovernmentdialog, null);
                    alertdialog.setView(localgovernmentdialog);
                    alertdialog.setCancelable(true);
                    DialogHandler = alertdialog.create();
                    DialogHandler = alertdialog.show();


                       localgovernmentadapter adapter  = new localgovernmentadapter(localgovernmentmodels,getContext (),(int clickedItemIndex) -> {

                        localgovernmentmodel current = localgovernmentmodels.get(clickedItemIndex);
                        localgovernment_id = current.getId();
                        localgovernmentvalue.setText(current.getLocalGovernmentName());
                        localGovernmentpasser.passLocalGovernmentid(localgovernment_id);
                        DialogHandler.dismiss();


                    });
                    recyclerView = (RecyclerView)localgovernmentdialog.findViewById(R.id.localgovernmentdialogrecyclerview);
                    recyclerView.setAdapter(adapter);
                    RecyclerView.LayoutManager mLayoutManager =
                            new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setHasFixedSize(true);


                }
                if (result.toString().equalsIgnoreCase(prompt)) {

                    //Toast.makeText(getContext(), prompt, Toast.LENGTH_LONG)
                            //.show();
                    displaydialog(prompt);


                }
            }
        }

    }

    private Boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public class OccupationJsonParser {

        public String SelectData(String url) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                // RequestBody formBody = new FormBody.Builder().build();

                Request request = new Request.Builder().url(url).build();
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

    public class localgovernmentJsonParser {

        public String SelectData(String url) {

            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .readTimeout(50, TimeUnit.SECONDS)
                        .build();

                // RequestBody formBody = new FormBody.Builder().build();

                Request request = new Request.Builder().url(url).build();
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
    private void displaydialog(String message){
        android.support.v7.app.AlertDialog.Builder builder;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            builder = new android.support.v7.app.AlertDialog.Builder(getContext(),android.R.style.Theme_Material_Dialog_Alert);
        }
        else{
            builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        }
        builder.setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}