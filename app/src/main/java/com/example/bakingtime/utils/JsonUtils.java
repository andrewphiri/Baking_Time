package com.example.bakingtime.utils;

import com.example.bakingtime.database.entities.Recipe;
import com.example.bakingtime.database.entities.RecipeIngredients;
import com.example.bakingtime.database.entities.RecipeSteps;
import com.example.bakingtime.database.entities.RecipeWithIngredients;
import com.example.bakingtime.database.entities.RecipeWithSteps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    public static List<Recipe> parsedRecipeJson(String recipeJson) throws JSONException {
        final String recipeName = "name";

        String recipe;

        List<Recipe> recipeList = new ArrayList<>();

        Recipe bakingRecipes;

        JSONArray jsonArray = new JSONArray(recipeJson);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            recipe = object.getString(recipeName);

            bakingRecipes = new Recipe(recipe);
            recipeList.add(bakingRecipes);
            }

            return recipeList;
        }


    public static List<RecipeIngredients> getAllIngredients(String name, String recipeJson, int i) throws JSONException  {

        final String ingredients = "ingredients";
        final String quantity = "quantity";
        final String measure = "measure";
        final String specificIngredients = "ingredient";

        String recipeIngredient = null;
        String quantityOfSpecificIngredient = null;
        String recipeMeasurement = null;

        RecipeIngredients recipeIngredientsObject;
        List<RecipeIngredients> list = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(recipeJson);
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        JSONArray arrayIngredients = jsonObject.getJSONArray(ingredients);

        for (int y = 0; y < arrayIngredients.length(); y++) {
            JSONObject ingredientsJsonObject = arrayIngredients.getJSONObject(y);

            if (ingredientsJsonObject.has(specificIngredients)) {
                recipeIngredient = ingredientsJsonObject.getString(specificIngredients);
            }

            if (ingredientsJsonObject.has(measure)) {
                recipeMeasurement = ingredientsJsonObject.getString(measure);
            }
            if (ingredientsJsonObject.has(quantity)) {
                quantityOfSpecificIngredient = ingredientsJsonObject.getString(quantity);
            }
            recipeIngredientsObject = new RecipeIngredients(name, quantityOfSpecificIngredient,
                    recipeMeasurement, recipeIngredient);
            list.add(recipeIngredientsObject);
        }
        return list;
    }

    public static List<RecipeSteps> getAllSteps(String name,String recipeJson, int i) throws JSONException  {

        final String stepsArray = "steps";
        final String description = "description";
        final String videoURl = "videoURL";
        final String imageURL = "thumbnailURL";

        String descriptionOfStep = null;
        String videoInstructionURl = null;
        String imageStepURL = null;

        RecipeSteps recipeSteps;

        List<RecipeSteps> steps = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(recipeJson);
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        JSONArray array = jsonObject.getJSONArray(stepsArray);

        for (int x = 0; x < array.length(); x++) {
            JSONObject stepsJsonObject = array.getJSONObject(x);

            if (stepsJsonObject.has(description)) {
                descriptionOfStep = stepsJsonObject.getString(description);
            }

            if (stepsJsonObject.has(videoURl)) {
                videoInstructionURl = stepsJsonObject.getString(videoURl);
            }

            if (stepsJsonObject.has(imageURL)) {
                imageStepURL = stepsJsonObject.getString(imageURL);
            }
            recipeSteps = new RecipeSteps(name, descriptionOfStep, videoInstructionURl, imageStepURL);
            steps.add(recipeSteps);
        }
        return steps;
    }

    public static List<RecipeWithSteps> recipeSteps(String json) throws JSONException {

        final String recipeName = "name";
        int sizeOfArray = 0;

        List<RecipeSteps> steps;
        List<RecipeWithSteps> recipeWithStepsList = new ArrayList<>();
        RecipeWithSteps recipeSteps;

        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String recipe = jsonObject.getString(recipeName);

            if (i == sizeOfArray) {
                steps = getAllSteps(recipe,json, i);
            } else{
                break;
            }
            recipeSteps = new RecipeWithSteps(steps);
            recipeWithStepsList.add(recipeSteps);
            sizeOfArray++;
        }
        return recipeWithStepsList;
    }

    public static List<RecipeWithIngredients> allRecipeIngredients(String json) throws JSONException {

        final String recipeName = "name";
        int sizeOfArray = 0;

        List<RecipeIngredients> ingredients;
        List<RecipeWithIngredients> recipeWithIngredientsList = new ArrayList<>();
        RecipeWithIngredients recipeIngredients;

        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String recipe = jsonObject.getString(recipeName);

            if (i == sizeOfArray) {
                ingredients = getAllIngredients(recipe,json, i);
            } else{
                break;
            }
            recipeIngredients = new RecipeWithIngredients(ingredients);
            recipeWithIngredientsList.add(recipeIngredients);
            sizeOfArray++;
        }
        return recipeWithIngredientsList;
    }
}
