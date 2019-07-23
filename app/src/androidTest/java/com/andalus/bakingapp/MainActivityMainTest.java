package com.andalus.bakingapp;

import static android.support.test.espresso.Espresso.* ;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.matcher.ViewMatchers.* ;
import static android.support.test.espresso.action.ViewActions.click ;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId  ;


import android.support.test.espresso.contrib.RecyclerViewActions ;
import android.util.Log;
import android.view.View;

import com.andalus.bakingapp.Activities.MainActivity;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith ;

@RunWith(AndroidJUnit4.class)
public class MainActivityMainTest {
    private IdlingResource mIdlingResource;


    @Rule
    public ActivityTestRule<MainActivity> mMainActivityTest = new ActivityTestRule<>(MainActivity.class) ;


    @Before
    public void registerIdlingResource() {



    }





    @Test
    public void IngredientsTest(){


        CountingIdlingResource componentIdlingResource = mMainActivityTest.getActivity().getIdlingResourceInTest() ;

        Espresso.registerIdlingResources(componentIdlingResource);



        onView(withRecyclerView(R.id.mainActivity_RecyclerView)
                .atPositionOnView(1, R.id.ingredientListItem_IngredientName_TextView))
                .check(matches(withText("Brownies")));

        onView(withId(R.id.mainActivity_RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0 , click()))  ;

        onView(withRecyclerView(R.id.Ingredient_Fragment_RecyclerView)
                .atPositionOnView(0, R.id.ingredientListItem_IngredientName_TextView))
                .check(matches(withText("Graham Cracker crumbs")));


        onView(withRecyclerView(R.id.Ingredient_Fragment_StepRecyclerView)
                .atPositionOnView(2, R.id.step_recyclerview_stepDescription))
                .check(matches(withText("Prep the cookie crust.")));


        onView(withId(R.id.Ingredient_Fragment_StepRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0 , click())) ;

        //onView(withId(R.id.Step_Fragment_ExoPlayer2)).check(matches(isDisplayed())) ;
        onView(allOf(Matchers.<View>instanceOf(SimpleExoPlayerView.class), withId(R.id.Step_Fragment_ExoPlayer)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.Step_Fragment_textview)).check(matches(withText("Recipe Introduction"))) ;


        onView(withId(R.id.fragmentStep_nextButton)).perform(click()) ;


        onView(allOf(Matchers.<View>instanceOf(SimpleExoPlayerView.class), withId(R.id.Step_Fragment_ExoPlayer)))
                .check(matches(withEffectiveVisibility(Visibility.INVISIBLE)));



    }




    public void testNavigationView(){

    }

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }




}
