package com.example.bakingtime.database;

import androidx.room.TypeConverter;


import com.example.bakingtime.database.entities.RecipeIngredients;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import java.util.List;

public class ConverterForIngredients {
    @TypeConverter
    public static List<RecipeIngredients> fromString(String value) {
        Type listType = new TypeToken<List<RecipeIngredients>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<RecipeIngredients> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

//        @TypeConverter
//        public List<RecipeIngredients> storedStringToEnum(String value) {
//            List<String> dbValues = Arrays.asList(value.split("\\s*,\\s*"));
//            List<RecipeIngredients> enums = new ArrayList<>();
//
//            for (String s: dbValues);
//
//
//            return enums;
//        }
//
//        @TypeConverter
//        public String languagesToStoredString(List<RecipeIngredients> cl) {
//            String value = "";
//
//            for (RecipeIngredients lang : cl);
//
//            return value;
//        }
    }
