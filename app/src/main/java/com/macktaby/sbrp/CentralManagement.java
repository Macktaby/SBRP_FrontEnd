package com.macktaby.sbrp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CentralManagement extends AppCompatActivity {

    private Button btn_packages;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, CentralManagement.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_management);

        attachViewIDs();
    }

    public void attachViewIDs() {
        btn_packages = (Button) findViewById(R.id.btn_packages);
        btn_packages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Packages.getIntent(CentralManagement.this));
            }
        });
    }
}
