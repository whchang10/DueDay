package com.app.dueday.maya;


import java.util.Date;
import java.util.List;

public class MayaEvent {
    public String id;
    public String name;
    public String location;
    public String details;
    public boolean mPersonal;

    public Date beginTime;
    public Date endTime;

    public String projectID;
    public List<User> attendeeCollection;

    public MayaEvent() {

    }

    public MayaEvent(String name, String location, String details, boolean personal) {
        this.name = name;
        this.location = location;
        this.details = details;
        this.mPersonal = personal;
    }

    public MayaEvent(String name, String location, String details, boolean personal, Date beginTime, Date endTime) {
        this(name, location, details, personal);

        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public boolean isPersonalEvent() {
        return mPersonal;
    }

    public void setDuration(Date beginTime, Date endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public void addAttendee(User user) {
        attendeeCollection.add(user);
    }

    public void removeAttendee(User user) {
        attendeeCollection.remove(user);
    }
}
