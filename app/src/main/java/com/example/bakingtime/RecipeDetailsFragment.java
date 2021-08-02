package com.example.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingtime.adapters.IngredientsAdapter;
import com.example.bakingtime.adapters.StepsAdapter;
import com.example.bakingtime.database.entities.RecipeSteps;
import com.example.bakingtime.database.entities.RecipeWithIngredients;
import com.example.bakingtime.database.entities.RecipeWithSteps;
import com.example.bakingtime.viewmodels.RecipeViewModel;
import com.example.bakingtime.viewmodels.RecipeViewModelFactory;

import java.util.List;

import static com.example.bakingtime.RecipeDetailActivity.CLICKED_POSITION_LIST;
import static com.example.bakingtime.RecipeDetailActivity.ITEM_CLICKED_POSITION;
import static com.example.bakingtime.RecipeDetailActivity.RECIPE_STEP_ID;
import static com.example.bakingtime.RecipeDetailActivity.RECIPE_STEP_LIST_SIZE;
import static com.example.bakingtime.RecipeDetailActivity.STEP_DESCRIPTION;
import static com.example.bakingtime.RecipeDetailActivity.STEP_IMAGE_URL;
import static com.example.bakingtime.RecipeDetailActivity.STEP_VIDEO_URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailsFragment extends Fragment implements StepsAdapter.ClickListener {

    StepsAdapter stepsAdapter;
    RecyclerView stepsRecyclerView;
    LinearLayoutManager layoutManagerSteps;
    LinearLayoutManager layoutManagerIngredients;

    IngredientsAdapter ingredientsAdapter;
    RecyclerView ingredientsRecyclerView;
    OnRecipeStepClickedListener mCallback;

    public interface OnRecipeStepClickedListener{
        void onStepClicked(List<RecipeSteps> data, int position);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnRecipeStepClickedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String POSITION_CLICKED = "POSITION_CLICKED";

    // TODO: Rename and change types of parameters
    int positionClicked;
    private String mParam2;
    private RecipeViewModelFactory factory;
    private RecipeViewModel viewModel;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @return A new instance of fragment RecipeDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeDetailsFragment newInstance(int position) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION_CLICKED, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            positionClicked = getArguments().getInt(POSITION_CLICKED);
        }
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        factory = new RecipeViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, factory).get(RecipeViewModel.class);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        stepsRecyclerView = view.findViewById(R.id.recipe_steps_recyclerView);
        ingredientsRecyclerView = view.findViewById(R.id.ingredients_recyclerView);

        layoutManagerSteps = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManagerIngredients = new LinearLayoutManager(getActivity().getApplicationContext());
        stepsAdapter = new StepsAdapter(this);
        ingredientsAdapter = new IngredientsAdapter();

        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsRecyclerView.setLayoutManager(layoutManagerSteps);

        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        ingredientsRecyclerView.setLayoutManager(layoutManagerIngredients);

        loadRecipeStepsAndIngredients();
    }

    public void loadRecipeStepsAndIngredients() {
        viewModel.getAllRecipeSteps().observe(getActivity(), new Observer<List<RecipeWithSteps>>() {
            @Override
            public void onChanged(List<RecipeWithSteps> recipeWithSteps) {
                stepsAdapter.setRecipeSteps(recipeWithSteps.get(positionClicked)
                        .getRecipeSteps());

                getActivity().setTitle(recipeWithSteps.get(positionClicked).getRecipeSteps()
                        .get(1).getRecipeName());
            }
        });

        viewModel.getRecipeWithIngredients().observe(getActivity(), new Observer<List<RecipeWithIngredients>>() {
            @Override
            public void onChanged(List<RecipeWithIngredients> recipeWithIngredients) {
                ingredientsAdapter.setRecipeIngredients(recipeWithIngredients.get(positionClicked)
                        .getRecipeIngredients());
            }
        });
    }

    @Override
    public void onItemClick(List<RecipeSteps> data, int position) {
      //  Intent intent = new Intent(getActivity().getApplicationContext(), SingleRecipeActivity.class);

        mCallback.onStepClicked(data, position);
        System.out.println("Position/ID " + data.size());
       // intent.putExtras(bundle);
       // startActivity(intent);
    }

}