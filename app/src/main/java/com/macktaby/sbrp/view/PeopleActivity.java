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
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.macktaby.sbrp.R;
import com.macktaby.sbrp.model.*;
import com.macktaby.sbrp.model.Package;
import com.macktaby.sbrp.parsing.PackageParser;

import java.util.ArrayList;

public class PeopleActivity extends Activity {

    private ArrayList<Person> persons;

    private ListView listView_people;
    private Button btn_add_new_person;

    public static Intent getIntent(Context context) {
        return new Intent(context, PeopleActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        attachViewIDs();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadPersonsFromAPI();
    }

    private void attachViewIDs() {
        listView_people = (ListView) findViewById(R.id.listView_persons);
        btn_add_new_person = (Button) findViewById(R.id.btn_add_new_person);
    }

    // TODO Back end Needed
    private void loadPersonsFromAPI() {
//        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/getPeople";
//
//        Uri builtUri = Uri.parse(baseURL).buildUpon()
//                .appendQueryParameter("id", String.valueOf(pkg.getPackageID()))
//                .build();
//
//        Ion.with(this)
//                .load("POST", builtUri.toString())
//                .setHeader("Content-Type", "application/x-www-form-urlencoded")
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//
//                    @Override
//                    public void onCompleted(Exception e, String result) {
//                        if (e != null) {
//                            Toast.makeText(PackagesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//
//                        subPackages = PackageParser.parsePackages(result);
//                        ArrayList<String> packagesStr = new ArrayList<String>();
//                        for (Package pkg : subPackages)
//                            packagesStr.add(pkg.getName());
//
//                        ArrayAdapter<String> mForecastAdapter =
//                                new ArrayAdapter<>(
//                                        PackagesActivity.this, // The current context (this activity)
//                                        R.layout.list_item_package, // The name of the layout ID.
//                                        R.id.list_item_package_textview, // The ID of the textview to populate.
//                                        packagesStr);
//
//                        list_packages.setAdapter(mForecastAdapter);
//                        list_packages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                                startActivity(
//                                        PackagesActivity.getIntent(PackagesActivity.this, subPackages.get(i))
//                                );
//
//                            }
//                        });
//
//                    }
//                });
    }


}
