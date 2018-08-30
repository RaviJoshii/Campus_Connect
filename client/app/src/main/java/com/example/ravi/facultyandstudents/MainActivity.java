package com.example.ravi.facultyandstudents;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start =findViewById(R.id.button2);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });


        /*SharedPreferences mypref = getSharedPreferences("mypref", MODE_PRIVATE);
        boolean firststart = mypref.getBoolean("firstStart", true);
        if(firststart){
            startIntro();
        }*/



    }


    /*private void startIntro() {


        SharedPreferences mypref= getSharedPreferences("mypref",MODE_PRIVATE);
        SharedPreferences.Editor editor= mypref.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }*/
}
