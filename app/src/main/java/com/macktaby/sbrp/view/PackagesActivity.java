package com.macktaby.sbrp.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.macktaby.sbrp.R;
import com.macktaby.sbrp.model.Package;
import com.macktaby.sbrp.parsing.*;

public class PackagesActivity extends AppCompatActivity {

    private ListView list_packages;
    private Button btn_addNewPackage;
    private Button btn_editPackage;
    private Button btn_deletePackage;

    private Package pkg;
    private ArrayList<Package> subPackages;

    public static Intent getIntent(Context context, Package pkg) {
        return new Intent(context, PackagesActivity.class)
                .putExtra("package", pkg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);

        pkg = (Package) getIntent().getSerializableExtra("package");

        attachViewIDs();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadPackagesFromAPI();
    }

    private void attachViewIDs() {
        list_packages = (ListView) findViewById(R.id.listview_packages);

        btn_editPackage = (Button) findViewById(R.id.btn_edit_package);
        btn_editPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), pkg.getName(), Toast.LENGTH_LONG).show();
                startActivity(
                        EditPackageActivity.getIntent(getApplicationContext(), pkg)
                );
            }
        });

        btn_deletePackage = (Button) findViewById(R.id.btn_delete_package);
        btn_deletePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(PackagesActivity.this)
                        .setTitle("Delete Package !!!")
                        .setMessage("Are you sure you want to delete this package?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deletePackage();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

        btn_addNewPackage = (Button) findViewById(R.id.btn_add_new_package);
        btn_addNewPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        AddNewPackageActivity.getIntent(getApplicationContext(), pkg)
                );
            }
        });

        if (pkg.getPackageID() == 1) {
            btn_deletePackage.setEnabled(false);
            btn_editPackage.setEnabled(false);
        }
    }

    private void deletePackage() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/deletePackage";

        Uri builtUri = Uri.parse(baseURL).buildUpon()
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
                            Toast.makeText(PackagesActivity.this, "Error Deleting the package\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (PackageParser.parseState(result).equals("true"))
                            Toast.makeText(PackagesActivity.this, "The Package deleted successfully", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(PackagesActivity.this, "Error deleting the package", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void loadPackagesFromAPI() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/getSubPackages";

        Uri builtUri = Uri.parse(baseURL).buildUpon()
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
                            Toast.makeText(PackagesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }


                        subPackages = PackageParser.parsePackages(result);
                        ArrayList<String> packagesStr = new ArrayList<String>();
                        for (Package pkg : subPackages)
                            packagesStr.add(pkg.getName());

                        ArrayAdapter<String> mForecastAdapter =
                                new ArrayAdapter<>(
                                        PackagesActivity.this, // The current context (this activity)
                                        R.layout.list_item_package, // The name of the layout ID.
                                        R.id.list_item_package_textview, // The ID of the textview to populate.
                                        packagesStr);

                        list_packages.setAdapter(mForecastAdapter);
                        list_packages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                startActivity(
                                        PackagesActivity.getIntent(PackagesActivity.this, subPackages.get(i))
                                );

                            }
                        });

                    }
                });
    }
}
