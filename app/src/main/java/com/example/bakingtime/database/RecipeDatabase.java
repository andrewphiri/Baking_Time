package com.example.bakingtime.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.bakingtime.database.entities.Recipe;
import com.example.bakingtime.database.entities.RecipeIngredients;
import com.example.bakingtime.database.entities.RecipeSteps;

@Database(entities = {Recipe.class, RecipeSteps.class, RecipeIngredients.class}, version = 3, exportSchema = false)

public abstract class RecipeDatabase extends RoomDatabase{

    private static final String DATABASE_NAME = "RECIPE_LIST" ;

    public abstract RecipeDao recipeDao();
    private static RecipeDatabase INSTANCE;

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE recipes " + "ADD COLUMN id INTEGER");
        }
    };


    public static RecipeDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (RecipeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeDatabase.class, RecipeDatabase.DATABASE_NAME)
                            .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
