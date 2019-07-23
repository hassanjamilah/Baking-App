package com.andalus.bakingapp.ListAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andalus.bakingapp.MyClasses.Step;
import com.andalus.bakingapp.R;

import java.util.ArrayList;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {


    private final StepClickHandler stepClickHandler;
    private ArrayList<Step> myData = new ArrayList<>();
    private Context mContext;
    public StepAdapter(StepClickHandler handler) {
        this.stepClickHandler = handler;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.step_list_item, parent, false);
        return new StepViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder ingredientViewHolder, int i) {
        Step st = myData.get(i);
        ingredientViewHolder.mStepNumberTextView.setText("Step ".concat(String.valueOf(i + 1)));
        ingredientViewHolder.mStepDescriptionTextView.setText(st.getShortDescription());

    }


    @Override
    public int getItemCount() {
        return myData.size();
    }


    public void setData(ArrayList<Step> steps) {
        this.myData = steps;
        notifyDataSetChanged();
    }

    public interface StepClickHandler {
        void onClick(Step step);
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        final TextView mStepNumberTextView;
        final TextView mStepDescriptionTextView;


        public StepViewHolder(@NonNull View itemView) {
            super(itemView);


            mStepDescriptionTextView = itemView.findViewById(R.id.step_recyclerview_stepDescription);
            mStepNumberTextView = itemView.findViewById(R.id.step_recyclerview_stepNumber);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Step st = myData.get(pos);
            stepClickHandler.onClick(st);


        }
    }
}
