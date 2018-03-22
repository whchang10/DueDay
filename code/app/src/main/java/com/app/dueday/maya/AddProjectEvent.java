package com.app.dueday.maya;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.dueday.maya.type.MayaDate;
import com.app.dueday.maya.type.MayaEvent;
import com.app.dueday.maya.type.Project;
import com.app.dueday.maya.type.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


public class AddProjectEvent extends AppCompatActivity {
    private Project mProject;
    private List<User> mMembersCollection;

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            mPickerInput.setText(hourOfDay + " : " + minute);
            Log.d(UIUtil.TAG, "onTimeSet");
        }

        public void setOutputView(View view) {
            mPickerInput = (EditText) view;
        }

        private EditText mPickerInput;
    }

    public void eventTimePicker(View v) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setOutputView(v);
        timePickerFragment.show(getFragmentManager(), "timePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            month = month + 1; // bug fix workaround
            mPickerInput.setText(month + " / " + day + " / " + year);
            Log.d(UIUtil.TAG, "onDateSet");
        }

        public void setOutputView(View view) {
            mPickerInput = (EditText) view;
        }
        private EditText mPickerInput;
    }

    public void eventDatePicker(View v) {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setOutputView(v);
        datePickerFragment.show(getFragmentManager(), "datePicker");
    }

    private long convertMayaDateToTimeStamp(MayaDate mayaDate) {
        long timeStamp = 0;
        String dateString = "" + mayaDate.year + "/" + mayaDate.month + "/" + mayaDate.day + " "
                + mayaDate.hourOfDay + ":" + mayaDate.minute + ":" + "00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(dateString);
        } catch (Exception e){}

        timeStamp = date.getTime();

        return timeStamp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal_event);

        mProject = (Project) getIntent().getSerializableExtra(ProjectCalendar.EXTRA_PROJECT);
        mMembersCollection = (ArrayList<User>) getIntent().getSerializableExtra(ProjectCalendar.EXTRA_MEMBERS_COLLECTION);

        Button add = findViewById(R.id.btn_addEvent);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.eventTitle)).getText().toString();
                String location = ((EditText) findViewById(R.id.eventLocation)).getText().toString();
                String details = ((EditText) findViewById(R.id.details)).getText().toString();

                String startDate = ((EditText) findViewById(R.id.eventStartDate)).getText().toString();
                String endDate = ((EditText) findViewById(R.id.eventEndDate)).getText().toString();
                String startDateArray[] = startDate.split(" / ");
                String endDateArray[] = endDate.split(" / ");

                String startTime = ((EditText) findViewById(R.id.eventStartTime)).getText().toString();
                String endTime = ((EditText) findViewById(R.id.eventEndTime)).getText().toString();
                String startTimeArray[] = startTime.split(" : ");
                String endTimeArray[] = endTime.split(" : ");

                MayaDate begin = new MayaDate(
                        Integer.parseInt(startDateArray[2]),
                        Integer.parseInt(startDateArray[0]),
                        Integer.parseInt(startDateArray[1]),
                        Integer.parseInt(startTimeArray[0]),
                        Integer.parseInt(startTimeArray[1])
                );
                MayaDate end = new MayaDate(
                        Integer.parseInt(endDateArray[2]),
                        Integer.parseInt(endDateArray[0]),
                        Integer.parseInt(endDateArray[1]),
                        Integer.parseInt(endTimeArray[0]),
                        Integer.parseInt(endTimeArray[1])
                );

                long beginDateTS = convertMayaDateToTimeStamp(begin);
                long endDateTS = convertMayaDateToTimeStamp(end);

                for (User user : mMembersCollection) {
                    List<MayaEvent> eventCollection = user.getEventCollection();
                    for (MayaEvent event : eventCollection) {
                        long eventBeginDateTS = convertMayaDateToTimeStamp(event.beginTime);
                        long eventEndDateTS = convertMayaDateToTimeStamp(event.endTime);

                        if ((eventBeginDateTS <= beginDateTS && eventEndDateTS >= beginDateTS) ||
                                (eventBeginDateTS <= endDateTS && eventEndDateTS >= endDateTS) ||
                                (beginDateTS <= eventBeginDateTS && endDateTS >= eventEndDateTS)) {
                            UIUtil.getInstance().showToast(getApplicationContext(), "Cannot add event at busy hours.");
                            return;
                        }
                    }
                }

                MayaEvent newMayaEvent = new MayaEvent(name, location, details, false, begin, end);

                newMayaEvent.projectID = mProject.id;
                newMayaEvent.attendeeCollection = mProject.memberCollection;

                for (User user : mMembersCollection) {
                    user.addEvent(newMayaEvent);
                    FirebaseUtil.updateUserEventList(user.id, user.eventCollection);
                }

                Log.d(UIUtil.TAG, "Add new event complete");

                AddProjectEvent.this.finish();
            }
        });
    }
}
