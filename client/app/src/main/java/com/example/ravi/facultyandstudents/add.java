package com.example.ravi.facultyandstudents;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;


public class add extends Fragment  {
    View myview;
    private Spinner spinner1, spinner2;

    EditText e1, e2;
    Button addb;
    URL url;
     RadioGroup radioSexGroup;
     RadioButton radioSexButton;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {





        myview= inflater.inflate(R.layout.add,container,false);
        String [] values1 =
                {"CSE","ECE","MAE","IT","MAE","EEE","LAW","MAIMS"};
        String [] values2 =
                {"Ist","2nd","3rd","4th","5th","6th","7th","8th"};
        spinner1 = (Spinner) myview.findViewById(R.id.spinner1);
        spinner2 = (Spinner) myview.findViewById(R.id.spinner2);
         e1=myview.findViewById(R.id.editText3);
         e2=myview.findViewById(R.id.editText4);
        addb=myview.findViewById(R.id.button3);



        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values1);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values2);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter2);



        addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    url = new URL("http://192.168.43.212:7000/home/Teacher/add");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                new SendPostRequesta().execute();

            }

        });






        return myview;
    }





    public class SendPostRequesta extends AsyncTask<String, Void, String> {



        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {
            try {
                String Name= e1.getText().toString();
                String rollno= e2.getText().toString();
                String department = spinner1.getSelectedItem().toString();
                String year = spinner2.getSelectedItem().toString();
                String sex,password;
                password=rollno+"0011";
                RadioButton m=myview.findViewById(R.id.male);
                RadioButton f=myview.findViewById(R.id.female);
                radioSexGroup =  myview.findViewById(R.id.radiogroup);
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                radioSexButton = myview.findViewById(selectedId);
                if(radioSexButton==m) sex="Male";
                else sex="female";





                JSONObject postDataParamsa = new JSONObject();
                postDataParamsa.put("Name", Name);
                postDataParamsa.put("department", department);
                postDataParamsa.put("year", year);
                postDataParamsa.put("rollno", rollno);
                postDataParamsa.put("password", password);
                postDataParamsa.put("sex",sex);
                Log.e("params", postDataParamsa.toString());

                HttpURLConnection conn = null;
                try {

                    conn = (HttpURLConnection) url.openConnection();
                    Log.e("cp1","connectionin");
                    //Toast.makeText(LoginActivity.this,"connevtion success",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    //Toast.makeText(LoginActivity.this,"connevtion failed",Toast.LENGTH_SHORT).show();
                    Log.e("connectionError", e.getMessage());
                }
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
////////////////////////////////////////////////////////////
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParamsa));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }


        @Override
        protected void onPostExecute(String s) {
            String y="";
            try {
                JSONObject json = new JSONObject(s);
                y=json.getString("error");
                Log.e("value of y",y);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("servers",s);


            if(y.equals("0")){
                // Toast.makeText(LoginActivity.this, "Teacher login", Toast.LENGTH_SHORT).show();
                Log.e("STATUS","REGISTER SUCCESSFULLY");
            }


        }
    }
    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }


}
