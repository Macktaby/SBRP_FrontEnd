package com.macktaby.sbrp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.macktaby.sbrp.R;
import com.macktaby.sbrp.parsing.*;
import com.macktaby.sbrp.model.*;

import java.util.ArrayList;

public class PeopleActivity extends AppCompatActivity {

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
        attachViewActions();
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

    private void attachViewActions() {
        btn_add_new_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        AddNewPersonActivity.getIntent(getBaseContext())
                );
            }
        });
    }

    private void loadPersonsFromAPI() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/getPersons";

        Ion.with(this)
                .load("POST", baseURL)
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .asString()
                .setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(PeopleActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        persons = PersonParser.parsePeople(result);
                        ArrayList<String> personsStr = new ArrayList<String>();
                        for (Person person : persons)
                            personsStr.add(person.getName());

                        ArrayAdapter<String> mForecastAdapter =
                                new ArrayAdapter<>(
                                        PeopleActivity.this, // The current context (this activity)
                                        R.layout.list_item_package, // The name of the layout ID.
                                        R.id.list_item_package_textview, // The ID of the textview to populate.
                                        personsStr);

                        listView_people.setAdapter(mForecastAdapter);
                        listView_people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                // TODO TO BE COMPLETED
                            }
                        });

                    }
                });
    }


}
