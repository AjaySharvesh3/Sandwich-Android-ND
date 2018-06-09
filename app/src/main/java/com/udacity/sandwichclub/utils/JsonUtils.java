package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String AKA = "alsoKnownAs";
    public static final String ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTIONS = "description";
    public static final String IMAGE = "image";
    public static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;
        try {
            JSONObject sandwichJsonObject = new JSONObject(json);

            JSONObject name = sandwichJsonObject.getJSONObject(NAME);
            String mainName = name.getString(MAIN_NAME);
            JSONArray alsoKnownAsJsonArray = name.getJSONArray(AKA);
            List<String> alsoKnownAs = appendJSONObjectInList(alsoKnownAsJsonArray);

            String placeOfOrigin = sandwichJsonObject.getString(ORIGIN);
            String description = sandwichJsonObject.getString(DESCRIPTIONS);
            String image = sandwichJsonObject.getString(IMAGE);

            JSONArray ingredientsJsonArray = sandwichJsonObject.getJSONArray(INGREDIENTS);
            List<String> ingredients = appendJSONObjectInList(ingredientsJsonArray);

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    private static List<String> appendJSONObjectInList (JSONArray jsonArray) throws JSONException {
        int lengthArray = jsonArray.length();
        List<String> listOfJsonObject = new ArrayList<>();

        for(int i = 0; i < lengthArray; i++) {
            listOfJsonObject.add(jsonArray.getString(i));
        }
        return listOfJsonObject;
    }
}
