package com.example.ravi.facultyandstudents;

import android.app.Fragment;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ravi on 4/8/18.
 */

public class attendance extends Fragment {
    ArrayList<String> select =new ArrayList<>();
    Button b,go;
    Spinner semdrop ;
    Spinner group ;
    String text,itemss;
    View myview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myview= inflater.inflate(R.layout.attendance,container,false);
        b=myview.findViewById(R.id.select);
        go = myview.findViewById(R.id.go);
        spinners();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected();
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectedItems();
            }
        });






        return myview;
    }
    public  void showSelectedItems(){
        String text1 = semdrop.getSelectedItem().toString();
        String text2 = group.getSelectedItem().toString();
        text=text1+text2;
        itemss="[";
        for(String itemm:select){
            itemss+=itemm+",";
        }
        itemss=itemss+"]";
        new SendPostRequestaa().execute();
    }


    //////////////////////////////////////////

    public void spinners() {
        semdrop = myview.findViewById(R.id.spinner3);
        group = myview.findViewById(R.id.spinner4);

//create a list of items for the spinner.
        String[] spinngroup = new String[]{"1C", "2C", "3C","4C","5C","6C","7C","8C"};
        String[] spinnsem = new String[]{"1", "2", "3","4","5","6","7","8","9","11","12","13"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(myview.getContext(), android.R.layout.simple_spinner_dropdown_item, spinngroup);
//set the spinners adapter to the previously created one.
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(myview.getContext(), android.R.layout.simple_spinner_dropdown_item, spinnsem);
        semdrop.setAdapter(adapter1);
        group.setAdapter(adapter2);

    }
    /////////////////////////////////
    public  void selected()
    {
        ListView chl = myview.findViewById(R.id.listview);
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayList<String>   items = new ArrayList<>();
        for (int i = 0; i < 70; i++) {
            items.add("Rollno." + (i + 1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(myview.getContext(), R.layout.row_layout, R.id.checkedTextView, items);
        chl.setAdapter(adapter);
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selecteditem = ((TextView) view).getText().toString();
                if (select.contains(selecteditem)) {
                    select.remove(selecteditem);
                } else {
                    select.add(selecteditem);
                }
            }
        });

    }
    /////////////////////////////////

    public class SendPostRequestaa extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://192.168.43.212:7000/home/teacher/attendance"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("batch", text);
                postDataParams.put("attendance", itemss);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Log.e("params",postDataParams.toString());

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
            Toast.makeText(myview.getContext(), result,
                    Toast.LENGTH_LONG).show();
        }
    }
    ///////////////////////////////////////////////
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
