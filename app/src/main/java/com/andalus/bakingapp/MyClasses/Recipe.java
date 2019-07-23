package com.andalus.bakingapp.MyClasses;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Recipe implements Parcelable {
    private int id;
    private String name;
    private int servings;
    private String image;

    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;


    /**
     * These constants are used for parsing the recipe and it's components
     * from the json file
     */
    public static final String JSON_RECIPE_ID_KEY = "id";
    public static final String JSON_RECIPE_NANME_KEY = "name";
    public static final String JSON_RECIPE_SERVINING_KEY = "servings";
    public static final String JSON_RECIPE_IMAGE_KEY = "image";
    public static final String JSON_RECIPE_INGREDIENTS_ARRAY_KEY = "ingredients";
    public static final String JSON_RECIPE_STEPS_ARRAY_KEY = "steps";


    public static final String JSON_FILE_NAME = "baking.json";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImageUrl() {
        return image;
    }

    public void setImageUrl(String imageName) {
        this.image = imageName;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }


    public Recipe() {
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", servings=" + servings +
                ", imageUrl='" + image + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.servings);
        dest.writeString(this.image);


    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.servings = in.readInt();
        this.image = in.readString();


    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };


    //===========================================================================

    /**
     * This method is used to parse all the precipes from the json file
     * and also its ingredients and steps
     *
     * @return an array of precipes
     */
    public ArrayList<Recipe> parseRecipesFromJson(Context context) {

        ArrayList<Recipe> recipes = new ArrayList<>();
        try {

            String jsonStr = getJsonStringFromAssets(JSON_FILE_NAME, context);

            JSONArray arr = new JSONArray(jsonStr);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject ob = arr.getJSONObject(i);
                Recipe re = new Recipe();

                re.setId(ob.getInt(JSON_RECIPE_ID_KEY));
                re.setImageUrl(ob.getString(JSON_RECIPE_IMAGE_KEY));
                re.setName(ob.getString(JSON_RECIPE_NANME_KEY));
                re.setServings(ob.getInt(JSON_RECIPE_SERVINING_KEY));

                JSONArray ingArray = ob.getJSONArray(JSON_RECIPE_INGREDIENTS_ARRAY_KEY);
                re.setIngredients(Ingredient.parseIngredientsFromJson(ingArray));

                JSONArray stArray = ob.getJSONArray(JSON_RECIPE_STEPS_ARRAY_KEY);
                re.setSteps(Step.parseIngredientsFromJson(stArray));


                recipes.add(re);

            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();

            return null;
        }


        return recipes;

    }


    public static String getJsonStringFromAssets(String fileName, Context context) throws IOException {
        String json = null;
        InputStream in = context.getAssets().open(fileName);
        int size = in.available();
        byte[] buffer = new byte[size];
        in.read(buffer);
        in.close();
        json = new String(buffer);
        return json;
    }

}
