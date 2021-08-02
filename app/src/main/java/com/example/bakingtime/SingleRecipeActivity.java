package com.example.bakingtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.bakingtime.database.RecipeDatabase;
import com.example.bakingtime.database.entities.RecipeSteps;
import com.example.bakingtime.database.entities.RecipeWithSteps;
import com.example.bakingtime.viewmodels.RecipeStepsViewModel;
import com.example.bakingtime.viewmodels.RecipeStepsViewModelFactory;
import com.example.bakingtime.viewmodels.RecipeViewModel;
import com.example.bakingtime.viewmodels.RecipeViewModelFactory;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

public class SingleRecipeActivity extends AppCompatActivity {

    public static final String STEPS_ID = "com.example.bakingtime.STEPS_ID";
    public static final String STEPS_LIST_SIZE = "com.example.bakingtime.STEPS_LIST_SIZE";
    public static final String POSITION_OF_CLICKED_STEP = "com.example.bakingtime.POSITION_OF_CLICKED_STEP";
    public static final String CLICKED_STEPS_LIST = "com.example.bakingtime.CLICKED_STEPS_LIST";

    int DEFAULT_RECIPE_ID;
    int RECIPE_STEPS_LIST_SIZE;
    int itemClicked;
    int positionOfClickedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);

        //Retrieve values passed from RecipeDetailsFragment
        Bundle b = getIntent().getExtras();
        if (b != null) {
            DEFAULT_RECIPE_ID = b.getInt(RecipeDetailActivity.RECIPE_STEP_ID, 1);
            RECIPE_STEPS_LIST_SIZE = b.getInt(RecipeDetailActivity.RECIPE_STEP_LIST_SIZE,
                    0);
            itemClicked = b.getInt(RecipeDetailActivity.ITEM_CLICKED_POSITION, 0);
            positionOfClickedList = b.getInt(RecipeDetailActivity.CLICKED_POSITION_LIST,
                    0);

            Log.d("POSITIONOFCLICKEDId", String.valueOf(DEFAULT_RECIPE_ID));
        }

        //Put these values in a bundle and pass them to the attached fragment
        Bundle bundle = new Bundle();
        bundle.putInt(STEPS_ID, DEFAULT_RECIPE_ID);
        bundle.putInt(STEPS_LIST_SIZE, RECIPE_STEPS_LIST_SIZE);
        bundle.putInt(POSITION_OF_CLICKED_STEP, itemClicked);
        bundle.putInt(CLICKED_STEPS_LIST, positionOfClickedList);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        if (savedInstanceState == null) {
            fragmentTransaction.setReorderingAllowed(true)
                    .add(R.id.single_recipe_fragment_container, DetailsFragment.class, bundle)
                    .commit();
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

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        DetailsFragment detailsFragment = (DetailsFragment)
                manager.findFragmentById(R.id.single_recipe_fragment_container);

        if (detailsFragment != null && !isTablet) {
            fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.remove(detailsFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeFragment();
    }
}