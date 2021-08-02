package com.example.bakingtime.database.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RecipeWithIngredients {
    @Embedded public Recipe recipe;
    @Relation(
            parentColumn = "nameOfRecipe",
            entityColumn = "recipe"
    )
    public List<RecipeIngredients> recipeIngredients;

    public RecipeWithIngredients(List<RecipeIngredients> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public List<RecipeIngredients> getRecipeIngredients() {
        return recipeIngredients;
    }
}
