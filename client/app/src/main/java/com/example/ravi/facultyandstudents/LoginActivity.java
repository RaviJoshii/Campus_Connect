package com.example.ravi.facultyandstudents;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {
    public static  String username="";
    public static  String password="";
    EditText e1,e2;
    RadioButton r1,r2,c;
    Button Loginb1;
    RadioGroup rg;
    int check=0;
    URL url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1=findViewById(R.id.editText);
        e2=findViewById(R.id.editText2);
        r1=findViewById(R.id.radioButton);
        r2=findViewById(R.id.radioButton2);
        Loginb1=findViewById(R.id.button);
        rg=findViewById(R.id.radiog);


        Loginb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int selected_id=rg.getCheckedRadioButtonId();
                c=findViewById(selected_id);

               if(c==r1){

                    try {

                 url = new URL("http://192.168.43.212:7000/home/Teacherlogin");
                    } catch (MalformedURLException e) {
                        Log.e("assign","problem");
                       // Toast.makeText(LoginActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }


                }
                else{
                    try {

                        url = new URL("http://192.168.43.212:7000/home/Studentlogin");
                    } catch (MalformedURLException e) {
                       // Toast.makeText(LoginActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }


                   // Toast.makeText(LoginActivity.this, "Student login", Toast.LENGTH_SHORT).show();
                }
                new SendPostRequest().execute();

            }



        });

    }





        public class SendPostRequest extends AsyncTask<String, Void, String> {



            protected void onPreExecute(){}

            protected String doInBackground(String... arg0) {
                try {
                    username = e1.getText().toString();
                    password = e2.getText().toString();
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("username", username);
                    postDataParams.put("password", password);
                    Log.e("params", postDataParams.toString());
                    SharedPreferences.Editor editor = getSharedPreferences("Teachers", MODE_PRIVATE).edit();
                    editor.putString("sharedTname", username);
                    editor.apply();
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
                    writer.write(getPostDataString(postDataParams));

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
                String d="";
                try {
                    JSONObject json = new JSONObject(s);
                    y=json.getString("error");
                    d=json.getString("data");

                    Log.e("value of y",y);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("servers",s);


               if(y.equals("0")){
                   e1.setText("");
                   e2.setText("");
                   Toast.makeText(getApplicationContext(), "Welcome Student", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, zstudent.class);
                    intent.putExtra("name",username);
                    Log.e("username",username);


                    startActivity(intent);
                }
               else if( y.equals("2")){
                   e1.setText("");
                   e2.setText("");
                   Toast.makeText(getApplicationContext(), "Welcome Teacher", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(LoginActivity.this, teacher.class);
                   intent.putExtra("name",username);
                   Log.e("username",username);
                   startActivity(intent);
               }
               else{
                   Toast.makeText(getApplicationContext(), d, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {

    }
}
