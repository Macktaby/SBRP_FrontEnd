package com.macktaby.sbrp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

public class Packages extends AppCompatActivity {

    private ListView list_packages;
    private Button btn_addNewPackage;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, Packages.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);

        attachViewIDs();
    }

    private void attachViewIDs() {
        list_packages = (ListView) findViewById(R.id.listview_packages);
        btn_addNewPackage = (Button) findViewById(R.id.btn_add)
    }
}
