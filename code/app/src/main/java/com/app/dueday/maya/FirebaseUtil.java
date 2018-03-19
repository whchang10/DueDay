package com.app.dueday.maya;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtil {
    private static String mUserDBPath = "all users";
    private static String mProjectDBPath = "all projects";

    private static String mEmail;
    private static String mUserID;

    private FirebaseUtil() {

    }

    public static void initFirebaseUtil(String email) {
        setEmail(email);
    }

    public static boolean isInitialized() {
        return mEmail != null;
    }

    private static void setEmail(String email) {
        mEmail = email;
        mUserID = emailToNode(email);
    }

    public static String getEmail() {
        return mEmail;
    }
    public static String getUserID() {
        return mUserID;
    }

    public static String emailToNode(String email) {
        return email.replaceAll("@", "_").replaceAll("\\.", "*");
    }

    public static String nodeToEmail(String node) {
        return node.replaceAll("_", "@").replaceAll("\\*", ".");
    }

    public static FirebaseDatabase getRootDB() {
        return FirebaseDatabase.getInstance();
    }
    public static DatabaseReference getAllUserRef() { return FirebaseDatabase.getInstance().getReference(mUserDBPath); }
    public static DatabaseReference getAllProjectRef() { return FirebaseDatabase.getInstance().getReference(mProjectDBPath); }

    public static DatabaseReference getUserRef(String userID) { return FirebaseDatabase.getInstance().getReference(mUserDBPath).child(userID); }
    public static DatabaseReference getProjectRef(String projectID) { return FirebaseDatabase.getInstance().getReference(mProjectDBPath).child(projectID); }

    public static DatabaseReference getCurrentUserRef() { return FirebaseDatabase.getInstance().getReference(mUserDBPath).child(mUserID); }

    public static void updateCurrentUserData(User user) {
        getCurrentUserRef().setValue(user);
    }
}