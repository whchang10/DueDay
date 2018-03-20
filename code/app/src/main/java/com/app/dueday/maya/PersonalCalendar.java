package com.app.dueday.maya;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.dueday.maya.type.MayaEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class PersonalCalendar extends AppCompatActivity {
    private HashSet<Date> mEvents;
    private List<MayaEvent> eventCollection;
    CalendarView mCalendarView;

    private void readMayaEvent() {
        FirebaseUtil.getCurrentUserEventListRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<MayaEvent>> genericTypeIndicator = new GenericTypeIndicator<List<MayaEvent>>(){};
                eventCollection = dataSnapshot.getValue(genericTypeIndicator);
                for (MayaEvent mayaEvent : eventCollection) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                    String dateInString = mayaEvent.beginTime.day + "/" + mayaEvent.beginTime.month + "/" + mayaEvent.beginTime.year;
                    try {
                        Date dateToAdd = sdf.parse(dateInString);
                        mEvents.add(dateToAdd);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //add a date
                }
                mCalendarView.updateCalendar(mEvents);
                Log.d(UIUtil.TAG, "get data success");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(UIUtil.TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_calendar);
        mEvents = new HashSet<>();
        readMayaEvent();

        //setTitle("My ProjectCalendar");

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
                Toast.makeText(PersonalCalendar.this, df.format(date), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PersonalCalendar.this, BasicActivity.class);
                startActivity(intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btn_addEvent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getApplicationContext(), AddPersonalEvent.class);

                startActivity(intent);
            }
        });
    }
}
