package com.example.bakingtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.bakingtime.adapters.IngredientsAdapter;
import com.example.bakingtime.adapters.StepsAdapter;
import com.example.bakingtime.database.entities.RecipeSteps;
import com.example.bakingtime.viewmodels.RecipeViewModel;
import com.example.bakingtime.viewmodels.RecipeViewModelFactory;
import com.example.bakingtime.database.entities.RecipeWithIngredients;
import com.example.bakingtime.database.entities.RecipeWithSteps;

import java.util.List;

import static com.example.bakingtime.SingleRecipeActivity.CLICKED_STEPS_LIST;
import static com.example.bakingtime.SingleRecipeActivity.POSITION_OF_CLICKED_STEP;
import static com.example.bakingtime.SingleRecipeActivity.STEPS_ID;
import static com.example.bakingtime.SingleRecipeActivity.STEPS_LIST_SIZE;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailsFragment.OnRecipeStepClickedListener {
    public static final String RECIPE_STEP_ID = "com.example.bakingtime.RECIPE_STEP_ID" ;
    public static final String RECIPE_STEP_LIST_SIZE = "com.example.bakingtime.RECIPE_STEP_LIST_SIZE" ;
    public static final String ITEM_CLICKED_POSITION = "com.example.bakingtime.ITEM_CLICKED_POSITION";
    public static final String STEP_DESCRIPTION = "com.example.bakingtime.STEP_DESCRIPTION";
    public static final String STEP_VIDEO_URL = "com.example.bakingtime.STEP_VIDEO_URL" ;
    public static final String STEP_IMAGE_URL = "com.example.bakingtime.STEP_IMAGE_URL";
    public static final String CLICKED_POSITION_LIST = "com.example.bakingtime.CLICKED_POSITION_LIST";

    Button nextButton;
    Button previousButton;

    RecyclerView recyclerView;

    int positionClicked;
    boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        nextButton = findViewById(R.id.button_next);
        previousButton = findViewById(R.id.button_prev);
        findViewById(R.id.recipe_steps_recyclerView);

        Intent intent = getIntent();
        positionClicked = intent.getIntExtra(MainActivity.POSITION_CLICKED, -1);

        if (findViewById(R.id.recipe_details_linear_layout) != null) {
            mTwoPane = true;

            Bundle bundle = getIntent().getExtras();
            bundle.putInt(STEPS_ID, 1);
            bundle.putInt(POSITION_OF_CLICKED_STEP, 0);
            bundle.putInt(CLICKED_STEPS_LIST, positionClicked);


            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.setReorderingAllowed(true)
                    .add(R.id.master_list_container_fragment, DetailsFragment.class, bundle)
                    .commit();

        } else {
            mTwoPane = false;
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        RecipeDetailsFragment recipeDetailsFragment = RecipeDetailsFragment.newInstance(positionClicked);

        if (savedInstanceState == null) {
            fragmentTransaction.setReorderingAllowed(true)
                    .add(R.id.fragmentContainerView, recipeDetailsFragment)
                    .commit();
        }

    }


    @Override
    public void onStepClicked(List<RecipeSteps> data, int position) {

        Bundle bundle = new Bundle();
        //bundle.putInt(RECIPE_STEP_ID, data.get(position).getStepsId());
        bundle.putInt(RECIPE_STEP_LIST_SIZE, data.size());
        bundle.putInt(ITEM_CLICKED_POSITION, position);
        bundle.putInt(CLICKED_POSITION_LIST, positionClicked);
        bundle.putString(STEP_DESCRIPTION, data.get(position).getDescription());
        bundle.putString(STEP_VIDEO_URL, data.get(position).getVideoUrl());
        bundle.putString(STEP_IMAGE_URL, data.get(position).getImageURL());

        if (mTwoPane) {
            //bundle.putInt(STEPS_ID, data.get(position).getStepsId());
            bundle.putInt(STEPS_LIST_SIZE, data.size());
            bundle.putInt(POSITION_OF_CLICKED_STEP, position);
            bundle.putInt(CLICKED_STEPS_LIST, positionClicked);

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();

            fragmentTransaction
                    .replace(R.id.master_list_container_fragment, DetailsFragment.class, bundle)
                    .commit();
            Log.d("POSITIONOFCLICKEDESC", data.get(position).getDescription());
        } else {
            Intent intent = new Intent(this, SingleRecipeActivity.class);

            intent.putExtras(bundle);
            startActivity(intent);
        }

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction;

        RecipeDetailsFragment recipeDetailsFragment = (RecipeDetailsFragment)
                manager.findFragmentById(R.id.fragmentContainerView);

        if (recipeDetailsFragment != null) {
             fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(recipeDetailsFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeFragment();
    }
}