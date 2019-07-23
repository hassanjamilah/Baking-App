package com.andalus.bakingapp.Fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andalus.bakingapp.Activities.RecipeDetailsActivity;
import com.andalus.bakingapp.Activities.StepActivity;
import com.andalus.bakingapp.ListAdapters.IngredientAdapter;
import com.andalus.bakingapp.Widget.MyBakingAppWidget;
import com.andalus.bakingapp.MyClasses.Ingredient;
import com.andalus.bakingapp.MyClasses.Step;
import com.andalus.bakingapp.R;
import com.andalus.bakingapp.ListAdapters.StepAdapter;
import com.andalus.bakingapp.databinding.FragmentIngredientsBinding;

import java.util.ArrayList;


public class IngredientsFragment extends Fragment implements IngredientAdapter.IngredientClickHandler {


    public static final String INGREDIENTS_STEP_BUNDLE_KEY = "stepKey";
    public static final String INGREDIENTS_STEPS_SIZE_BUNDLE_KEY = "stepssize";
    private static final String SAVE_INSTANCE_STATE_BUNDLE_INGREDIENTS = "saveBundleIngredients";
    private static final String SAVE_INSTANCE_STATE_BUNDLE_STPES = "saveBundleSteps";


    public static ArrayList<Ingredient> mIngredients;
    public static ArrayList<Step> mSteps;

    private int currentStepID;

    FragmentIngredientsBinding mBinding;


    public IngredientsFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mIngredients = getArguments().getParcelableArrayList(RecipeDetailsActivity.INGREDIENTS_FRAGMENT_BUNDLE_KEY);
            mSteps = getArguments().getParcelableArrayList(RecipeDetailsActivity.INGREDIENTS_STEPS_SIZE_BUNDLE_KEY);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredients, container, false);
        fillRecyclerView();
        fillStepsRecyclerView();

        return mBinding.getRoot();
    }

    /**
     * This method is used to fill the recyclerviews with data
     */
    private void fillRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        IngredientAdapter adapter = new IngredientAdapter(this);
        adapter.setData(mIngredients);

        mBinding.IngredientFragmentRecyclerView.setLayoutManager(manager);
        mBinding.IngredientFragmentRecyclerView.setAdapter(adapter);
        mBinding.IngredientFragmentRecyclerView.setHasFixedSize(true);


        updateListFragment();


    }


    /**
     * This method is used to fill the steps recycler view with data
     */
    private void fillStepsRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        StepAdapter adapter = new StepAdapter(new StepAdapter.StepClickHandler() {
            @Override
            public void onClick(Step step) {
                StepFragment.currentPlayPos = 0;

                int x = getResources().getConfiguration().smallestScreenWidthDp;
                int orientatoin = getResources().getConfiguration().orientation;
                if (x >= 600) {
                    if (orientatoin == Configuration.ORIENTATION_LANDSCAPE) {
                        gotoStepFragment(step.getStepId(), true);
                    } else {
                        gotoStepFragment(step.getStepId(), false);
                    }
                } else {

                    gotoStepFragment(step.getStepId(), false);

                }

            }
        });
        adapter.setData(mSteps);


        mBinding.IngredientFragmentStepRecyclerView.setLayoutManager(manager);
        mBinding.IngredientFragmentStepRecyclerView.setAdapter(adapter);
        mBinding.IngredientFragmentStepRecyclerView.setHasFixedSize(true);


    }


    /**
     * This method is used to load and initialize the step details fragment
     *
     * @param stepId
     * @param isLandScape
     */
    private void gotoStepFragment(int stepId, boolean isLandScape) {

        if (getActivity() != null) {
            FragmentManager manager = getActivity().getSupportFragmentManager();

            Bundle bundle = new Bundle();

            bundle.putParcelable(INGREDIENTS_STEP_BUNDLE_KEY, mSteps.get(stepId));
            bundle.putInt(INGREDIENTS_STEPS_SIZE_BUNDLE_KEY, mSteps.size());
            currentStepID = mSteps.get(stepId).getStepId();

            int width = getResources().getConfiguration().smallestScreenWidthDp;
            if (width >= 600) {
                StepFragment stepFragment = new StepFragment();
                stepFragment.setArguments(bundle);
                manager.beginTransaction().replace(R.id.recipeDetails_land_step_details, stepFragment)
                        .addToBackStack("Ingredients")
                        .commit()
                ;
            } else {

                Intent intent = new Intent(getContext(), StepActivity.class);


                intent.putExtras(bundle);
                startActivity(intent);


            }

        }


    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(SAVE_INSTANCE_STATE_BUNDLE_INGREDIENTS, mIngredients);
        outState.putParcelableArrayList(SAVE_INSTANCE_STATE_BUNDLE_STPES, mSteps);
        super.onSaveInstanceState(outState);
    }


    /**
     * This method is used to update the widget data
     */
    private void updateListFragment() {
        AppWidgetManager manager1 = AppWidgetManager.getInstance(getContext());
        int[] ids = manager1.getAppWidgetIds(new ComponentName(getContext(), MyBakingAppWidget.class));
        manager1.notifyAppWidgetViewDataChanged(ids, R.id.widget_listview_view);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mIngredients = null;
    }

    @Override
    public void onClick(Ingredient ing) {

    }


}
