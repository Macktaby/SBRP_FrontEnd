package com.macktaby.sbrp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.macktaby.sbrp.R;
import com.macktaby.sbrp.model.Package;

public class CentralManagementActivity extends AppCompatActivity {

    private Button btn_packages;

    public static Intent getIntent(Context context) {
        return new Intent(context, CentralManagementActivity.class);
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
                Package parentPkg = new Package(1, "DEFAULT", "DEFAULT", "DEFAULT", "DEFAULT", 1);
                startActivity(
                        PackagesActivity.getIntent(CentralManagementActivity.this, parentPkg)
                );
            }
        });
    }
}
