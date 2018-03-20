package com.app.dueday.maya.type;

import java.util.ArrayList;
import java.util.List;

public class MayaEvent implements java.io.Serializable{
    public String id;
    public String name;
    public String location;
    public String details;
    public boolean personalEvent;

    public MayaDate beginTime;
    public MayaDate endTime;

    public String projectID;
    public List<User> attendeeCollection;

    public MayaEvent() {

    }

    public MayaEvent(String name, String location, String details, boolean personalEvent) {
        this.id = name + "+" + location + "+" + details + "+" + personalEvent;
        this.name = name;
        this.location = location;
        this.details = details;
        this.personalEvent = personalEvent;
    }

    public MayaEvent(String name, String location, String details, boolean personal, MayaDate beginTime, MayaDate endTime) {
        this(name, location, details, personal);

        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public boolean isPersonalEvent() {
        return personalEvent;
    }

    public void setDuration(MayaDate beginTime, MayaDate endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public MayaDate getBeginTime() {
        if (beginTime == null) {
            beginTime = new MayaDate();
        }

        return beginTime;
    }

    public MayaDate getEndTime() {
        if (endTime == null) {
            endTime = new MayaDate();
        }

        return endTime;
    }

    public void addAttendee(User user) {
        if (attendeeCollection == null) {
            attendeeCollection = new ArrayList<>();
        }
        attendeeCollection.add(user);
    }

    public void removeAttendee(User user) {
        assert attendeeCollection != null;
        attendeeCollection.remove(user);
    }
}
