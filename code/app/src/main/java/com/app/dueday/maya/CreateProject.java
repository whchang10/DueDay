package com.app.dueday.maya;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.dueday.maya.type.Project;
import com.app.dueday.maya.type.User;

import java.util.ArrayList;
import java.util.List;


public class CreateProject extends AppCompatActivity {

    private List<User> memberCollection;
    public static class MembersPickerFragment extends DialogFragment {

        private List<Integer> track;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            track = new ArrayList<>();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String stringArray[] = {"A", "B", "C", "D"};
            // Set the dialog title
            builder.setTitle("Select Members")
                    // Specify the list array, the items to be selected by default (null for none),
                    // and the listener through which to receive callbacks when items are selected
                    .setMultiChoiceItems(stringArray, null,
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
                            // User clicked OK, so save the mSelectedItems results somewhere
                            // or return them to the component that opened the dialog
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            return builder.create();
        }
    }

    public void selectMembers(View v) {
        CreateProject.MembersPickerFragment membersPickerFragment = new CreateProject.MembersPickerFragment();
        //datePickerFragment.setOutputView(v);
        membersPickerFragment.show(getFragmentManager(), "membersPicker");
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
                project.addMember(leader);

                FirebaseUtil.getCurrentUser().addProject(project);
                FirebaseUtil.updateCurrentUserPrjectList();

                FirebaseUtil.updateAllProjectByID(project.id, project);

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



