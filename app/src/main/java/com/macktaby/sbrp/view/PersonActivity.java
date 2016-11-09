package com.macktaby.sbrp.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.macktaby.sbrp.R;
import com.macktaby.sbrp.model.Person;
import com.macktaby.sbrp.parsing.PackageParser;

public class PersonActivity extends AppCompatActivity {

    private TextView textView_person_Name;
    private TextView textView_person_Role;
    private Button btn_editPerson;
    private Button btn_deletePerson;
    private ListView listView_reports;

    private Person person;

    public static Intent getIntent(Context context, Person person) {
        return new Intent(context, PersonActivity.class)
                .putExtra("person", person);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        person = (Person) getIntent().getSerializableExtra("person");

        attachViewIDs();
        attachViewActions();
    }

    private void attachViewIDs() {
        listView_reports = (ListView) findViewById(R.id.listView_person_report);

        btn_editPerson = (Button) findViewById(R.id.btn_edit_person);
        btn_deletePerson = (Button) findViewById(R.id.btn_delete_person);

        textView_person_Name = (TextView) findViewById(R.id.textView_person_name);
        textView_person_Role = (TextView) findViewById(R.id.textView_person_role);
    }

    private void attachViewActions() {
        textView_person_Name.setText(person.getName());
        textView_person_Role.setText(person.getRole());

        btn_editPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        EditPersonActivity.getIntent(PersonActivity.this, person)
                );
            }
        });

        btn_deletePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(PersonActivity.this)
                        .setTitle("Delete Person !!!")
                        .setMessage("Are you sure you want to delete this person?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deletePerson();
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
    }

    private void deletePerson() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/deletePerson";

        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("id", String.valueOf(person.getPersonID()))
                .build();

        Ion.with(this)
                .load("POST", builtUri.toString())
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .asString()
                .setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(PersonActivity.this, "Error Deleting " + person.getName() + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (PackageParser.parseState(result).equals("true"))
                            Toast.makeText(PersonActivity.this, person.getName() + " deleted successfully", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(PersonActivity.this, "Error Deleting " + person.getName(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

}
