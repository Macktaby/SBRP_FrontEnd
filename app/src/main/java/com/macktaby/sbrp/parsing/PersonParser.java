package com.macktaby.sbrp.parsing;

import android.util.Log;

import com.macktaby.sbrp.model.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shady on 11/8/2016.
 */
public class PersonParser {

    private static String LOG_CAT = PersonParser.class.getName();

    public static ArrayList<Person> parsePeople(String result) {
        JSONObject jsonObject;
        ArrayList<Person> persons;

        try {
            jsonObject = new JSONObject(result);
            JSONArray results = jsonObject.getJSONArray("people");
            persons = new ArrayList<>(results.length());

            for (int i = 0; i < results.length(); ++i) {
                JSONObject obj = results.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");
                String role = obj.getString("role");

                Person person = new Person(id, name, role);
                persons.add(person);
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }

        return persons;
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
