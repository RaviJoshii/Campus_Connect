package com.example.ravi.facultyandstudents;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class znotes extends AppCompatActivity {

    // ListView lv;
    String all,description,content,content1;
    int key;
    ListView listView;
    private  StringRequest stringRequest; // Assume this exists.
    private RequestQueue mRequestQueue;  // Assume this exists.
    DownloadManager downloadManager;
    ArrayList<String> values=new ArrayList<String>();
    String urp;
    int i;
    Long reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        listView = (ListView) findViewById(R.id.listnotice);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_znotes);
        mRequestQueue = Volley.newRequestQueue(this);
        String url = "http://192.168.43.212:7000/home/teacher/studfilesystem2";
        stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("res",response);

                        try {
                            JSONArray array = new JSONArray(response);
                            for (i = 0; i < array.length(); i++) {
                                JSONObject row = array.getJSONObject(i);
                                key = row.getInt("key");
                                description = row.getString("description");
                                content = row.getString("content");
                                Log.e("content",content);
                                Log.e("key", key + "");
                                Log.e("decription", description);
                                String[] separated = content.split("/");
                                Log.e("sepa",separated[1]);
                                values.add(key + ": " + description+"/"+separated[1]);


                            }
                            Collections.reverse(values);
                            for (int k = 0; k < values.size(); k++) {
                                Log.e("values", values.get(k));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.activity_listview, values);

                        listView = (ListView) findViewById(R.id.listnotes);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {
                                String select=listView.getItemAtPosition(position)+"";
                                String[] separated1 = select.split("/");
                                content1=separated1[1];

                                String substr=select.substring(3);
                                Log.e("content1",content1);

                                check((values.size()-position),substr,content1);




                            }
                        });


                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", "error");
                    }

                });

        mRequestQueue.add(stringRequest);
        //////////////////////////////








        

    }
    public void check(final int position,final String drsc,String c){
        Log.e(position+"",drsc);

        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse("http://192.168.43.212:7000/home/teacher/snotes"+position);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(drsc+" is downloading..");
        request.setDescription(drsc);
        request.setVisibleInDownloadsUi(true);
        String string = drsc.replace("/", ".");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/project/" + string);


        reference = downloadManager.enqueue(request);





    }





}
