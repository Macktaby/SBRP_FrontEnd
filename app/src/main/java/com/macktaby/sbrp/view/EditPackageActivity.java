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
import com.macktaby.sbrp.parsing.PackageParser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditPackageActivity extends Activity {

    private Package pkg;

    @BindView(R.id.editText_edit_package_name)
    private EditText editText_packageName;

    @BindView(R.id.editText_edit_tech_ref)
    private EditText editText_packageTechRef;

    @BindView(R.id.editText_edit_mng_ref)
    private EditText editText_packageMngRef;

    @BindView(R.id.editText_edit_bz_ref)
    private EditText editText_packageBzRef;

    @BindView(R.id.btn_edit_package_action)
    private Button btn_editPackage;

    public static Intent getIntent(Context context, Package pkg) {
        return new Intent(context, EditPackageActivity.class)
                .putExtra("package", pkg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_package);

        pkg = (Package) getIntent().getSerializableExtra("package");

        attachViewIDs();
        attachViewActions();
        attachViewValues();
    }

    private void attachViewIDs() {
        editText_packageName = (EditText) findViewById(R.id.editText_edit_package_name);
        editText_packageTechRef = (EditText) findViewById(R.id.editText_edit_tech_ref);
        editText_packageMngRef = (EditText) findViewById(R.id.editText_edit_mng_ref);
        editText_packageBzRef = (EditText) findViewById(R.id.editText_edit_bz_ref);
        btn_editPackage = (Button) findViewById(R.id.btn_edit_package_action);
    }

    private void attachViewValues() {
        editText_packageName.setText(pkg.getName());
        editText_packageTechRef.setText(pkg.getTechReflection());
        editText_packageMngRef.setText(pkg.getMngReflection());
        editText_packageBzRef.setText(pkg.getBzReflection());
    }

    private void attachViewActions() {
        btn_editPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPackage();
            }
        });
    }

    private void editPackage() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/updatePackage";

        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("name", editText_packageName.getText().toString())
                .appendQueryParameter("tech_ref", editText_packageTechRef.getText().toString())
                .appendQueryParameter("mng_ref", editText_packageMngRef.getText().toString())
                .appendQueryParameter("bz_ref", editText_packageBzRef.getText().toString())
                .appendQueryParameter("parentID", String.valueOf(pkg.getParentID()))
                .appendQueryParameter("id", String.valueOf(pkg.getPackageID()))
                .build();

        Ion.with(this)
                .load("POST", builtUri.toString())
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .asString()
                .setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(), "ERROR updating the package\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (PackageParser.parseState(result).equals("true"))
                            Toast.makeText(getApplicationContext(), "The Package Updated SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "ERROR updating the package", Toast.LENGTH_SHORT).show();

                    }
                });

    }

}
