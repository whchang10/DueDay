package com.app.dueday.maya;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;


public class PersonalCalendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_calendar);

        //setTitle("My Calendar");

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
