package com.mybaking.android.bakingapp.utils;

import com.mybaking.android.bakingapp.domain.Ingredient;
import com.mybaking.android.bakingapp.domain.Recipe;
import com.mybaking.android.bakingapp.domain.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makrandsumant on 15/02/18.
 */

public class JsonUtils {

    /**
     * Utility method which parses response received from Recipes api and returns array of recipe objects.
     *
     * @param jsonResponse
     * @return
     */
    public static Recipe[] parseResponseJson(String jsonResponse) {

        try {
            JSONArray results = new JSONArray(jsonResponse);

            List<Recipe> recipeList = new ArrayList<>();
            Recipe recipe = null;
            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonRecipe = results.getJSONObject(i);
                recipe = new Recipe();
                recipe.setId(jsonRecipe.getLong("id"));
                recipe.setName(jsonRecipe.getString("name"));
                recipe.setServings(jsonRecipe.getInt("servings"));
                recipe.setImageUrl(jsonRecipe.getString("image"));

                JSONArray ingredients = jsonRecipe.getJSONArray("ingredients");
                List<Ingredient> ingredientList = new ArrayList<>();
                Ingredient ingredient = null;
                for (int j =0; j<ingredients.length(); j++) {
                   ingredient = new Ingredient(recipe.getId());
                   JSONObject jsonIngredient = ingredients.getJSONObject(j);
                   ingredient.setQuantity(jsonIngredient.getInt("quantity"));
                   ingredient.setMeasure(jsonIngredient.getString("measure"));
                   ingredient.setName(jsonIngredient.getString("ingredient"));
                   ingredientList.add(ingredient);
                }
                recipe.setIngredients(ingredientList);


                JSONArray steps = jsonRecipe.getJSONArray("steps");
                List<RecipeStep> stepsList = new ArrayList<>();
                RecipeStep step = null;
                for (int j =0; j<steps.length(); j++) {
                    step = new RecipeStep(recipe.getId());
                    JSONObject jsonStep = steps.getJSONObject(j);
                    step.setStepId(jsonStep.getLong("id"));
                    step.setDescription (jsonStep.getString("description"));
                    step.setShortDescription(jsonStep.getString("shortDescription"));
                    step.setVideoURL(jsonStep.getString("videoURL"));
                    step.setThumbnailURL(jsonStep.getString("thumbnailURL"));
                    stepsList.add(step);
                }
                recipe.setSteps(stepsList);

                recipeList.add(recipe);
            }
            return recipeList.toArray(new Recipe[recipeList.size()]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Recipe[0];
    }
}
