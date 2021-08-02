package com.example.bakingtime.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingtime.R;
import com.example.bakingtime.database.entities.RecipeSteps;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    List<RecipeSteps> recipeStepsData;
    ClickListener listener;
    int mSelectedItem;
    boolean isTablet;
    private Context context;

    public StepsAdapter(ClickListener listener) {
        this.listener = listener;
    }

    public interface ClickListener {
        void onItemClick(List<RecipeSteps> data, int position);
    }

    @NonNull
    @Override
    public StepsAdapter.StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        isTablet = context.getResources().getBoolean(R.bool.isTablet);

        View view = inflater.inflate(R.layout.recipe_steps_list, parent, false);

        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.StepsViewHolder holder, int position) {
        RecipeSteps recipe = recipeStepsData.get(position);
        holder.bindTo(recipe);
        holder.recipeStepsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedItem = position;
                listener.onItemClick(recipeStepsData, position);
                notifyDataSetChanged();
            }
        });

        if (mSelectedItem == position && isTablet) {
            holder.recipeStepsTextView.setBackgroundColor(Color.GREEN);
        } else {
            holder.recipeStepsTextView.setBackground(context.getResources().getDrawable(R.drawable.background_selector));
        }

    }

    @Override
    public int getItemCount() {
        if (recipeStepsData == null) {
            return 0;
        } else {
            return recipeStepsData.size();
        }
    }

    public void setRecipeSteps(List<RecipeSteps> data) {
        recipeStepsData = data;
        notifyDataSetChanged();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {

        TextView recipeStepsTextView;
        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeStepsTextView = itemView.findViewById(R.id.recipe_steps_tv);

        }

        public void bindTo(RecipeSteps recipe) {
            recipeStepsTextView.setText(recipe.getDescription());
        }
    }
}
