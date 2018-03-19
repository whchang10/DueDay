package com.app.dueday.maya;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.List;

import android.app.DatePickerDialog;
import android.widget.TextView;
import java.text.DateFormat;
import android.app.Dialog;

public class AddEvent extends AppCompatActivity {

    private TextView startDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Button add = findViewById(R.id.btn_addEvent);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
//                Intent intent = new Intent(getApplicationContext(), ProjectCalendar.class);
//                startActivity(intent);
//                finish();

                int HOUR_OF_DAY = 2;
                int MINUTE = 0;
                int MONTH= 3;
                int YEAR= 2017;

                int HOUR = 2;

                //////////////////
                List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
                int newMonth = 0;
                int newYear = 0;
                String EventTitle = "";

                java.util.Calendar startTime = java.util.Calendar.getInstance();
                startTime.set(java.util.Calendar.HOUR_OF_DAY, HOUR_OF_DAY);
                startTime.set(java.util.Calendar.MINUTE, MINUTE);
                startTime.set(java.util.Calendar.MONTH, MONTH);
                startTime.set(java.util.Calendar.YEAR, YEAR);
                java.util.Calendar endTime = (java.util.Calendar) startTime.clone();
                endTime.add(java.util.Calendar.HOUR, HOUR);
                endTime.set(java.util.Calendar.MONTH, MONTH);
                WeekViewEvent event = new WeekViewEvent(1, EventTitle, startTime, endTime);
                event.setColor(getResources().getColor(R.color.event_color_01));
                events.add(event);
            }
        });
    }

    //////////////


}
