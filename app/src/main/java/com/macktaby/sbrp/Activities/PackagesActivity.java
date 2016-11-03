package com.macktaby.sbrp.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
//import com.macktaby.sbrp.models.*;
import com.macktaby.sbrp.R;
import com.macktaby.sbrp.models.Package;
import com.macktaby.sbrp.parsing.*;

public class PackagesActivity extends AppCompatActivity {

    private ListView list_packages;
    private Button btn_addNewPackage;

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

    private void attachViewIDs() {
        loadPackagesFromAPI();
        list_packages = (ListView) findViewById(R.id.listview_packages);
        btn_addNewPackage = (Button) findViewById(R.id.btn_add_new_package);
        btn_addNewPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        AddNewPackageActivity.getIntent(getApplicationContext(), pkg)
                );
            }
        });
    }

    private void loadPackagesFromAPI() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/getSubPackages";

        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("id", String.valueOf(pkg.getPackageID()))
                .build();

//        Toast.makeText(PackagesActivity.this, builtUri.toString(), Toast.LENGTH_LONG).show();
        Log.d("LINKK", builtUri.toString());

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
