package com.app.dueday.maya;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dueday.maya.type.MayaEvent;
import com.app.dueday.maya.type.Project;
import com.app.dueday.maya.type.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

//import android.widget.CalendarView;

public class ProjectCalendar extends AppCompatActivity {
    private HashSet<Date> mEvents;
    private List<MayaEvent> eventCollection;
    CalendarView mCalendarView;

    private Project mProject;
    private List<User> membersCollection;

    public static final String EXTRA_PROJECT = "Project";
    public static final String EXTRA_MEMBERS_COLLECTION = "MembersCollection";

    private void readMemebersEvent() {
        FirebaseUtil.getAllUserRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mEvents.clear();
                    membersCollection.clear();
                    eventCollection.clear();

                    for (User userLight : mProject.memberCollection) {
                        User user = dataSnapshot.child(userLight.id).getValue(User.class);
                        membersCollection.add(user);
                        eventCollection.addAll(user.eventCollection);
                    }

                    for (MayaEvent mayaEvent : eventCollection) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                        String dateInString = mayaEvent.beginTime.day + "/" + mayaEvent.beginTime.month + "/" + mayaEvent.beginTime.year;
                        try {
                            Date dateToAdd = sdf.parse(dateInString);
                            mEvents.add(dateToAdd);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    mCalendarView.updateCalendar(mEvents);
                    Log.d(UIUtil.TAG, "get data success");
                }
                else {
                    Log.d(UIUtil.TAG, "No data exists");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(UIUtil.TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void setMemberListString() {
        TextView memberTextView = (TextView) findViewById(R.id.members);
        StringBuilder memberListString = new StringBuilder();
        String header = "Members: \n";
        memberListString.append(header);

        for (User user : mProject.memberCollection) {
            memberListString.append(user.name).append(" (").append(user.email).append(")\n");
        }

        memberTextView.setText(memberListString.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_calendar);
        mEvents = new HashSet<>();
        membersCollection = new ArrayList<>();
        eventCollection = new ArrayList<>();

        mProject = (Project) getIntent().getSerializableExtra(MainActivity.EXTRA_PROJECT);
        setTitle(mProject.name);

        setMemberListString();
        readMemebersEvent();

        mCalendarView = ((CalendarView)findViewById(R.id.calendar_view));
        mCalendarView.updateCalendar(mEvents);

        // assign event handler
        mCalendarView.setEventHandler(new CalendarView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(ProjectCalendar.this, df.format(date), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ProjectCalendar.this, ProjectDayView.class);
                intent.putExtra(EXTRA_PROJECT, mProject);

//                ArrayList<User> memberCollectionLight = new ArrayList<>();
//                for (User user : membersCollection) {
//                    User userLight = new User(user.id, user.name, user.email);
//                    userLight.eventCollection = user.eventCollection;
//                    memberCollectionLight.add(userLight);
//                }
                intent.putExtra(EXTRA_MEMBERS_COLLECTION, (ArrayList<User>) membersCollection);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_addEvent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getApplicationContext(), AddProjectEvent.class);

                intent.putExtra(EXTRA_PROJECT, mProject);
                intent.putExtra(EXTRA_MEMBERS_COLLECTION, (ArrayList<User>) membersCollection);

                startActivity(intent);
            }
        });
    }


}
