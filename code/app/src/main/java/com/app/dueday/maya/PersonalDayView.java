package com.app.dueday.maya;

import android.os.Bundle;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;
import com.app.dueday.maya.type.MayaEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A basic example of how to use week view library.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 */
public class PersonalDayView extends BaseActivity {
    private List<WeekViewEvent> events;
    private List<MayaEvent> eventCollection;
    private void readMayaEvent() {
        FirebaseUtil.getCurrentUserEventListRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    GenericTypeIndicator<List<MayaEvent>> genericTypeIndicator = new GenericTypeIndicator<List<MayaEvent>>() {
                    };
                    eventCollection = dataSnapshot.getValue(genericTypeIndicator);
                    events.clear();
                    int id = 0;
                    for (MayaEvent mayaEvent : eventCollection) {
                        Calendar startTime = Calendar.getInstance();
                        startTime = Calendar.getInstance();
                        startTime.set(Calendar.DATE, mayaEvent.beginTime.day);
                        startTime.set(Calendar.HOUR_OF_DAY, mayaEvent.beginTime.hourOfDay);
                        startTime.set(Calendar.MINUTE, mayaEvent.beginTime.minute);
                        startTime.set(Calendar.MONTH, mayaEvent.beginTime.month - 1);
                        startTime.set(Calendar.YEAR, mayaEvent.beginTime.year);
                        Calendar endTime = Calendar.getInstance();
                        endTime = Calendar.getInstance();
                        endTime.set(Calendar.DATE, mayaEvent.endTime.day);
                        endTime.set(Calendar.HOUR_OF_DAY, mayaEvent.endTime.hourOfDay);
                        endTime.set(Calendar.MINUTE, mayaEvent.endTime.minute);
                        endTime.set(Calendar.MONTH, mayaEvent.endTime.month - 1);
                        endTime.set(Calendar.YEAR, mayaEvent.endTime.year);
                        WeekViewEvent event = new WeekViewEvent(id++, mayaEvent.name + "\n\n", "Location: " + mayaEvent.location + "\n\n" + " Descriptions: " + mayaEvent.details, startTime, endTime);
                        if (!mayaEvent.personalEvent) {
                            event.setColor(getResources().getColor(R.color.event_color_04));
                        } else {
                            event.setColor(getResources().getColor(R.color.event_color_03));
                        }

                        events.add(event);
                    }
                    Log.d(UIUtil.TAG, "get data success");
                    getWeekView().notifyDatasetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(UIUtil.TAG, "Failed to read value.", error.toException());
            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        events = new ArrayList<WeekViewEvent>();
        Date date = (Date) getIntent().getSerializableExtra(UIUtil.GOTO_DATE);
        Calendar moveToDate = Calendar.getInstance();
        moveToDate.setTime(date);
        getWeekView().goToDate(moveToDate);
        readMayaEvent();
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        ArrayList<WeekViewEvent> eventsMonth = new ArrayList<WeekViewEvent>();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getStartTime().get(Calendar.MONTH) == newMonth) {
                eventsMonth.add(events.get(i));
            }
        }
        return eventsMonth;
    }

}
