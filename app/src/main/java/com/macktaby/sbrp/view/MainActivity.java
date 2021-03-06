package com.macktaby.sbrp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.macktaby.sbrp.R;

public class MainActivity extends AppCompatActivity {

    private Button central_mng;
    private Button project_mng;
    private Button developer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attachViewIDs();
    }

    public void attachViewIDs(){
        central_mng = (Button) findViewById(R.id.btn_central_management);
        central_mng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CentralManagementActivity.getIntent(MainActivity.this));
            }
        });

        project_mng = (Button) findViewById(R.id.btn_project_management);
        project_mng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ProjectManagementActivity.getIntent(MainActivity.this));
            }
        });

        developer = (Button) findViewById(R.id.btn_dev);
    }


}
