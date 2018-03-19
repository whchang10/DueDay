package com.app.dueday.maya;

import java.util.Date;
import java.util.List;


public class MayaGroupEvent extends MayaEvent {
    public String projectID;
    public List<User> attendeeCollection;

    public MayaGroupEvent() {
        super();
    }

    public MayaGroupEvent(String name, String location, String details, String projectID) {
        super(name, location, details);
        this.projectID = projectID;
    }

    public MayaGroupEvent(String name, String location, String details, Date beginTime, Date endTime, String projectID) {
        super(name, location, details, beginTime, endTime);
        this.projectID = projectID;
    }

    @Override
    public boolean isPersonalEvent() {
        return false;
    }

    @Override
    public boolean isGroupEvent() {
        return true;
    }

    public void addAttendee(User user) {
        attendeeCollection.add(user);
    }

    public void removeAttendee(User user) {
        attendeeCollection.remove(user);
    }
}
