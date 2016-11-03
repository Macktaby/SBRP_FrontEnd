package com.macktaby.sbrp.Activities;

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
import com.macktaby.sbrp.models.Package;
import com.macktaby.sbrp.parsing.*;

public class AddNewPackageActivity extends AppCompatActivity {

    private Package parent;

    private EditText editText_packageName;
    private EditText editText_packageTechRef;
    private EditText editText_packageMngRef;
    private EditText editText_packageBzRef;
    private Button btn_addPackage;

    public static Intent getIntent(Context context, Package pkg) {
        return
                new Intent(context, AddNewPackageActivity.class)
                        .putExtra("parent", pkg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_package);

        parent = (Package) getIntent().getSerializableExtra("parent");

        attachViewIDs();
    }

    private void attachViewIDs() {
        editText_packageName = (EditText) findViewById(R.id.editText_new_package_name);
        editText_packageTechRef = (EditText) findViewById(R.id.editText_new_tech_ref);
        editText_packageMngRef = (EditText) findViewById(R.id.editText_new_mng_ref);
        editText_packageBzRef = (EditText) findViewById(R.id.editText_new_bz_ref);
        btn_addPackage = (Button) findViewById(R.id.btn_add_package_action);
        btn_addPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPackage();
            }
        });
    }

    private void addPackage() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/addPackage";

        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("name", editText_packageName.getText().toString())
                .appendQueryParameter("tech_ref", editText_packageTechRef.getText().toString())
                .appendQueryParameter("mng_ref", editText_packageMngRef.getText().toString())
                .appendQueryParameter("bz_ref", editText_packageBzRef.getText().toString())
                .appendQueryParameter("parentID", String.valueOf(parent.getPackageID()))
                .build();

        Ion.with(this)
                .load("POST", builtUri.toString())
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .asString()
                .setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(AddNewPackageActivity.this, "Error Adding the package\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (PackageParser.parseID(result) > 0)
                            Toast.makeText(AddNewPackageActivity.this, "The Package Added successfully", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddNewPackageActivity.this, "Error Adding the package", Toast.LENGTH_SHORT).show();

                    }
                });
    }


}
