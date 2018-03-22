package com.app.dueday.maya.type;

import com.app.dueday.maya.type.MayaEvent;
import com.app.dueday.maya.type.Project;

import java.util.ArrayList;
import java.util.List;

public class User implements java.io.Serializable{
    public String id;
    public String name;
    public String email;
    public String phoneNumber;
    public String photoUrl;

    public List<MayaEvent> eventCollection;
    public List<Project> projectCollection;

    public static final String EVENT_COLLECTION = "eventCollection";
    public static final String PROJECT_COLLECTION = "projectCollection";

    public User () {

    }

    public User (String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
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

    public List<MayaEvent> getEventCollection() {
         if (eventCollection == null ) {
             eventCollection = new ArrayList<MayaEvent>();
         }
         return eventCollection;
    }

    public List<Project> getProjectCollection() {
        if (projectCollection == null) {
            projectCollection = new ArrayList<Project>();
        }
        return projectCollection;
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

}