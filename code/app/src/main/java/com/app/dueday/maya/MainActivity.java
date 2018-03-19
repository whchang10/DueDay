package com.app.dueday.maya;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.dueday.maya.type.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.app.dueday.maya.adapter.EventListAdapter;
import com.app.dueday.maya.module.EventListViewItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListView mListview;
    ArrayList<EventListViewItem> mProjectList;
    EventListAdapter mAdapter;
    public static final String EXTRA_PName = "";

    private static final int FIREBASE_SIGN_IN = 120;
    private static final int CREATE_PROJECT_R = 125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FirebaseUtil.isInitialized()) {
            processLogin();
        }

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
        mListview = (ListView) findViewById(R.id.projectList);

        mProjectList = new ArrayList<EventListViewItem>();

        // set up ListView content
        // Create an ArrayAdapter from List
        mAdapter = new EventListAdapter(this, mProjectList);

        // DataBind ListView with items from ArrayAdapter
        View header = (View)getLayoutInflater().inflate(R.layout.list_event_item, null);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(onClickProjListView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_addProject);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), CreateProject.class);
                startActivityForResult(intent, CREATE_PROJECT_R);
            }
        });

        processLogin();
    }

    private void processLogin() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        Intent loginIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();

        startActivityForResult(loginIntent, FIREBASE_SIGN_IN);
    }

    private static class NewUserHelper extends AsyncTask<User, Integer, Integer> {
        protected Integer doInBackground(User... users) {
            FirebaseUtil.updateCurrentUserData(users[0]);
            return 1;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Integer result) {
            Log.d(UIUtil.TAG, "New a User complete");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == CREATE_PROJECT_R) {
            if (resultCode == Activity.RESULT_OK) {
                String title = intent.getStringExtra("title");
                System.out.print("+++++");
                System.out.print(title);
                EventListViewItem e = new EventListViewItem(R.drawable.ic_launcher_foreground, title);
                mProjectList.add(e);
                mAdapter.notifyDataSetChanged();
            }
        }
        else if (requestCode == FIREBASE_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(intent);
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseUtil.initFirebaseUtil(user.getEmail());
                FirebaseUtil.getCurrentUserRef().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User currentUser;
                        if (!dataSnapshot.exists()) {
                            currentUser = new User(FirebaseUtil.getUserID(), user.getDisplayName(),
                                    user.getEmail(), user.getPhoneNumber(), user.getPhotoUrl() == null? "" : user.getPhotoUrl().toString());

                            new NewUserHelper().execute(currentUser);
                        }
                        else {
                            currentUser = dataSnapshot.getValue(User.class);
                        }

                        FirebaseUtil.setCurrentUser(currentUser);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.d(UIUtil.TAG, "Failed to read value.", error.toException());
                    }
                });
            } else {
                // Sign in failed, check response for error code
                // User cancel
                if (response == null) {
                    Log.d("==========", "User cancel login");
                }
                // No Internet
                else if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Log.d("==========", "No Internet service");
                }
                // Other error occur
                else if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Log.d("==========", "Other error occur");
                }
            }
        }
    }


    /***
     * pass selected text to the next activity
     */
    private AdapterView.OnItemClickListener onClickProjListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent;
            intent = new Intent(getApplicationContext(), Calendar.class);
            String projectName = mProjectList.get(position).title;
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home_page, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//
//        if (id == R.id.action_setting) {
//            Intent intent;
//            intent = new Intent(getApplicationContext(), Setting.class);
//
//            startActivity(intent);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_social) {
            // Handle the camera action
        } else if (id == R.id.nav_travel) {

        } else if (id == R.id.nav_work) {


        } else if (id == R.id.nav_own) {
            Intent intent;
            intent = new Intent(getApplicationContext(), PersonalCalendar.class);

            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
