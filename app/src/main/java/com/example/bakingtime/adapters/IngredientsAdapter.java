package com.example.bakingtime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.R;
import com.example.bakingtime.database.entities.RecipeIngredients;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    List<RecipeIngredients> recipeIngredientsData;


    @NonNull
    @Override
    public IngredientsAdapter.IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.ingredients_item_list, parent, false);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.IngredientsViewHolder holder, int position) {
        RecipeIngredients recipe = recipeIngredientsData.get(position);
        holder.bindTo(recipe);
    }

    @Override
    public int getItemCount() {
        if (recipeIngredientsData == null) {
            return 0;
        } else {
            return recipeIngredientsData.size();
        }
    }

    public void setRecipeIngredients(List<RecipeIngredients> data) {
        recipeIngredientsData = data;
        notifyDataSetChanged();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        TextView recipeIngredientsTextView;
        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeIngredientsTextView = itemView.findViewById(R.id.ingredients_tv);
        }

        public void bindTo(RecipeIngredients ingredients) {

            String combinedIngredients = ingredients.getQuantity() + " " + ingredients.getMeasure()
                    + " " + ingredients.getIngredient();
            recipeIngredientsTextView.setText(combinedIngredients);
        }

    }
}
