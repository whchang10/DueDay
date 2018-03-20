package com.app.dueday.maya;

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


public class CreateProject extends AppCompatActivity {
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



