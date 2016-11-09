package com.macktaby.sbrp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.macktaby.sbrp.R;
import com.macktaby.sbrp.model.Attribute;
import com.macktaby.sbrp.model.Person;
import com.macktaby.sbrp.parsing.AttributeParser;
import com.macktaby.sbrp.parsing.PersonParser;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class AttributesActivity extends AppCompatActivity {

    private ArrayList<Attribute> attributes;

    private ListView listView_attributes;
    private Button btn_add_new_attribute;

    public static Intent getIntent(Context context) {
        return new Intent(context, AttributesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attributes);

        attachViewIDs();
        attachViewActions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadAttributesFromAPI();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.listView_attributes) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("Options");
            String[] menuItems = getResources().getStringArray(R.array.attribute_context_menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.attribute_context_menu);
        Toast.makeText(this, menuItems[menuItemIndex], Toast.LENGTH_SHORT).show();
        return true;
    }

    private void attachViewIDs() {
        listView_attributes = (ListView) findViewById(R.id.listView_attributes);
        btn_add_new_attribute = (Button) findViewById(R.id.btn_add_new_attribute);
    }

    private void attachViewActions() {
        btn_add_new_attribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(
//                        AddNewPersonActivity.getIntent(PeopleActivity.this)
//                );
            }
        });

        registerForContextMenu(listView_attributes);
        listView_attributes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                startActivity(
//                                        PersonActivity.getIntent(AttributesActivity.this, attributes.get(i))
//                                );
            }
        });
    }

    private void loadAttributesFromAPI() {
        String baseURL = "https://macktaby-merged.rhcloud.com/SBRP/rest/cm/getAttributes";

        Ion.with(this)
                .load("POST", baseURL)
                .setHeader("Content-Type", "application/x-www-form-urlencoded")
                .asString()
                .setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null) {
                            Toast.makeText(AttributesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        attributes = AttributeParser.parseAttributes(result);
                        ArrayList<String> attributesStr = new ArrayList<>();
                        for (Attribute attribute : attributes)
                            attributesStr.add(attribute.getName());

                        ArrayAdapter<String> mForecastAdapter =
                                new ArrayAdapter<>(
                                        AttributesActivity.this, // The current context (this activity)
                                        R.layout.list_item_package, // The name of the layout ID.
                                        R.id.list_item_package_textview, // The ID of the textview to populate.
                                        attributesStr);

                        listView_attributes.setAdapter(mForecastAdapter);

                    }
                });
    }

}
