package com.example.bakingtime.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bakingtime.database.entities.Recipe;
import com.example.bakingtime.database.entities.RecipeIngredients;
import com.example.bakingtime.database.entities.RecipeSteps;
import com.example.bakingtime.database.entities.RecipeWithIngredients;
import com.example.bakingtime.database.entities.RecipeWithSteps;
import com.example.bakingtime.utils.AppExecutors;
import com.example.bakingtime.utils.JsonUtils;
import com.example.bakingtime.utils.NetworkUtils;


import java.util.List;

public class RecipeRepository {
    private static RecipeRepository instance;

    private RecipeDao recipeDao;
    private LiveData<List<Recipe>> allRecipes;
    LiveData<List<RecipeWithSteps>> recipeWithSteps;
    LiveData<List<RecipeWithIngredients>> recipeWithIngredients;
    private int databaseSize;

    public static RecipeRepository getInstance(Application application) {
        if (instance == null){
            instance = new RecipeRepository(application);
        }
        return instance;
    }

    RecipeRepository(Application application) {
        RecipeDatabase db = RecipeDatabase.getINSTANCE(application);
        recipeDao = db.recipeDao();
        allRecipes = recipeDao.loadAllRecipes();
        recipeWithSteps = recipeDao.getRecipeSteps();
        recipeWithIngredients = recipeDao.getRecipeIngredients();
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        setRecipesIntoDatabase();
        return allRecipes;
    }

    public LiveData<List<RecipeWithSteps>> getAllRecipesWithSteps() {
        setRecipesStepsIntoDatabase();
        return recipeWithSteps;
    }

    public LiveData<List<RecipeWithIngredients>> getAllRecipesWithIngredients() {
        setRecipesIngredientsIntoDatabase();
        return recipeWithIngredients;
    }

    public void insert(Recipe recipe) {
        AppExecutors.getInstance().diskIO().execute(() -> recipeDao.insertRecipe(recipe));
    }
    public void insertSteps(RecipeSteps steps) {
        AppExecutors.getInstance().diskIO().execute(() -> recipeDao.insertRecipeSteps(steps));
    }

    public void insertIngredients(RecipeIngredients ingredients) {
        AppExecutors.getInstance().diskIO().execute(() -> recipeDao.insertRecipeIngredients(ingredients));
    }

    public int getAnyRecipe() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            private int size;
            @Override
            public void run() {
                size = recipeDao.getAnyRecipe().length;
                databaseSize = size;
            }
        });

        return databaseSize;
    }

    public void setRecipesIntoDatabase() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<Recipe> recipes;

            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl();
                //Log.i("RESPONSE FROM INTERNET", jsonResponse);
                recipes = JsonUtils.parsedRecipeJson(jsonResponse);
                if (recipeDao.getAnyRecipe().length < 1) {
                    for (int i = 0; i < recipes.size(); i++) {
                        insert(recipes.get(i));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setRecipesStepsIntoDatabase() {
        AppExecutors.getInstance().diskIO().execute(() -> {

            List<RecipeWithSteps> steps;

            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl();

                steps = JsonUtils.recipeSteps(jsonResponse);

                if (recipeDao.getAnyRecipe().length < 1) {
                    for (int i = 0; i < steps.size(); i++) {
                        List<RecipeSteps> recipeSteps = steps.get(i).getRecipeSteps();
                        for (int x = 0; x < recipeSteps.size(); x++) {
                            insertSteps(recipeSteps.get(x));
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setRecipesIngredientsIntoDatabase() {
        AppExecutors.getInstance().diskIO().execute(() -> {

            List<RecipeWithIngredients> ingredientsList;

            try {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl();
                //Log.i("RESPONSE FROM INTERNET", jsonResponse);
                ingredientsList = JsonUtils.allRecipeIngredients(jsonResponse);
                //Log.i("RECIPE Steps Size", String.valueOf(steps.get(0).recipeSteps.size()));
                if (recipeDao.getAnyRecipe().length < 1) {
                    for (int i = 0; i < ingredientsList.size(); i++) {
                        List<RecipeIngredients> recipeIngredients = ingredientsList.get(i).getRecipeIngredients();
                        for (int y = 0; y < recipeIngredients.size(); y++) {
                            System.out.println("Size of Database" + recipeIngredients.size());
                            System.out.println(recipeIngredients.get(y).getIngredient());
                                insertIngredients(recipeIngredients.get(y));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
