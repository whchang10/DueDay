package com.app.dueday.maya;


import java.util.Date;

public class MayaEvent {
    public String id;
    public String name;
    public String location;
    public String details;

    public Date beginTime;
    public Date endTime;

    public MayaEvent() {

    }

    public MayaEvent(String name, String location, String details) {
        this.name = name;
        this.location = location;
        this.details = details;
    }

    public MayaEvent(String name, String location, String details, Date beginTime, Date endTime) {
        this(name, location, details);

        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public boolean isPersonalEvent() {
        return true;
    }

    public boolean isGroupEvent() {
        return false;
    }

    public void setDuration(Date beginTime, Date endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
}
