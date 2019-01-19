package com.example.aashishgodambe.testproject;

import android.content.Context;
import android.util.Log;

import com.example.aashishgodambe.testproject.model.Location;
import com.example.aashishgodambe.testproject.views.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aashishgodambe on 1/14/19.
 */

public class Utils {

    public static String readJsonDataFromFile(Context context) throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = context.getResources().openRawResource(R.raw.newspaper_locations);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }

    public static List<Location> addLocationItemsFromJson(Context context) {

        List<Location> locationsList= new ArrayList<>();
        try {
            String jsonDataString = Utils.readJsonDataFromFile(context);
            JSONObject locationsItemsJsonObject = new JSONObject(jsonDataString);
            JSONArray locationsArray = locationsItemsJsonObject.getJSONArray("locations");

            for (int i = 0; i < locationsArray.length(); ++i) {

                JSONObject locationObject = locationsArray.getJSONObject(i);

                String street = locationObject.getString("street");
                String city = locationObject.getString("city");
                String style = locationObject.getString("style");
                String name = locationObject.getString("name");
                String lat = locationObject.getString("lat");
                String lng = locationObject.getString("lng");
                String state = locationObject.getString("state");
                String location = locationObject.getString("directions_address");
                String details = locationObject.getString("details");

                Location item = new Location(street, city, style,
                        name, lat,lng,state,location,details);
                locationsList.add(item);
            }
        } catch (IOException | JSONException exception) {
            Log.e(MainActivity.class.getName(), "Unable to parse JSON file.", exception);
        }

        return locationsList;
    }
}
