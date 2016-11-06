package com.macktaby.sbrp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.macktaby.sbrp.R;
import com.macktaby.sbrp.model.Package;
import com.macktaby.sbrp.model.Project;
import com.macktaby.sbrp.parsing.PackageParser;
import com.macktaby.sbrp.parsing.ProjectParser;

import java.util.ArrayList;

public class ProjectsActivity extends Activity {

    private ListView listView_projects;
    private TextView textView_projectName;
    private Button btn_addNewProject;
    private Button btn_editProject;
    private Button btn_deleteProject;

    private Project project;
    private ArrayList<Project> subProjects;

    public static Intent getIntent(Context context, Project project) {
        return new Intent(context, ProjectsActivity.class)
                .putExtra("project", project);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        this.project = (Project) getIntent().getSerializableExtra("project");

        attachViewIDs();
        attachViewActions();
    }

    private void attachViewIDs() {
        listView_projects = (ListView) findViewById(R.id.listview_projects);

        btn_editProject = (Button) findViewById(R.id.btn_edit_project);
        btn_deleteProject = (Button) findViewById(R.id.btn_delete_project);
        btn_addNewProject = (Button) findViewById(R.id.btn_add_new_project);

        textView_projectName = (TextView) findViewById(R.id.textView_project_name);
        textView_projectName.setText(project.getName());

        if (project.getProjectID() == 1) {
            btn_deleteProject.setEnabled(false);
            btn_editProject.setEnabled(false);
        }
    }

    // TODO
    private void attachViewActions() {

    }

    private void loadProjectsFromAPI() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/getSubProjects";

        Uri builtUri = Uri.parse(baseURL).buildUpon()
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
                            Toast.makeText(ProjectsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        subProjects = ProjectParser.parseProjects(result);
                        ArrayList<String> projectsStr = new ArrayList<String>();
                        for (Project project : subProjects)
                            projectsStr.add(project.getName());

                        ArrayAdapter<String> mForecastAdapter =
                                new ArrayAdapter<>(
                                        ProjectsActivity.this, // The current context (this activity)
                                        R.layout.list_item_package, // The name of the layout ID.
                                        R.id.list_item_package_textview, // The ID of the textview to populate.
                                        projectsStr);

                        listView_projects.setAdapter(mForecastAdapter);
                        listView_projects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                startActivity(
                                        ProjectsActivity.getIntent(ProjectsActivity.this, subProjects.get(i))
                                );

                            }
                        });

                    }
                });
    }


}
