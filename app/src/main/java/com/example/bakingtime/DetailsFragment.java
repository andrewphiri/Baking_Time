package com.example.bakingtime;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.bakingtime.database.RecipeDatabase;
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
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {

    RecipeStepsViewModel recipeStepsViewModel;
    RecipeStepsViewModelFactory factory;
    RecipeViewModel recipeViewModel;
    RecipeViewModelFactory viewModelFactory;
    RecipeDatabase database;
    SimpleExoPlayer exoPlayer;
    PlayerView playerView;
    TextView instructionTextView;
    Button nextButton;
    Button previousButton;

    int DEFAULT_RECIPE_ID;
    int RECIPE_STEPS_LIST_SIZE;
    int itemClicked;
    int positionOfClickedList;

    String description;
    String videoUrl;
    String imageUrl;
    private ScrollView scrollView;
    private Guideline guideline;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        viewModelFactory = new RecipeViewModelFactory(getActivity().getApplication());
        recipeViewModel = new ViewModelProvider(this, viewModelFactory).get(RecipeViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize views
        playerView = view.findViewById(R.id.playerView);
        instructionTextView =view.findViewById(R.id.instruction_tv);
        scrollView = view.findViewById(R.id.scrollView);
        guideline = view.findViewById(R.id.horizontalHalf);
        nextButton = view.findViewById(R.id.button_next);
        previousButton = view.findViewById(R.id.button_prev);

        nextButton.setOnClickListener(this::onClick);
        previousButton.setOnClickListener(this::onClick);

        playerView.setDefaultArtwork(ContextCompat.getDrawable(getActivity().getApplicationContext(),
                R.drawable.artwork_media_foreground));

        DEFAULT_RECIPE_ID = requireArguments().getInt(SingleRecipeActivity.STEPS_ID, 1);
        RECIPE_STEPS_LIST_SIZE = requireArguments().getInt(SingleRecipeActivity.STEPS_LIST_SIZE, 0);
        itemClicked = requireArguments().getInt(SingleRecipeActivity.POSITION_OF_CLICKED_STEP,0);
        positionOfClickedList = requireArguments().getInt(SingleRecipeActivity.CLICKED_STEPS_LIST,0);

        enableDisableButtons();

        playVideoInFullscreen();

        loadRecipes();
        
    }

    private void initializePlayer(Uri uri) {
        if (exoPlayer == null) {
            //Create instance of Exoplayer
            TrackSelector selector = new DefaultTrackSelector(getActivity().getApplicationContext());
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = new SimpleExoPlayer.Builder(getActivity().getApplicationContext())
                    .setTrackSelector(selector)
                    .setLoadControl(loadControl)
                    .build();

            playerView.setPlayer(exoPlayer);

            MediaItem mediaItem = new MediaItem.Builder()
                    .setUri(uri)
                    .build();
            exoPlayer.addMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
    public void PreviousOrNext(View view) {
        releasePlayer();
        int id = view.getId();
        switch (id) {
            case R.id.button_prev:
                DEFAULT_RECIPE_ID--;
                itemClicked--;
                enableDisableButtons();
                break;
            case R.id.button_next:
                DEFAULT_RECIPE_ID++;
                itemClicked++;
                enableDisableButtons();
                break;
            default:break;
        }
        loadRecipes();
        Log.d("ITEM CLICKED", String.valueOf(itemClicked));
        Log.d("RECIPE_ID", String.valueOf(DEFAULT_RECIPE_ID));
    }

    private void loadRecipes() {
        recipeViewModel.getAllRecipeSteps().observe(getActivity(), new Observer<List<RecipeWithSteps>>() {
            @Override
            public void onChanged(List<RecipeWithSteps> recipeWithSteps) {
                description = recipeWithSteps.get(positionOfClickedList).getRecipeSteps()
                        .get(itemClicked).getDescription();
                videoUrl = recipeWithSteps.get(positionOfClickedList).getRecipeSteps()
                        .get(itemClicked).getVideoUrl();
                imageUrl = recipeWithSteps.get(positionOfClickedList).getRecipeSteps()
                        .get(itemClicked).getImageURL();

                getActivity().setTitle(recipeWithSteps.get(positionOfClickedList).getRecipeSteps()
                        .get(itemClicked).getRecipeName());

                if (videoUrl != null) {
                    initializePlayer(Uri.parse(videoUrl));
                } else if (imageUrl != null){
                    initializePlayer(Uri.parse(imageUrl));
                } else {
                    releasePlayer();
                }

                if (instructionTextView.getVisibility() == View.VISIBLE) {
                    instructionTextView.setText(description);
                }
            }
        });
    }

    private void enableDisableButtons() {
        if (itemClicked == 0) {
            previousButton.setEnabled(false);
        } else {
            previousButton.setEnabled(true);
        }

        if (RECIPE_STEPS_LIST_SIZE - 1 == itemClicked) {
            nextButton.setEnabled(false);
        } else {
            nextButton.setEnabled(true);
        }
    }
    private void playVideoInFullscreen() {
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                && !isTablet) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                    playerView.getLayoutParams();
            params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
            playerView.setLayoutParams(params);
            scrollView.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
            previousButton.setVisibility(View.GONE);
            instructionTextView.setVisibility(View.GONE);
            guideline.setVisibility(View.GONE);

            if (((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
            }
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if (isTablet) {
            nextButton.setVisibility(View.GONE);
            previousButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onClick(View view) {
        PreviousOrNext(view);
    }
}