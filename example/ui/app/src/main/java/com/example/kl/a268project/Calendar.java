package com.example.kl.a268project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

public class Calendar extends AppCompatActivity {
    CalendarView myCalendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);

        Intent intent = getIntent();
        String projName = intent.getStringExtra(HomePage.EXTRA_PName);

        setTitle(projName);



        myCalendarView = (CalendarView) findViewById(R.id.CalendarView); // get the reference of CalendarView


//        myCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                // display the selected date by using a toast
//                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
