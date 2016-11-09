package com.macktaby.sbrp.parsing;

import android.util.Log;

import com.macktaby.sbrp.model.Attribute;
import com.macktaby.sbrp.model.Project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shady on 11/2/2016.
 */
public class AttributeParser {

    private static String LOG_CAT = AttributeParser.class.getName();

    public static ArrayList<Attribute> parseAttributes(String result) {
        JSONObject jsonObject;
        ArrayList<Attribute> attributes;

        try {
            jsonObject = new JSONObject(result);
            JSONArray results = jsonObject.getJSONArray("attributes");
            attributes = new ArrayList<>(results.length());

            for (int i = 0; i < results.length(); ++i) {
                JSONObject obj = results.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");

                Attribute attribute = new Attribute(id, name);
                attributes.add(attribute);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            Log.v(LOG_CAT, ex.toString());
            return null;
        }

        return attributes;
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
