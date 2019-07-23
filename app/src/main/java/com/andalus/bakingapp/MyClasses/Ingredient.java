package com.andalus.bakingapp.MyClasses;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Ingredient implements Parcelable {

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
    private static final String JSON_INGREDIENT_QUANTITY_KEY = "quantity";
    private static final String JSON_INGREDIENT_MEASURE_KEY = "measure";
    private static final String JSON_INGREDIENT_NAME_KEY = "ingredient";
    private double quantity;
    private String ingredient;
    private String measure;

    private Ingredient() {
    }

    protected Ingredient(Parcel in) {
        this.quantity = in.readDouble();
        this.ingredient = in.readString();
        this.measure = in.readString();
    }

    /**
     * This method is used to get the ingredients from the json array object
     * this object is getten in the Recipe parseFromJson Method
     * and return an array of ingredients
     *
     * @param arr
     * @return
     */
    public static ArrayList<Ingredient> parseIngredientsFromJson(JSONArray arr) {

        if (arr == null || arr.length() == 0) {

            return null;
        }
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            try {
                JSONObject ob = arr.getJSONObject(i);
                Ingredient ing = new Ingredient();
                ing.setIngredientName(ob.getString(JSON_INGREDIENT_NAME_KEY));
                ing.setMeasure(ob.getString(JSON_INGREDIENT_MEASURE_KEY));
                ing.setQuantity(ob.getDouble(JSON_INGREDIENT_QUANTITY_KEY));


                ingredients.add(ing);
            } catch (JSONException e) {
                e.printStackTrace();


            }

        }
        return ingredients;

    }

    public double getQuantity() {
        return quantity;
    }

    private void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getIngredientName() {
        return ingredient;
    }

    private void setIngredientName(String ingredientName) {
        ingredient = ingredientName;
    }

    public String getMeasure() {
        return measure;
    }

    private void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "quantity=" + quantity +
                ", IngredientName='" + ingredient + '\'' +
                ", measure='" + measure + '\'' +

                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.quantity);
        dest.writeString(this.ingredient);
        dest.writeString(this.measure);
    }
}
