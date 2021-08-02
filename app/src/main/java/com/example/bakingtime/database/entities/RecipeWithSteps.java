package com.example.bakingtime.database.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RecipeWithSteps {
    @Embedded public Recipe recipe;
    @Relation(
            parentColumn = "nameOfRecipe",
            entityColumn = "recipeName"
    )
    public List<RecipeSteps> recipeSteps;

    public RecipeWithSteps(List<RecipeSteps> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public List<RecipeSteps> getRecipeSteps() {
        return recipeSteps;
    }
}
