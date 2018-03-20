package com.app.dueday.maya;

import com.app.dueday.maya.type.Project;
import com.app.dueday.maya.type.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FirebaseUtil {
    private static final String USER_DB_PATH = "all users";
    private static final String PROJCT_DB_PATH = "all projects";

    private static String mEmail;
    private static String mUserID;

    private static User mUser;

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

    public static User getCurrentUser() {
        assert mUser != null;
        return mUser;
    }

    public static void setCurrentUser(User user) {
        mUser = user;
        setEmail(user.email);
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
    public static DatabaseReference getAllUserRef() {
        return FirebaseDatabase.getInstance().getReference(USER_DB_PATH);
    }
    public static DatabaseReference getAllProjectRef() {
        return FirebaseDatabase.getInstance().getReference(PROJCT_DB_PATH);
    }

    public static DatabaseReference getUserRef(String userID) {
        return FirebaseDatabase.getInstance().getReference(USER_DB_PATH).child(userID);
    }
    public static DatabaseReference getAllProjectRefByID(String projectID) {
        return FirebaseDatabase.getInstance().getReference(PROJCT_DB_PATH).child(projectID);
    }
    public static void updateAllProjectByID(String projectID, Project project) {
        getAllProjectRefByID(projectID).setValue(project);
    }

    public static DatabaseReference getCurrentUserRef() { return FirebaseDatabase.getInstance().getReference(USER_DB_PATH).child(mUserID); }
    public static void updateCurrentUserData(User user) {
        getCurrentUserRef().setValue(user);
    }

    public static DatabaseReference getCurrentUserEventListRef() {
        return FirebaseDatabase.getInstance().getReference(USER_DB_PATH).child(mUserID).child(User.EVENT_COLLECTION);
    }
    public static void updateCurrentUserEventList() {
        getCurrentUserEventListRef().setValue(mUser.eventCollection);
    }

    public static DatabaseReference getCurrentUserProjectListRef() {
        return FirebaseDatabase.getInstance().getReference(USER_DB_PATH).child(mUserID).child(User.PROJECT_COLLECTION);
    }
    public static void updateCurrentUserPrjectList() {
        getCurrentUserProjectListRef().setValue(mUser.projectCollection);
    }

    public static DatabaseReference getUserProjectListRef(String userID) {
        return FirebaseDatabase.getInstance().getReference(USER_DB_PATH).child(userID).child(User.PROJECT_COLLECTION);
    }
    public static void updateUserPrjectList(String userID, List<Project> projectCollection) {
        getUserProjectListRef(userID).setValue(projectCollection);
    }
}
