package com.example.bakingtime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;


import com.example.bakingtime.adapters.RecipeAdapter;
import com.example.bakingtime.database.entities.Recipe;
import com.example.bakingtime.viewmodels.RecipeViewModel;
import com.example.bakingtime.viewmodels.RecipeViewModelFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ItemClickListener {

    public static final String POSITION_CLICKED = "com.example.bakingtime.POSITION_CLICKED";
    RecipeAdapter adapter;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int columns = getResources().getInteger(R.integer.columns);

        RecipeViewModelFactory factory = new RecipeViewModelFactory(this.getApplication());
        RecipeViewModel viewModel = new ViewModelProvider(this, factory).get(RecipeViewModel.class);

        recyclerView = findViewById(R.id.recipe_list_recyclerView);
        adapter = new RecipeAdapter(this);
        layoutManager = new GridLayoutManager(this, columns);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        viewModel.loadAllRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setRecipes(recipes);
            }
        });
    }

    @Override
    public void onItemClick(List<Recipe> data, int position) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(POSITION_CLICKED, position);
        startActivity(intent);
    }
}