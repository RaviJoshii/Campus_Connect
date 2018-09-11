package com.example.ravi.facultyandstudents;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class zchangepassword extends AppCompatActivity {
    EditText news,cnew,old,rollno;
    Button confirm;
    String snews,scnews,sold,srollno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zchangepassword);
        news=findViewById(R.id.news);
        cnew=findViewById(R.id.cnews);
        old=findViewById(R.id.old);
        rollno=findViewById(R.id.rollno);
        confirm=findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snews=news.getText().toString();
                scnews=cnew.getText().toString();
                sold=old.getText().toString();
                srollno=rollno.getText().toString();
                if(snews.equals(scnews)==false){
                    Toast.makeText(getApplicationContext(),"NEW PASSWORD DOESN'T MATCH",Toast.LENGTH_SHORT).show();
                    news.setText("");
                    cnew.setText("");
                }
                else {
                    new SendPostRequestchange().execute();
                }

            }
        });

    }
    public class SendPostRequestchange extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://192.168.43.212:7000/home/Student/changepassword"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("rollno", srollno);
                postDataParams.put("oldpassword", sold);
                postDataParams.put("newpassword", snews);
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            String y="";
            try {
                JSONObject json = new JSONObject(result);
                y=json.getString("error");
                Log.e("value of y",y);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("servers",result);


            if(y.equals("0")){
                old.setText("");
                news.setText("");
                rollno.setText("");
                cnew.setText("");
                Toast.makeText(getApplicationContext(), "PASSWORD SUCCESSFULLY CHANGED", Toast.LENGTH_SHORT).show();


            }
            else if( y.equals("2")){
                old.setText("");
                news.setText("");
                rollno.setText("");
                cnew.setText("");
                Toast.makeText(getApplicationContext(), "ROLLNO or PASSWORD DOESN'T MATCH ", Toast.LENGTH_SHORT).show();

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
