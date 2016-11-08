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
import com.macktaby.sbrp.parsing.PackageParser;
import com.macktaby.sbrp.parsing.PersonParser;

public class AddNewPersonActivity extends AppCompatActivity {

    private EditText editText_personName;
    private EditText editText_personRole;
    private Button btn_addPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_person);

        attachViewIDs();
    }

    private void attachViewIDs() {
        editText_personName = (EditText) findViewById(R.id.editText_new_person_name);
        editText_personRole = (EditText) findViewById(R.id.editText_new_person_role);
        btn_addPerson = (Button) findViewById(R.id.btn_add_person_action);
        btn_addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPerson();
            }
        });

    }

    private void addPerson() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/addPerson";

        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("name", editText_personName.getText().toString())
                .appendQueryParameter("role", editText_personRole.getText().toString())
                .build();

        Ion.with(this)
                .load("POST", builtUri.toString())
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .asString()
                .setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(AddNewPersonActivity.this, "ERROR Adding the Person\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (PersonParser.parseID(result) > 0)
                            Toast.makeText(AddNewPersonActivity.this, "The Person Added SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddNewPersonActivity.this, "ERROR Adding the Person", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public static Intent getIntent(Context baseContext) {
        return new Intent(baseContext, AddNewPersonActivity.class);
    }
}
