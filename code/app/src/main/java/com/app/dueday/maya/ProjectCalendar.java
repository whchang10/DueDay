package com.app.dueday.maya;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

//import android.widget.CalendarView;

public class ProjectCalendar extends AppCompatActivity {
    //CalendarView myCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_project_calendar);

        Intent intent = getIntent();
        String projName = intent.getStringExtra(MainActivity.EXTRA_PName);

        setTitle(projName);



        HashSet<Date> events = new HashSet<>();
        events.add(new Date());
        //myCalendarView = (CalendarView) findViewById(R.id.CalendarView); // get the reference of CalendarView

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
                Toast.makeText(ProjectCalendar.this, df.format(date), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ProjectCalendar.this, BasicActivity.class);
                startActivity(intent);
            }
        });



//        myCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                // display the selected date by using a toast
//                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
//            }
//        });



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
