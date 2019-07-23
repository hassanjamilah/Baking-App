package com.andalus.bakingapp.Activities;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.andalus.bakingapp.Fragments.IngredientsFragment;
import com.andalus.bakingapp.Fragments.StepFragment;
import com.andalus.bakingapp.MyClasses.Step;
import com.andalus.bakingapp.R;
import com.andalus.bakingapp.databinding.ActivityStepBinding;

public class StepActivity extends AppCompatActivity implements StepFragment.onNextStepClickListener, StepFragment.onPreviousStepClickListener {

    private static final String SAVE_INSTANCE_STATE_STEP = "TheStep";
    private static final String SAVE_INSTANCE_STATE_STEP_SIZE = "The step size";
    private int currentStepID;

    ActivityStepBinding mBinding;
    Step mStep;
    int stepsSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_step);
        setContentView(R.layout.activity_step);


        if (savedInstanceState != null) {

            mStep = savedInstanceState.getParcelable(SAVE_INSTANCE_STATE_STEP);
            stepsSize = savedInstanceState.getInt(SAVE_INSTANCE_STATE_STEP_SIZE);
        } else if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            mStep = extras.getParcelable(IngredientsFragment.INGREDIENTS_STEP_BUNDLE_KEY);
            stepsSize = extras.getInt(IngredientsFragment.INGREDIENTS_STEPS_SIZE_BUNDLE_KEY);

            StepFragment stepFragment = new StepFragment();
            Bundle bundle = new Bundle();

            bundle.putParcelable(IngredientsFragment.INGREDIENTS_STEP_BUNDLE_KEY, mStep);
            bundle.putInt(IngredientsFragment.INGREDIENTS_STEPS_SIZE_BUNDLE_KEY, stepsSize);

            stepFragment.setArguments(bundle);

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.step_container1, stepFragment)
                    //   .addToBackStack("Ingredients")
                    .commit();

        }
    }


    /**
     * This method is used to load the step details fragment
     */
    public void gotoStepFragment(int stepId) {


        StepFragment stepFragment = new StepFragment();

        Bundle bundle = new Bundle();

        mStep = RecipeDetailsActivity.mSteps.get(stepId);
        bundle.putParcelable(RecipeDetailsActivity.INGREDIENTS_STEP_BUNDLE_KEY, mStep);
        bundle.putInt(RecipeDetailsActivity.INGREDIENTS_STEPS_SIZE_BUNDLE_KEY, stepsSize);


        stepFragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();


        manager.beginTransaction().replace(R.id.step_container1, stepFragment)
                //.addToBackStack("mystep")
                .commit()
        ;


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVE_INSTANCE_STATE_STEP, mStep);
        outState.putInt(SAVE_INSTANCE_STATE_STEP_SIZE, stepsSize);

    }

    @Override
    public void onNextClicked(int position) {

        position++;

        if (position >= stepsSize) position = stepsSize - 1;
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
