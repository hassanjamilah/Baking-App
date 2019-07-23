package com.andalus.bakingapp.Activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.andalus.bakingapp.Fragments.IngredientsFragment;
import com.andalus.bakingapp.Widget.MyBakingAppWidget;
import com.andalus.bakingapp.MyClasses.Ingredient;
import com.andalus.bakingapp.MyClasses.Step;
import com.andalus.bakingapp.R;
import com.andalus.bakingapp.Fragments.StepFragment;


import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity implements StepFragment.onPreviousStepClickListener, StepFragment.onNextStepClickListener {

    public static final String INGREDIENTS_FRAGMENT_BUNDLE_KEY = "ingKey";
    public static final String INGREDIENTS_STEP_BUNDLE_KEY = "stepKey";
    public static final String INGREDIENTS_STEPS_SIZE_BUNDLE_KEY = "stepssize";


    public static Bundle stepFragmentState;


    public static ArrayList<Ingredient> mIngredients;

    public static ArrayList<Step> mSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {


            mIngredients = bundle.getParcelableArrayList(MainActivity.RECIPE_DETAILS_INGREDIENTS_BUNDLE_KEY);
            mSteps = bundle.getParcelableArrayList(MainActivity.RECIPE_DETAILS_STEPS_BUNDLE_KEY);

        }

        int x = getResources().getConfiguration().smallestScreenWidthDp;
        if (x >= 600) {

            viewFragmentsForTab();


        } else {

            viewFragmentsPortrait();

        }


    }


    /**
     * this method is used to check if the orientation is landscape
     *
     * @param context
     * @return
     */
    public boolean isInLandscapeMode(Context context) {
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }


    /**
     * This method is used to load the fragments in portrait mode
     */
    public void viewFragmentsPortrait() {
        Fragment fra;

        fra = new IngredientsFragment();

        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(INGREDIENTS_FRAGMENT_BUNDLE_KEY, mIngredients);
        bundle.putParcelableArrayList(INGREDIENTS_STEPS_SIZE_BUNDLE_KEY, mSteps);


        fra.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.recipeDetails_container, fra)

                .commit();


    }


    /**
     * This method is used to load the fragments in landscape mode
     */
    public void viewFragmentsForTab() {

        Fragment fra = new IngredientsFragment();

        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList(INGREDIENTS_FRAGMENT_BUNDLE_KEY, mIngredients);
        bundle.putParcelableArrayList(INGREDIENTS_STEPS_SIZE_BUNDLE_KEY, mSteps);


        fra.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.recipeDetails_container, fra)

                .commit()
        ;

        if (stepFragmentState != null) {
            Step s = stepFragmentState.getParcelable(StepFragment.SAVE_INSTANCE_STATE_BUNDLE_STEP);
            // gotoStepFragment(s.getStepId());
        } else {
            gotoStepFragment(0);
        }


    }


    /**
     * This method is used to load the step details fragment
     */
    public void gotoStepFragment(int stepId) {


        StepFragment stepFragment = new StepFragment();

        Bundle bundle = new Bundle();

        bundle.putParcelable(INGREDIENTS_STEP_BUNDLE_KEY, mSteps.get(stepId));
        bundle.putInt(INGREDIENTS_STEPS_SIZE_BUNDLE_KEY, mSteps.size());


        stepFragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();

        int width = getResources().getConfiguration().smallestScreenWidthDp;


        if (width >= 600) {
            manager.beginTransaction().replace(R.id.recipeDetails_land_step_details, stepFragment)
                    //.addToBackStack("mystep")

                    .commit()
            ;

        } else {

        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        IngredientsFragment.mIngredients = null;
        AppWidgetManager manager1 = AppWidgetManager.getInstance(this);
        int[] ids = manager1.getAppWidgetIds(new ComponentName(this, MyBakingAppWidget.class));
        manager1.notifyAppWidgetViewDataChanged(ids, R.id.widget_listview_view);
    }

    /**
     * This method is used to handle the step details next button
     * to go the next step
     *
     * @param position
     */
    @Override
    public void onNextClicked(int position) {
        position++;
        if (position >= mSteps.size()) position = mSteps.size() - 1;
        gotoStepFragment(position);
    }

    /**
     * This method is used to handle the step details previous button
     * to go the previous step
     *
     * @param position
     */
    @Override
    public void onPrevious(int position) {
        position--;
        if (position < 0) position = 0;
        gotoStepFragment(position);
    }

}
