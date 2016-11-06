package com.macktaby.sbrp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.macktaby.sbrp.R;
import com.macktaby.sbrp.model.Package;
import com.macktaby.sbrp.model.Project;

public class CentralManagementActivity extends AppCompatActivity {

    private Button btn_packages;
    private Button btn_projects;
    private Button btn_people;
    private Button btn_attributes;

    public static Intent getIntent(Context context) {
        return new Intent(context, CentralManagementActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_management);

        attachViewIDs();
        attachViewActions();
    }


    public void attachViewIDs() {
        btn_packages = (Button) findViewById(R.id.btn_packages);
        btn_projects = (Button) findViewById(R.id.btn_projects);
        btn_people = (Button) findViewById(R.id.btn_people);
        btn_attributes = (Button) findViewById(R.id.btn_attributes);
    }

    private void attachViewActions() {
        btn_packages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Package parentPkg = new Package(1, "PARENT", "PARENT", "PARENT", "PARENT", 1);
                startActivity(
                        PackagesActivity.getIntent(CentralManagementActivity.this, parentPkg)
                );
            }
        });

        btn_projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Project parentProject = new Project(1, "PARENT", "PARENT", "PARENT", "PARENT", 1);
                startActivity(
                        ProjectsActivity.getIntent(CentralManagementActivity.this, parentProject)
                );
            }
        });

        btn_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        PeopleActivity.getIntent(CentralManagementActivity.this)
                );
            }
        });

    }
}
