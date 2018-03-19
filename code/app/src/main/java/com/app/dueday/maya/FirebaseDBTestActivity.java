package com.app.dueday.maya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.app.dueday.maya.type.MayaDate;
import com.app.dueday.maya.type.MayaEvent;
import com.app.dueday.maya.type.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FirebaseDBTestActivity extends AppCompatActivity {

    static int year = 2018;
    static int month = 3;
    static int day = 19;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_dbtest);

        //newUserAndUpdateUser();
        //newMayaEventTest();
        //newOneMayaEventTest();
        //newMayaEventWithDateTest();
        //readMayaEventTest();

        //createTestData();

        Log.d(UIUtil.TAG, "UserAccTest complete");
    }

    private void createTestData() {
        FirebaseUtil.initFirebaseUtil("mayatest@test.com");
        FirebaseUtil.getCurrentUserRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User me = dataSnapshot.getValue(User.class);
                FirebaseUtil.setCurrentUser(me);
                MayaDate beginTime = new MayaDate(year, month, day, 9,30);
                MayaDate endTime = new MayaDate(year, month, day , 14,00);
                MayaEvent mayaEvent = new MayaEvent("To office", "office", "Work", true, beginTime, endTime);

                MayaDate beginTime1 = new MayaDate(year, month, day + 1, 9,30);
                MayaDate endTime1 = new MayaDate(year, month, day + 1, 14,00);
                MayaEvent mayaEvent1 = new MayaEvent("To school", "school", "To school to take a class", true, beginTime1, endTime1);

                MayaDate beginTime2 = new MayaDate(year, month, day + 2, 9,30);
                MayaDate endTime2 = new MayaDate(year, month, day + 2, 14,00);
                MayaEvent mayaEvent2 = new MayaEvent("meet my teacher", "Santa clara University", "For assignments", true, beginTime2, endTime2);

                MayaDate beginTime3 = new MayaDate(year, month, day - 1, 9,30);
                MayaDate endTime3 = new MayaDate(year, month, day - 1, 14,00);
                MayaEvent mayaEvent3 = new MayaEvent("Eat dinner", "home", "Eat dinner at home", true, beginTime3, endTime3);

                MayaDate beginTime4 = new MayaDate(year, month, day +3, 9,30);
                MayaDate endTime4 = new MayaDate(year, month, day + 3, 14,00);
                MayaEvent mayaEvent4 = new MayaEvent("Meeting", "office", "Discuss", false, beginTime4, endTime4);

                FirebaseUtil.getCurrentUser().addEvent(mayaEvent);
                FirebaseUtil.getCurrentUser().addEvent(mayaEvent1);
                FirebaseUtil.getCurrentUser().addEvent(mayaEvent2);
                FirebaseUtil.getCurrentUser().addEvent(mayaEvent3);
                FirebaseUtil.getCurrentUser().addEvent(mayaEvent4);
                FirebaseUtil.updateCurrentUserEventList();
                Log.d(UIUtil.TAG, "test end");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(UIUtil.TAG, "Failed to read value.", error.toException());
            }
        });
    }
    private void readMayaEventTest() {
        FirebaseUtil.initFirebaseUtil("whchang10@gmail.com");
        FirebaseUtil.getCurrentUserEventListRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<MayaEvent>> genericTypeIndicator = new GenericTypeIndicator<List<MayaEvent>>(){};
                List<MayaEvent> eventCollection = dataSnapshot.getValue(genericTypeIndicator);

                Log.d(UIUtil.TAG, "test end");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(UIUtil.TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void newMayaEventWithDateTest() {
        FirebaseUtil.initFirebaseUtil("whchang10@gmail.com");
        FirebaseUtil.getCurrentUserRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User me = dataSnapshot.getValue(User.class);
                FirebaseUtil.setCurrentUser(me);
                MayaDate beginTime = new MayaDate(year, month, day, 9,30);
                MayaDate endTime = new MayaDate(year, month, day , 14,00);
                MayaEvent mayaEvent = new MayaEvent("To office", "office", "Work", true, beginTime, endTime);

                MayaDate beginTime1 = new MayaDate(year, month, day + 1, 9,30);
                MayaDate endTime1 = new MayaDate(year, month, day + 1, 14,00);
                MayaEvent mayaEvent1 = new MayaEvent("To school", "school", "To school to take a class", true, beginTime1, endTime1);

                MayaDate beginTime2 = new MayaDate(year, month, day + 2, 9,30);
                MayaDate endTime2 = new MayaDate(year, month, day + 2, 14,00);
                MayaEvent mayaEvent2 = new MayaEvent("meet my teacher", "Santa clara University", "For assignments", true, beginTime2, endTime2);

                MayaDate beginTime3 = new MayaDate(year, month, day - 1, 9,30);
                MayaDate endTime3 = new MayaDate(year, month, day - 1, 14,00);
                MayaEvent mayaEvent3 = new MayaEvent("Eat dinner", "home", "Eat dinner at home", true, beginTime3, endTime3);

                MayaDate beginTime4 = new MayaDate(year, month, day +3, 9,30);
                MayaDate endTime4 = new MayaDate(year, month, day + 3, 14,00);
                MayaEvent mayaEvent4 = new MayaEvent("Meeting", "office", "Discuss", false, beginTime4, endTime4);

                FirebaseUtil.getCurrentUser().addEvent(mayaEvent);
                FirebaseUtil.getCurrentUser().addEvent(mayaEvent1);
                FirebaseUtil.getCurrentUser().addEvent(mayaEvent2);
                FirebaseUtil.getCurrentUser().addEvent(mayaEvent3);
                FirebaseUtil.getCurrentUser().addEvent(mayaEvent4);
                FirebaseUtil.updateCurrentUserEventList();
                Log.d(UIUtil.TAG, "test end");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(UIUtil.TAG, "Failed to read value.", error.toException());
            }
        });
    }


    private void newOneMayaEventTest() {
        FirebaseUtil.initFirebaseUtil("whchang10@gmail.com");
        FirebaseUtil.getCurrentUserRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User me = dataSnapshot.getValue(User.class);
                FirebaseUtil.setCurrentUser(me);
                MayaEvent mayaEvent = new MayaEvent("To school", "school", "To school to take a class", true);

                FirebaseUtil.getCurrentUser().addEvent(mayaEvent);
                FirebaseUtil.updateCurrentUserEventList();
                Log.d(UIUtil.TAG, "test end");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(UIUtil.TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void newMayaEventTest() {
        FirebaseUtil.initFirebaseUtil("whchang10@gmail.com");
        FirebaseUtil.getCurrentUserRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User me = dataSnapshot.getValue(User.class);
                FirebaseUtil.setCurrentUser(me);
                String name = "meet my teacher";
                String location = "Santa clara University";
                String details = "For assignments";
                boolean personal = true;
                MayaEvent mayaE = new MayaEvent(name, location, details, personal);

                MayaEvent mayaE2 = new MayaEvent("Eat dinner", "home", "Eat dinner at home", true);

                FirebaseUtil.getCurrentUser().addEvent(mayaE);
                FirebaseUtil.getCurrentUser().addEvent(mayaE2);
                //FirebaseUtil.updateCurrentUserData(FirebaseUtil.getCurrentUser());
                FirebaseUtil.updateCurrentUserEventList();
                Log.d(UIUtil.TAG, "test end");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(UIUtil.TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void newUserAndUpdateUser() {
        FirebaseUtil.initFirebaseUtil("whchang10@gmail.com");
        String userID = FirebaseUtil.getUserID();

        User me = new User(userID, "WenHan", "whchang10@gmail.com", "669-265-2138", null);

        FirebaseUtil.updateCurrentUserData(me);

        FirebaseUtil.getCurrentUserRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                @SuppressWarnings("unchecked")
//                Map<String, String> map = (Map)dataSnapshot.getValue();
//                Log.d(MainActivity.TAG, "Value is: " + map);

                // test existence
//                boolean exist = dataSnapshot.exists();
//                System.out.println(exist);

                User me = dataSnapshot.getValue(User.class);
                Log.d(UIUtil.TAG, "test end");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(UIUtil.TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
