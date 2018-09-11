package com.example.ravi.facultyandstudents;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class znotice extends AppCompatActivity {

   // ListView lv;
    String all,description;
    int key;
    //String values[];
    ListView listView;
    private  StringRequest stringRequest; // Assume this exists.
    private RequestQueue mRequestQueue;  // Assume this exists.
    DownloadManager downloadManager;
    ArrayList<String> values=new ArrayList<String>();
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_znotice);
        mRequestQueue = Volley.newRequestQueue(this);
        String url ="http://192.168.43.212:7000/home/teacher/studfilesystem1";
        stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONArray array = new JSONArray(response);
                            for (i = 0; i < array.length(); i++) {
                                JSONObject row = array.getJSONObject(i);
                                key = row.getInt("key");
                                description = row.getString("description");
                                Log.e("key", key + "");
                                Log.e("decription", description);
                                values.add(key+": "+description);


                            }
                            Collections.reverse(values);
                            for(int k = 0; k < values.size(); k++){
                               Log.e("values",values.get(k));
                            }


                        }catch (Exception e){
                            e.printStackTrace();
                        }

               check();

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error","error");
                    }

                });
        mRequestQueue.add(stringRequest);



    }

    private void check() {

        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.activity_listview, values);

        listView= (ListView) findViewById(R.id.listnotice);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                position=i-position;
            String url="http://192.168.43.212:7000/home/teacher/snotice?link="+ position+".jpg";
            Log.e("url",url);
            try {
                downloadManager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(url);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);
            }catch (Exception e){
                Log.e("uri_exception",e.getMessage());

            }

            }
        });

    }


}
