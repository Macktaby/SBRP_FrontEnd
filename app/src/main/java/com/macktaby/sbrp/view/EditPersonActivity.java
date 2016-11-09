package com.macktaby.sbrp.view;

import android.app.Activity;
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
import com.macktaby.sbrp.model.Person;
import com.macktaby.sbrp.model.Project;
import com.macktaby.sbrp.parsing.PackageParser;
import com.macktaby.sbrp.parsing.PersonParser;

public class EditPersonActivity extends AppCompatActivity {

    private Person person;

    private EditText editText_personName;
    private EditText editText_personRole;

    private Button btn_editPerson;

    public static Intent getIntent(Context context, Person person) {
        return new Intent(context, EditPersonActivity.class)
                .putExtra("person", person);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);

        person = (Person) getIntent().getSerializableExtra("person");

        attachViewIDs();
        attachViewActions();
        attachViewValues();
    }

    private void attachViewIDs() {
        editText_personName = (EditText) findViewById(R.id.editText_person_name);
        editText_personRole = (EditText) findViewById(R.id.editText_person_role);
        btn_editPerson = (Button) findViewById(R.id.btn_edit_person_action);
    }

    private void attachViewValues() {
        editText_personName.setText(person.getName());
        editText_personRole.setText(person.getRole());
    }

    private void attachViewActions() {
        btn_editPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProject();
            }
        });
    }

    private void editProject() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/updatePerson";

        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("name", editText_personName.getText().toString())
                .appendQueryParameter("role", editText_personRole.getText().toString())
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
                            Toast.makeText(getApplicationContext(), "ERROR updating this Person\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (PersonParser.parseState(result).equals("true"))
                            Toast.makeText(getApplicationContext(), "Person Updated SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "ERROR updating this Project", Toast.LENGTH_SHORT).show();

                    }
                });

    }

}
