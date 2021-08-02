package com.example.bakingtime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.R;
import com.example.bakingtime.database.entities.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    List<Recipe> recipeData;
    ItemClickListener listener;

    public RecipeAdapter(ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener{
        void onItemClick(List<Recipe> data, int position);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recipe_item_list, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeData.get(position);
        holder.bindTo(recipe);
    }

    @Override
    public int getItemCount() {
        if (recipeData == null) {
            return 0;
        } else {
            return recipeData.size();
        }
    }

    public void setRecipes(List<Recipe> data) {
        recipeData = data;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeTextView;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTextView = itemView.findViewById(R.id.recipe_name_textView);
            itemView.setOnClickListener(this);
        }

        public void bindTo(Recipe recipe) {
            recipeTextView.setText(recipe.getNameOfRecipe());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.onItemClick(recipeData, position);
        }
    }
}
