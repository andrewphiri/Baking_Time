package com.example.bakingtime.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.bakingtime.database.entities.Recipe;
import com.example.bakingtime.database.entities.RecipeIngredients;
import com.example.bakingtime.database.entities.RecipeSteps;
import com.example.bakingtime.database.entities.RecipeWithIngredients;
import com.example.bakingtime.database.entities.RecipeWithSteps;

import java.util.List;
@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe_table")
    LiveData<List<Recipe>> loadAllRecipes();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRecipe(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRecipeSteps(RecipeSteps steps);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRecipeIngredients(RecipeIngredients ingredients);

    @Query("SELECT * FROM recipe_table LIMIT 1")
    Recipe [] getAnyRecipe();

    @Transaction
    @Query("SELECT * FROM recipe_table")
    LiveData<List<RecipeWithSteps>> getRecipeSteps();

    @Transaction
    @Query("SELECT * FROM recipe_table")
    LiveData<List<RecipeWithIngredients>> getRecipeIngredients();

    @Query("SELECT * FROM recipesteps WHERE stepsId= :id")
    LiveData<RecipeSteps> loadRecipeStepsById(int id);
}
