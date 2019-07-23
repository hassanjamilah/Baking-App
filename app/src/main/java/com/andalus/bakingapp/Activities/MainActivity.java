package com.andalus.bakingapp.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.andalus.bakingapp.ListAdapters.RecipeAdapter;
import com.andalus.bakingapp.MyClasses.Recipe;
import com.andalus.bakingapp.R;

import com.andalus.bakingapp.api.RecipeApiService;
import com.andalus.bakingapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements   RecipeAdapter.RecipeClickHanlder {



    CountingIdlingResource mIdlingResource = new CountingIdlingResource("hassan001")  ;
    public static final String RECIPE_DETAILS_INGREDIENTS_BUNDLE_KEY = "h1";
    public static final String RECIPE_DETAILS_STEPS_BUNDLE_KEY = "h2";

    public static final String SAVE_INSTANCE_STATE_BUNDLE_RECIPES = "saveBundle";
    public static Recipe selectedRecipe;

    ActivityMainBinding binding;
    ArrayList<Recipe> mRecipes = new ArrayList<>();



    public static ProgressBar indicator ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);







        indicator = binding.getRoot().findViewById(R.id.MainActivity_Progressbar) ;


        if (mIdlingResource !=null){

            mIdlingResource.increment();
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(SAVE_INSTANCE_STATE_BUNDLE_RECIPES)) {
            mRecipes = savedInstanceState.getParcelableArrayList(SAVE_INSTANCE_STATE_BUNDLE_RECIPES);

            fillRecyclerViewWithData();
        } else {
            //mRecipes = r.parseRecipesFromJson(this);


            loadRecipesFromServer();
        }










    }


    public  void loadRecipesFromServer(){



        showIndicator(true);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RecipeApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build() ;
        RecipeApiService api = retrofit.create(RecipeApiService.class) ;
        Call<List<Recipe>> recipes  = api.getRecipes() ;

        recipes.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                List<Recipe> recipes2 = response.body() ;
                mRecipes = new ArrayList<>(recipes2) ;
                for (Recipe r:mRecipes){

                }
                fillRecyclerViewWithData();

                showIndicator(false);


            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                String msg = getResources().getString(R.string.error_msg );
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();

                mRecipes = null ;
                showIndicator(false);
            }
        });



    }

    public CountingIdlingResource getIdlingResourceInTest() {
        return mIdlingResource;
    }


    public void fillRecyclerViewWithData(){


        Configuration config = getResources().getConfiguration();
        int x = config.smallestScreenWidthDp;



        LinearLayoutManager manager;
        if (x >= 600 || isLandScape()) {
            manager = new GridLayoutManager(this, 2);
        } else {
            manager = new LinearLayoutManager(this);

        }
        RecipeAdapter adapter = new RecipeAdapter(this);
        adapter.setData(mRecipes);

        binding.mainActivityRecyclerView.setLayoutManager(manager);
        binding.mainActivityRecyclerView.setAdapter(adapter);
        binding.mainActivityRecyclerView.setHasFixedSize(true);
        if (mIdlingResource !=null){

        mIdlingResource.decrement();
        }

    }


    public boolean isLandScape() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) return true;
        return false;
    }





    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(SAVE_INSTANCE_STATE_BUNDLE_RECIPES, mRecipes);
    }

    @Override
    public void onClick(Recipe r) {
        selectedRecipe = r;

        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        Bundle bundle = new Bundle();

        //bundle.putInt(RECIPE_DETAILS_BUNDLE_KEY , r.getId());
        bundle.putParcelableArrayList(RECIPE_DETAILS_INGREDIENTS_BUNDLE_KEY, r.getIngredients());
        bundle.putParcelableArrayList(RECIPE_DETAILS_STEPS_BUNDLE_KEY, r.getSteps());


        intent.putExtras(bundle);



        startActivity(intent);

    }





    public static  void showIndicator(boolean b) {
        if (b){
            indicator.setVisibility(View.VISIBLE);
        }else {
            indicator.setVisibility(View.INVISIBLE);
        }
    }






}
