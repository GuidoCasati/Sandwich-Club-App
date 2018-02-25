package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility functions to handle JSON data.
 */
public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        final String[] saAttributes = {"mainName", "alsoKnownAs", "placeOfOrigin", "description", "image", "ingredients"};

        /* String array to hold each day's weather String */
        String sSeparatorStart = "\":";
        String sSeparatorEnd = ",\"";

        List<String> lSplittedJson = new ArrayList<String>() {
        };
        for (Integer i = 0; i < saAttributes.length; i++) {
            String placeholder = saAttributes[i] + sSeparatorStart;
            Integer iStartIndex = json.lastIndexOf(placeholder);
            if (i != saAttributes.length - 1) {
                Integer iEndIndex = json.indexOf(sSeparatorEnd + saAttributes[i + 1]);

                lSplittedJson.add(json.substring(iStartIndex + placeholder.length(), iEndIndex).replaceAll("[\\[{}\\]\"]", ""));
            } else
                lSplittedJson.add(json.substring(iStartIndex + placeholder.length()).replaceAll("[\\[{}\\]\"]", ""));
        }

        String mainName = lSplittedJson.get(0), alsoKnownAs = lSplittedJson.get(1), placeOfOrigin = lSplittedJson.get(2),
                description = lSplittedJson.get(3), image = lSplittedJson.get(4), ingredients = lSplittedJson.get(5);

        List<String> lsAlsoKnownAs = new ArrayList<String>(), lsIngredients=new ArrayList<String>();

        if (!alsoKnownAs.isEmpty())
            lsAlsoKnownAs = Arrays.asList(alsoKnownAs.trim().split(","));

        if (!ingredients.isEmpty())
        {
            String [] saIngredients = ingredients.trim().split("(?=\\p{Upper})");
            if (saIngredients.length == 2)
                saIngredients = ingredients.trim().split(",");

            lsIngredients = Arrays.asList(saIngredients);
        }


        return new Sandwich(mainName, lsAlsoKnownAs, placeOfOrigin, description, image, lsIngredients);
    }

}
