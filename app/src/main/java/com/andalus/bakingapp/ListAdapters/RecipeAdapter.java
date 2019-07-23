package com.andalus.bakingapp.ListAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andalus.bakingapp.MyClasses.Recipe;
import com.andalus.bakingapp.R;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {


    private final RecipeClickHanlder recipeClickHandler;

    private ArrayList<Recipe> myData = new ArrayList<>();

    private Context mContext;


    public RecipeAdapter(RecipeClickHanlder hanlder) {
        this.recipeClickHandler = hanlder;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        Recipe r = myData.get(i);
        recipeViewHolder.mRecipeNameTextView.setText(r.getName());
        //final int resId = mContext.getResources().getIdentifier(r.getImageUrl(), "drawable", mContext.getPackageName());
        int resid = 0 ;
        switch (r.getName()){
            case "Nutella Pie":
                resid = R.drawable.nutell_pie;
                break ;
            case "Brownies":
                resid = R.drawable.brownies;
                break ;
            case "Yellow Cake":
                resid = R.drawable.yellow_cake;
                break ;
            case "Cheesecake":
                resid = R.drawable.cheese_cake;
                break ;
             default:
                    resid = R.drawable.widget_logo ;
        }



        recipeViewHolder.mRecipeImageView.setImageResource(resid);

        if (r.getIngredients() != null && r.getIngredients().size() > 0) {
            recipeViewHolder.mRecipeNumIngredientsTextView.setText(String.valueOf(r.getIngredients().size()));
        }
        if (r.getSteps() != null && r.getSteps().size() > 0) {
            recipeViewHolder.mRecipeNumStepsTextView.setText(String.valueOf(r.getSteps().size()));
        }
        recipeViewHolder.mRecipeServingsTextView.setText(String.valueOf(r.getServings()));
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }


    public void setData(ArrayList<Recipe> recipes) {
        this.myData = recipes;
        notifyDataSetChanged();
    }

    public interface RecipeClickHanlder {
        void onClick(Recipe r);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView mRecipeImageView;
        final TextView mRecipeNameTextView;
        final TextView mRecipeNumIngredientsTextView;
        final TextView mRecipeNumStepsTextView;
        final TextView mRecipeServingsTextView;


        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            mRecipeImageView = itemView.findViewById(R.id.recipeListItem_ImageView);
            mRecipeNameTextView = itemView.findViewById(R.id.ingredientListItem_IngredientName_TextView);
            mRecipeNumIngredientsTextView = itemView.findViewById(R.id.ingredientListItem_Quantity_TextView);
            mRecipeNumStepsTextView = itemView.findViewById(R.id.recipeListItem_NumSteps_TextView2);
            mRecipeServingsTextView = itemView.findViewById(R.id.recipeListItem_Servings_TextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Recipe r = myData.get(pos);
            recipeClickHandler.onClick(r);


        }
    }
}
