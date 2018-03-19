package com.app.dueday.maya;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project {
    public String id;
    public String name;
    public User leader;
    // add tag
    public String description;
    public List<User> memberCollection;

    public Date startDate;
    public Date endDate;

    public Project() {

    }

    public Project(String id, String name, User leader) {
        this.id = id;
        this.name = name;
        this.leader = leader;

        memberCollection = new ArrayList<>();
    }

    public Project(String id, String name, User leader, String description, Date startDate, Date endDate) {
        this(id, name, leader);
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void addMember(User user) {
        memberCollection.add(user);
    }

    public void removeMember(User user) {
        memberCollection.remove(user);
    }

    public void setDuration(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
