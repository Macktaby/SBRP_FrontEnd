package com.macktaby.sbrp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProjectManagement extends AppCompatActivity {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ProjectManagement.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_management);
    }
}
