package com.macktaby.sbrp.view;

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
import com.macktaby.sbrp.model.Attribute;
import com.macktaby.sbrp.model.Person;
import com.macktaby.sbrp.parsing.AttributeParser;
import com.macktaby.sbrp.parsing.PersonParser;

import org.w3c.dom.Attr;

public class EditAttributeActivity extends AppCompatActivity {

    private EditText editText_attributeName;
    private Button btn_editAttribute;

    private Attribute attribute;

    public static Intent getIntent(Context context, Attribute attribute) {
        return new Intent(context, EditAttributeActivity.class)
                .putExtra("attribute", attribute);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_attribute);

        attribute = (Attribute) getIntent().getSerializableExtra("attribute");

        attachView();
    }

    private void attachView() {
        editText_attributeName = (EditText) findViewById(R.id.editText_edit_attribute_name);
        editText_attributeName.setText(attribute.getName());

        btn_editAttribute = (Button) findViewById(R.id.btn_edit_attribute_action);
        btn_editAttribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAttribute();
            }
        });
    }

    private void editAttribute() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/updateAttribute";

        Uri builtUri = Uri.parse(baseURL).buildUpon()
                .appendQueryParameter("name", editText_attributeName.getText().toString())
                .appendQueryParameter("id", String.valueOf(attribute.getAttributeID()))
                .build();

        Ion.with(this)
                .load("POST", builtUri.toString())
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .asString()
                .setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(), "ERROR updating this Attribute\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (AttributeParser.parseState(result).equals("true"))
                            Toast.makeText(getApplicationContext(), "Attribute Updated SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "ERROR updating this Attribute", Toast.LENGTH_SHORT).show();

                    }
                });

    }


}
