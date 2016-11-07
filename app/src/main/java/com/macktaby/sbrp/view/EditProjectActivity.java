package com.macktaby.sbrp.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.macktaby.sbrp.R;
import com.macktaby.sbrp.model.*;
import com.macktaby.sbrp.parsing.*;

import butterknife.BindView;

public class EditProjectActivity extends AppCompatActivity {

    private Project project;

    private EditText editText_projectName;
    private EditText editText_projectTechRef;
    private EditText editText_projectMngRef;
    private EditText editText_projectBzRef;
    private Button btn_editProject;

    public static Intent getIntent(Context context, Project project) {
        return new Intent(context, EditProjectActivity.class)
                .putExtra("project", project);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        project = (Project) getIntent().getSerializableExtra("project");

        attachViewIDs();
        attachViewActions();
        attachViewValues();
    }

    private void attachViewIDs() {
        editText_projectName = (EditText) findViewById(R.id.editText_edit_project_name);
        editText_projectTechRef = (EditText) findViewById(R.id.editText_edit_tech_ref);
        editText_projectMngRef = (EditText) findViewById(R.id.editText_edit_mng_ref);
        editText_projectBzRef = (EditText) findViewById(R.id.editText_edit_bz_ref);
        btn_editProject = (Button) findViewById(R.id.btn_edit_project_action);
    }

    private void attachViewValues() {
        editText_projectName.setText(project.getName());
        editText_projectTechRef.setText(project.getTechReflection());
        editText_projectMngRef.setText(project.getMngReflection());
        editText_projectBzRef.setText(project.getBzReflection());
    }

    private void attachViewActions() {
        btn_editProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProject();
            }
        });
    }

    private void editProject() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/updateProject";

        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("name", editText_projectName.getText().toString())
                .appendQueryParameter("tech_ref", editText_projectTechRef.getText().toString())
                .appendQueryParameter("mng_ref", editText_projectMngRef.getText().toString())
                .appendQueryParameter("bz_ref", editText_projectBzRef.getText().toString())
                .appendQueryParameter("parentID", String.valueOf(project.getParentID()))
                .appendQueryParameter("id", String.valueOf(project.getProjectID()))
                .build();

        Ion.with(this)
                .load("POST", builtUri.toString())
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .asString()
                .setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(), "ERROR updating the Project\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (PackageParser.parseState(result).equals("true"))
                            Toast.makeText(getApplicationContext(), "The Project Updated SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "ERROR updating the Project", Toast.LENGTH_SHORT).show();

                    }
                });

    }

}
