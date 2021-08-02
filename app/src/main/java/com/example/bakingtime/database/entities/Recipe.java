package com.example.bakingtime.database.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_table")
public class Recipe {

    private String nameOfRecipe;
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;


    @Ignore
    public Recipe(String nameOfRecipe) {
        this.nameOfRecipe = nameOfRecipe;
    }

    public Recipe(int id, String nameOfRecipe) {
        this.id = id;
        this.nameOfRecipe = nameOfRecipe;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOfRecipe() {
        return nameOfRecipe;
    }

    public void setNameOfRecipe(String nameOfRecipe) {
        this.nameOfRecipe = nameOfRecipe;
    }

}
