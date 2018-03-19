package com.app.dueday.maya.type;

import com.app.dueday.maya.type.MayaEvent;
import com.app.dueday.maya.type.Project;

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

    public static final String EVENT_COLLECTION = "eventCollection";

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
        if (projectCollection == null) {
            projectCollection = new ArrayList<>();
        }
        projectCollection.add(project);
    }

    public void removeProject(Project project) {
        assert project != null;
        projectCollection.remove(project);
    }

    public void addEvent(MayaEvent event) {
        if (eventCollection == null) {
            eventCollection = new ArrayList<>();
        }
        eventCollection.add(event);
    }

    public void removeEvent(MayaEvent event) {
        assert event != null;
        eventCollection.remove(event);
    }

}