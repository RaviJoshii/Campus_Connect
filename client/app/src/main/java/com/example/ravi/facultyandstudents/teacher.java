package com.example.ravi.facultyandstudents;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class teacher extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       /* SharedPreferences prefs = getSharedPreferences("Teachers", MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {
            String Tname = prefs.getString("name", "No name defined");//"No name defined" is the default value.
        }*/



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(getApplicationContext(), "PLease do Logout", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teacher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager=getFragmentManager();

        if (id == R.id.nav_add) {
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame,new add());
            fragmentTransaction.commit();
            // Handle the camera action
        } else if (id == R.id.nav_attendance) {
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame,new attendance());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_uploadsn) {
           /* Intent i =new Intent(this, unotes.class);
            startActivity(i);*/
            //Log.e("notes","notes");
            Intent p =new Intent(this, unotes.class);
            startActivity(p);


        } else if (id == R.id.nav_notice){

            Intent i =new Intent(this, unotice.class);
            startActivity(i);


        } else if (id == R.id.nav_discussionforum) {
            String Username= getIntent().getStringExtra("name");
            Intent i =new Intent(this, group.class);
            i.putExtra("username",Username);
            startActivity(i);
            /*FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame,new discussionforum());
            fragmentTransaction.commit();*/

        }
        else if (id == R.id.nav_wifichat) {
            Intent i =new Intent(this, wifi.class);
            startActivity(i);

        }
        else if(id==R.id.nav_share){
            Toast.makeText(getApplicationContext(), "logout Succesfully", Toast.LENGTH_SHORT).show();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.notices);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
    /*@Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "logout Succesfully", Toast.LENGTH_SHORT).show();

    }*/



}
