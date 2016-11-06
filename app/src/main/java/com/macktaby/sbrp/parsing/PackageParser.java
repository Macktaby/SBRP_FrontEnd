package com.macktaby.sbrp.parsing;

import android.util.Log;

import com.macktaby.sbrp.model.Package;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shady on 11/2/2016.
 */
public class PackageParser {

    private static String LOG_CAT = PackageParser.class.getName();

    public static ArrayList<Package> parsePackages(String result) {
        JSONObject jsonObject;
        ArrayList<Package> packages;

        try {
            jsonObject = new JSONObject(result);
            JSONArray results = jsonObject.getJSONArray("packages");
            packages = new ArrayList<>(results.length());

            for (int i = 0; i < results.length(); ++i) {
                JSONObject obj = results.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");
                String tech_ref = obj.getString("tech_ref");
                String mng_ref = obj.getString("mng_ref");
                String bz_ref = obj.getString("bz_ref");
                int parentID = obj.getInt("parent_id");

                Package pkg = new Package(id, name, tech_ref, mng_ref, bz_ref, parentID);
                packages.add(pkg);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            Log.v(LOG_CAT, ex.toString());
            return null;
        }

        return packages;
    }

    public static int parseID(String result) {
        try {

            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String parseState(String result) {
        try {

            JSONObject jsonObject = new JSONObject(result);
            return jsonObject.getString("state");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "false";
    }

}
