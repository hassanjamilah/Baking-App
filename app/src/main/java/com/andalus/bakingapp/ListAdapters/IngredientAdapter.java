package com.andalus.bakingapp.ListAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.andalus.bakingapp.MyClasses.Ingredient;
import com.andalus.bakingapp.R;
import com.andalus.bakingapp.Activities.RecipeDetailsActivity;
import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private final IngredientClickHandler ingredientClickHandler;
    private ArrayList<Ingredient> myData = new ArrayList<>();
    private Context mContext;


    public IngredientAdapter(IngredientClickHandler handler) {
        this.ingredientClickHandler = handler;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.ingredient_list_item, parent, false);


        return new IngredientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder ingredientViewHolder, int i) {
        Ingredient ing = myData.get(i);
        ingredientViewHolder.mIngredientNameTextView.setText(ing.getIngredientName());
        ingredientViewHolder.mMeasureTextView.setText(ing.getMeasure());
        ingredientViewHolder.mQuantityTextView.setText(String.valueOf(ing.getQuantity()));
    }


    @Override
    public int getItemCount() {
        if (myData == null) {
            myData = RecipeDetailsActivity.mIngredients;

        }
        return myData.size();


    }


    public void setData(ArrayList<Ingredient> ingredients) {
        this.myData = ingredients;
        notifyDataSetChanged();
    }

    public interface IngredientClickHandler {
        void onClick(Ingredient ing);
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        final TextView mIngredientNameTextView;
        final TextView mQuantityTextView;
        final TextView mMeasureTextView;


        private IngredientViewHolder(@NonNull View itemView) {
            super(itemView);


            mIngredientNameTextView = itemView.findViewById(R.id.ingredientListItem_IngredientName_TextView);
            mQuantityTextView = itemView.findViewById(R.id.ingredientListItem_Quantity_TextView);
            mMeasureTextView = itemView.findViewById(R.id.ingredientListItem_Measure_TextView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Ingredient ing = myData.get(pos);
            ingredientClickHandler.onClick(ing);


        }
    }
}
