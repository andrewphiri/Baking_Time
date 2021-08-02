package com.example.bakingtime.database;

import androidx.room.TypeConverter;


import com.example.bakingtime.database.entities.RecipeSteps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ConverterForSteps {
    @TypeConverter
    public static List<RecipeSteps> fromString(String value) {
        Type listType = new TypeToken<List<RecipeSteps>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<RecipeSteps> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
