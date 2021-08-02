package com.example.bakingtime.viewmodels;



import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingtime.database.RecipeDatabase;

public class RecipeStepsViewModelFactory implements ViewModelProvider.Factory {
    RecipeDatabase database;
    int stepsId;

    public RecipeStepsViewModelFactory(RecipeDatabase database, int stepsId) {
        this.database = database;
        this.stepsId = stepsId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeStepsViewModel(database, stepsId);
    }
}
