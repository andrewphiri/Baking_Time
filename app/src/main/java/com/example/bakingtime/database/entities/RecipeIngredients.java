package com.example.bakingtime.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class RecipeIngredients {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String recipe;
    private String quantity;
    private String measure;
    private String ingredient;

    @Ignore
    public RecipeIngredients(String recipe, String quantity, String measure, String ingredient) {
        this.recipe = recipe;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public RecipeIngredients(int id, String recipe, String quantity, String measure, String ingredient) {
        this.id = id;
        this.recipe = recipe;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getId() {
        return id;
    }
}
