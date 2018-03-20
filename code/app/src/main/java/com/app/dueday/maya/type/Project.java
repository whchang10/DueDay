package com.app.dueday.maya.type;


import java.util.ArrayList;
import java.util.List;

public class Project implements java.io.Serializable{
    public String id;
    public String name;
    public User leader;
    public String tag;
    public String description;

    public List<User> memberCollection;

    public Project() {

    }

    public Project(String name, String tag, String description, User leader, String leaderID) {
        this.id = name + "+" + leaderID + "+" + tag;
        this.name = name;
        this.leader = leader;
        this.tag = tag;
        this.description = description;
        memberCollection = new ArrayList<>();
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void addMember(User user) {
        if (memberCollection == null) {
            memberCollection = new ArrayList<>();
        }

        memberCollection.add(user);
    }

    public void removeMember(User user) {
        assert memberCollection != null;

        memberCollection.remove(user);
    }

}
