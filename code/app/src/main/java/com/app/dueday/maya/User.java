package com.app.dueday.maya;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String id;
    public String name;
    public String email;
    public String phoneNumber;
    public String photoUrl;

    public List<Project> projectCollection;
    public List<MayaEvent> eventCollection;

    public User () {

    }

    public User (String id, String name, String email, String phoneNumber, String photoUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber == null? "" : phoneNumber;
        this.photoUrl = photoUrl;

        projectCollection = new ArrayList<>();
        eventCollection = new ArrayList<>();
    }

    public void addProject(Project project) {
        projectCollection.add(project);
    }

    public void removeProject(Project project) {
        projectCollection.remove(project);
    }

    public void addEvent(MayaEvent event) {
        eventCollection.add(event);
    }

    public void removeEvent(MayaEvent event) {
        eventCollection.remove(event);
    }

}