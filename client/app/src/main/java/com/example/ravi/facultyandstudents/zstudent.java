package com.example.ravi.facultyandstudents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class zstudent extends AppCompatActivity {
   ImageButton notes,notice,discuss,changepassword,logout,wifi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zactivity_student);

        notes=findViewById(R.id.imageButton1);
        notice=findViewById(R.id.imageButton2);
        discuss=findViewById(R.id.imageButton3);
        wifi=findViewById(R.id.imageButton4);
        changepassword=findViewById(R.id.imageButton5);
        logout=findViewById(R.id.imageButton6);

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),znotes.class);
                startActivity(i);

            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),znotice.class);
                startActivity(i);

            }
        });
        discuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username= getIntent().getStringExtra("name");
                //Log.e("usernames",Username);
                Intent i =new Intent(getApplicationContext(), group.class);
                i.putExtra("username",Username);
                startActivity(i);
            }
        });
       wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(), wifi.class);
                startActivity(i);

            }
        });
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),zchangepassword.class);
                startActivity(i);
            }
        });
       logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Please do Logout", Toast.LENGTH_SHORT).show();

    }
}
