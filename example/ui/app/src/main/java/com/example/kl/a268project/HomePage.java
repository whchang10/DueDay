package com.example.kl.a268project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.TypedValue;


import java.util.LinkedList;
import java.util.List;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView mylistview;
    private List<String> myProjects;
    public static final String EXTRA_PName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ///////////////////////////////////////////////////////////////////////////////////////////
        mylistview = (ListView) findViewById(R.id.projectList);

        myProjects = new LinkedList<String>();
        myProjects.add("PROJECT 1");
        myProjects.add("PROJECT 2");
        myProjects.add("PROJECT 3");
        myProjects.add("PROJECT 4");
        myProjects.add("PROJECT 5");


        // set up ListView content
        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, myProjects){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                TextView ListItemShow = (TextView) view.findViewById(android.R.id.text1);
                ListItemShow.setTextSize(TypedValue.COMPLEX_UNIT_DIP,50);
                if(position %2 == 1)
                {
                    // Set a background color for ListView regular row/item
                    view.setBackgroundResource(R.color.main);
                    ListItemShow.setTextColor(getResources().getColor(R.color.sub));
                }
                else
                {
                    // Set the background color for alternate row/item
                    view.setBackgroundResource(R.color.sub);
                    ListItemShow.setTextColor(getResources().getColor(R.color.main));
                }
                return view;
            }
        };

        // DataBind ListView with items from ArrayAdapter
        mylistview.setAdapter(arrayAdapter);
        mylistview.setOnItemClickListener(onClickProjListView);



    }


    /***
     * pass selected text to the next activity
     */
    private AdapterView.OnItemClickListener onClickProjListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent;
            intent = new Intent(getApplicationContext(), Calendar.class);

            String projectName = myProjects.get(position);
            intent.putExtra(EXTRA_PName, projectName);
            startActivity(intent);
        }
    };


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_new) {
            Intent intent;
            intent = new Intent(getApplicationContext(), CreateNew.class);

            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_setting) {
            Intent intent;
            intent = new Intent(getApplicationContext(), Setting.class);

            startActivity(intent);
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
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
