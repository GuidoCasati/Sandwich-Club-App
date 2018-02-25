package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions to handle JSON data using JSONObject.
 */
public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        if (json != null) {
            try {
                JSONObject jsonObj = new JSONObject(json);
                JSONObject name = jsonObj.getJSONObject("name");

                String mainName = name.getString("mainName");
                JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
                String placeOfOrigin = jsonObj.getString("placeOfOrigin");
                String description = jsonObj.getString("description");
                String image = jsonObj.getString("image");
                JSONArray ingredients = jsonObj.getJSONArray("ingredients");


                List<String> lsAlsoKnownAs = new ArrayList<>(), lsIngredients = new ArrayList<>();

                for (int i = 0; i < alsoKnownAs.length(); i++)
                    lsAlsoKnownAs.add(String.valueOf(alsoKnownAs.get(i)));

                for (int i = 0; i < ingredients.length(); i++)
                    lsIngredients.add(String.valueOf(ingredients.get(i)));
                return new Sandwich(mainName, lsAlsoKnownAs, placeOfOrigin, description, image, lsIngredients);

            } catch (JSONException e) {
                Log.e("JSONException", "Json parsing error: " + e.getMessage());
            }
        }
        return null;
    }
}
