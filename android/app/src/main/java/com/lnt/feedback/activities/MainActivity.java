package com.lnt.feedback.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lnt.feedback.R;
import com.lnt.feedback.adapters.FormDisplayAdapter;
import com.lnt.feedback.models.FormDisplayObject;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference mFirebaseFormReference;
    private ValueEventListener mValueEventListener;

    private ArrayList<FormDisplayObject> formList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FormDisplayAdapter adapter;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ProgressBar progressBar;

    private String USERNAME = "Anonymous";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase Init
        mFirebaseFormReference = FirebaseDatabase.getInstance().getReference().child("Forms");
        mFirebaseFormReference.keepSynced(true);

        //ID Referencing
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        recyclerView = (RecyclerView)findViewById(R.id.form_recycler);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        //Toolbar Setup
        setSupportActionBar(toolbar);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //Setup Recycler View
        recyclerView = (RecyclerView)findViewById(R.id.form_recycler);
        adapter = new FormDisplayAdapter(formList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Dynamically Fetch Views
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                formList.clear();
                progressBar.setIndeterminate(true);
                for(DataSnapshot shot: dataSnapshot.getChildren()){
                    Log.e("TAG",shot.child("formTitle").getValue(String.class));
                    FormDisplayObject formDisplayObject = new FormDisplayObject(shot.child("formTitle").getValue(String.class));
                    formDisplayObject.setFormId(shot.getKey());
                    formList.add(formDisplayObject);
                }
                progressBar.setIndeterminate(false);
                progressBar.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Fierbase Error",databaseError.toString());
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseFormReference.addValueEventListener(mValueEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseFormReference.removeEventListener(mValueEventListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if(id ==  R.id.nav_sing_out){

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
