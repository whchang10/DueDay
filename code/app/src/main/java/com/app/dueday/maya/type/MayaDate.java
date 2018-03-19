package com.app.dueday.maya.type;

public class MayaDate {
    public int year;
    public int month;
    public int day;

    public int hourOfDay;
    public int minute;

    public MayaDate() {

    }

    public MayaDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public MayaDate(int year, int month, int day, int hourOfDay, int minute) {
        this(year, month, day);
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }

}
