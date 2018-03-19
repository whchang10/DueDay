package com.app.dueday.maya;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.dueday.maya.type.MayaEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class PersonalCalendar extends AppCompatActivity {

    private List<MayaEvent> eventCollection;
    private void readMayaEvent() {
        FirebaseUtil.initFirebaseUtil("mayatest@test.com");
        FirebaseUtil.getCurrentUserEventListRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<MayaEvent>> genericTypeIndicator = new GenericTypeIndicator<List<MayaEvent>>(){};
                eventCollection = dataSnapshot.getValue(genericTypeIndicator);

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

        /****************/
        // read test data, remove these line if you complete implemention
        readMayaEvent();
        /****************/

        //setTitle("My ProjectCalendar");

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        CalendarView myCalendarView = ((CalendarView)findViewById(R.id.calendar_view));
        myCalendarView.updateCalendar(events);

        // assign event handler
        myCalendarView.setEventHandler(new CalendarView.EventHandler()
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
                intent = new Intent(getApplicationContext(), AddEvent.class);

                startActivity(intent);
            }
        });
    }
}
