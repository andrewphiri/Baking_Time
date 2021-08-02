package com.example.bakingtime.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bakingtime.database.entities.Recipe;
import com.example.bakingtime.database.RecipeRepository;
import com.example.bakingtime.database.entities.RecipeWithIngredients;
import com.example.bakingtime.database.entities.RecipeWithSteps;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    RecipeRepository repository;
    LiveData<List<Recipe>> allRecipes;
    LiveData<List<RecipeWithIngredients>> recipeWithIngredients;
    LiveData<List<RecipeWithSteps>> allRecipeSteps;


    public RecipeViewModel(@NonNull Application application) {
        super(application);
        repository = RecipeRepository.getInstance(application);
        allRecipes = repository.getAllRecipes();
        recipeWithIngredients = repository.getAllRecipesWithIngredients();
        allRecipeSteps = repository.getAllRecipesWithSteps();
    }

    public LiveData<List<Recipe>> loadAllRecipes() {
        return allRecipes;
    }
    public LiveData<List<RecipeWithIngredients>> getRecipeWithIngredients() {
        return recipeWithIngredients;
    }
    public LiveData<List<RecipeWithSteps>> getAllRecipeSteps() {
        return allRecipeSteps;
    }
}
