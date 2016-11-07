package com.macktaby.sbrp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.macktaby.sbrp.R;
import com.macktaby.sbrp.model.Package;
import com.macktaby.sbrp.model.Project;
import com.macktaby.sbrp.parsing.PackageParser;

public class AddNewProjectActivity extends Activity {

    private Project parent;

    private EditText editText_projectName;
    private EditText editText_projectTechRef;
    private EditText editText_projectMngRef;
    private EditText editText_projectBzRef;
    private Button btn_addProject;

    public static Intent getIntent(Context context, Project project) {
        return
                new Intent(context, AddNewProjectActivity.class)
                        .putExtra("parent", project);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_project);

        parent = (Project) getIntent().getSerializableExtra("parent");

        attachViewIDs();
    }

    private void attachViewIDs() {
        editText_projectName = (EditText) findViewById(R.id.editText_new_project_name);
        editText_projectTechRef = (EditText) findViewById(R.id.editText_new_tech_ref);
        editText_projectMngRef = (EditText) findViewById(R.id.editText_new_mng_ref);
        editText_projectBzRef = (EditText) findViewById(R.id.editText_new_bz_ref);
        btn_addProject = (Button) findViewById(R.id.btn_add_project_action);
        btn_addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProject();
            }
        });

    }

    private void addProject() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/addProject";

        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("name", editText_projectName.getText().toString())
                .appendQueryParameter("tech_ref", editText_projectTechRef.getText().toString())
                .appendQueryParameter("mng_ref", editText_projectMngRef.getText().toString())
                .appendQueryParameter("bz_ref", editText_projectBzRef.getText().toString())
                .appendQueryParameter("parentID", String.valueOf(parent.getProjectID()))
                .build();

        Ion.with(this)
                .load("POST", builtUri.toString())
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .asString()
                .setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(AddNewProjectActivity.this, "ERROR Adding the Project\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (PackageParser.parseID(result) > 0)
                            Toast.makeText(AddNewProjectActivity.this, "The Project Added SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddNewProjectActivity.this, "ERROR Adding the Project", Toast.LENGTH_SHORT).show();

                    }
                });
    }

}
