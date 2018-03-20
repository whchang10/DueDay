package com.app.dueday.maya;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.dueday.maya.type.Project;
import com.app.dueday.maya.type.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CreateProject extends AppCompatActivity {

    private List<User> mAllUsersCollection;
    private List<User> mMemberCollection;



    public static class MembersPickerFragment extends DialogFragment {
        private List<User> mPickerAllUsersCollection;
        private List<Integer> track;

        private List<User> mPickerMemberCollection;

        private TextView mMemberTextView;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            track = new ArrayList<>();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            List<String> selections = new ArrayList<>();
            for (User user : mPickerAllUsersCollection) {
                selections.add(user.name + " (" + user.email + ")");
            }

            String[] selectionsString = new String[selections.size()];
            selectionsString = selections.toArray(selectionsString);
            // Set the dialog title
            builder.setTitle("Select Members")
                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    .setMultiChoiceItems(selectionsString, null,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which,
                                                    boolean isChecked) {
                                    if (isChecked) {
                                        // If the user checked the item, add it to the selected items
                                        track.add(which);
                                    } else if (track.contains(which)) {
                                        // Else, if the item is already in the array, remove it
                                        track.remove(Integer.valueOf(which));
                                    }
                                }
                            })
                    // Set the action buttons
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        mPickerMemberCollection.clear();
                        StringBuilder memberListString = new StringBuilder();
                        String header = "Members: \n";
                        memberListString.append(header);
                        for(Integer index : track) {
                            User user = mPickerAllUsersCollection.get(index);
                            memberListString.append(user.name).append(" (").append(user.email).append(")\n");
                            mPickerMemberCollection.add(user);
                        }
                        if (!isSelfInMeberlist()) {
                            User user = FirebaseUtil.getCurrentUser();
                            mPickerMemberCollection.add(0, user);
                            memberListString.insert(header.length(), user.name + " (" + user.email + ")\n");
                        }
                        mMemberTextView.setText(memberListString.toString());
                        Log.d(UIUtil.TAG, "OK");
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            return builder.create();
        }

        private boolean isSelfInMeberlist() {
            for(User user : mPickerMemberCollection) {
                if (FirebaseUtil.getCurrentUser().id.equals(user.id)) {
                    return true;
                }
            }

            return false;
        }

        public void setAllMemberCollection(List<User> allUsersCollection) {
            mPickerAllUsersCollection = allUsersCollection;
        }

        public void setMemberCollection(List<User> memberCollection) {
            mPickerMemberCollection = memberCollection;
        }

        public void setOuputTextView(TextView memberTextView) {
            mMemberTextView = memberTextView;
        }
    }

    public void selectMembers(View v) {
        CreateProject.MembersPickerFragment membersPickerFragment = new CreateProject.MembersPickerFragment();
        membersPickerFragment.setAllMemberCollection(mAllUsersCollection);
        membersPickerFragment.setMemberCollection(mMemberCollection);
        TextView memberTextView = (TextView) findViewById(R.id.members);
        membersPickerFragment.setOuputTextView(memberTextView);
        membersPickerFragment.show(getFragmentManager(), "membersPicker");
    }

    private void loadAllMembers() {
        FirebaseUtil.getAllUserRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    @SuppressWarnings("unchecked")
                    GenericTypeIndicator<Map<String, User>> genericTypeIndicator = new GenericTypeIndicator<Map<String, User>>() {};
                    Map<String, User> map  = dataSnapshot.getValue(genericTypeIndicator);
                    mAllUsersCollection = new ArrayList<User>(map.values());
                    Log.d(UIUtil.TAG, "load data success");
                }
                else {
                    Log.d(UIUtil.TAG, "data not exist");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(UIUtil.TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        Spinner spinner = (Spinner) findViewById(R.id.tagSelect);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tag_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(onSelectSpinner);

        mMemberCollection = new ArrayList<>();
        loadAllMembers();

        Button create = findViewById(R.id.btn_createProject);
        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                String projectName = ((EditText) findViewById(R.id.projectName)).getText().toString();
                String tag = ((Spinner) findViewById(R.id.tagSelect)).getSelectedItem().toString();
                String description = ((EditText) findViewById(R.id.description)).getText().toString();
                User leader = new User(
                        FirebaseUtil.getCurrentUser().id,
                        FirebaseUtil.getCurrentUser().name,
                        FirebaseUtil.getCurrentUser().email);
                Project project = new Project(
                        projectName, tag, description,
                        leader, FirebaseUtil.getCurrentUser().id
                );

                for (User user : mMemberCollection) {
                    User userLight = new User(user.id, user.name, user.email);
                    project.addMember(userLight);
                }
                FirebaseUtil.updateAllProjectByID(project.id, project);

                for (User user : mMemberCollection) {
                    user.addProject(project);
                    FirebaseUtil.updateUserProjectList(user.id, user.projectCollection);
                }
//                FirebaseUtil.getCurrentUser().addProject(project);
//                FirebaseUtil.updateCurrentUserPrjectList();

                finish();
            }
        });
    }


    private AdapterView.OnItemSelectedListener onSelectSpinner = new AdapterView.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback

        }
    };
}



