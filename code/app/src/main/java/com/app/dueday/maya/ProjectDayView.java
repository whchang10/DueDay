package com.app.dueday.maya;

import android.os.Bundle;

import com.alamkanak.weekview.WeekViewEvent;
import com.app.dueday.maya.type.MayaEvent;
import com.app.dueday.maya.type.Project;
import com.app.dueday.maya.type.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A basic example of how to use week view library.
 * Created by Raquib-ul-Alam Kanak on 1/3/2014.
 * Website: http://alamkanak.github.io
 */
public class ProjectDayView extends BaseActivity {
    private List<WeekViewEvent> events;

    private Project mProject;
    private List<User> mMembersCollection;

    private void prepareEvents() {
        events.clear();
        int id = 0;
        for (User user : mMembersCollection) {
            if (user.eventCollection == null)
                continue;
            for (MayaEvent mayaEvent : user.eventCollection) {
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

                WeekViewEvent event;
                // Current user personal event
                if (FirebaseUtil.getCurrentUser().id.equals(user.id) && !mProject.id.equals(mayaEvent.projectID)) {
                    event = new WeekViewEvent(id++, mayaEvent.name + "\n\n", "Location: " + mayaEvent.location + "\n\n" + " Descriptions: " + mayaEvent.details, startTime, endTime);
                    event.setColor(getResources().getColor(R.color.event_color_03));
                    events.add(event);
                }
                // Current project event
                else if (FirebaseUtil.getCurrentUser().id.equals(user.id) && mProject.id.equals(mayaEvent.projectID)){
                    event = new WeekViewEvent(id++, mayaEvent.name + "\n\n", "Location: " + mayaEvent.location + "\n\n" + " Descriptions: " + mayaEvent.details, startTime, endTime);
                    event.setColor(getResources().getColor(R.color.event_color_04));
                    events.add(event);
                }
                // Other person event exclude current project event
                else if (!FirebaseUtil.getCurrentUser().id.equals(user.id) && !mProject.id.equals(mayaEvent.projectID)) {
                    event = new WeekViewEvent(id++, "Private" + "\n\n", "No Information", startTime, endTime);
                    event.setColor(getResources().getColor(R.color.event_color_01));
                    events.add(event);
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        events = new ArrayList<WeekViewEvent>();

        mProject = (Project) getIntent().getSerializableExtra(ProjectCalendar.EXTRA_PROJECT);
        mMembersCollection = (ArrayList<User>) getIntent().getSerializableExtra(ProjectCalendar.EXTRA_MEMBERS_COLLECTION);

        Date date = (Date) getIntent().getSerializableExtra(UIUtil.GOTO_DATE);
        Calendar moveToDate = Calendar.getInstance();
        moveToDate.setTime(date);
        getWeekView().goToDate(moveToDate);

        prepareEvents();
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
