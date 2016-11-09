package com.macktaby.sbrp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.macktaby.sbrp.R;
import com.macktaby.sbrp.parsing.AttributeParser;
import com.macktaby.sbrp.parsing.PersonParser;

public class AddNewAttributeActivity extends AppCompatActivity {

    private TextView editText_attributeName;
    private Button btn_addAttribute;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddNewAttributeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_attribute);

        attachViewIDs();
        attachViewActions();
    }

    private void attachViewIDs() {
        editText_attributeName = (TextView) findViewById(R.id.editText_new_attribute_name);
        btn_addAttribute = (Button) findViewById(R.id.btn_add_attribute_action);
    }

    private void attachViewActions() {
        btn_addAttribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAttribute();
            }
        });
    }

    private void addAttribute() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/addAttribute";

        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("name", editText_attributeName.getText().toString())
                .build();

        Ion.with(this)
                .load("POST", builtUri.toString())
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .asString()
                .setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(AddNewAttributeActivity.this, "ERROR Adding the Attribute\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (AttributeParser.parseID(result) > 0)
                            Toast.makeText(AddNewAttributeActivity.this, "The Attribute Added SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddNewAttributeActivity.this, "ERROR Adding the Attribute", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
