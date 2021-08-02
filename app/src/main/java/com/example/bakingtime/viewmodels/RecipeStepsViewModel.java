package com.example.bakingtime.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bakingtime.database.RecipeDatabase;

import com.example.bakingtime.database.entities.RecipeSteps;


public class RecipeStepsViewModel extends ViewModel {
    LiveData<RecipeSteps> recipeStep;

    public RecipeStepsViewModel(RecipeDatabase database, int recipeStepId) {
        recipeStep = database.recipeDao().loadRecipeStepsById(recipeStepId);
    }

    public LiveData<RecipeSteps> getRecipeStep() {
        return recipeStep;
    }
}
