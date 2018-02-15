package com.mybaking.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mybaking.android.bakingapp.R;
import com.mybaking.android.bakingapp.domain.Ingredient;
import com.mybaking.android.bakingapp.domain.Recipe;

import java.util.List;

/**
 * Created by makrandsumant on 15/02/18.
 */

public class RecipeStepsFragment extends Fragment {

    private static final String CURRENT_RECIPE_DATA_KEY = "currentRecipe";
    private Recipe currentRecipe;

    public RecipeStepsFragment() {

    }

    public void setCurrentRecipe(Recipe currentRecipe) {
        this.currentRecipe = currentRecipe;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        TextView ingredientsTextView = (TextView) rootView.findViewById(R.id.tv_ingredients);

        if(savedInstanceState!=null && savedInstanceState.containsKey(CURRENT_RECIPE_DATA_KEY)) {
            currentRecipe = (Recipe) savedInstanceState.get(CURRENT_RECIPE_DATA_KEY);
        }
        ingredientsTextView.setText(Html.fromHtml(this.generateIngredientsString(currentRecipe.getIngredients())));
        return rootView;
    }

    private String generateIngredientsString(List<Ingredient> ingredients) {
        if (ingredients != null && !ingredients.isEmpty()) {
            StringBuilder ingredientsStr = new StringBuilder();
            ingredientsStr.append("<h2>").append(this.getString(R.string.ingredients)).append("</h2><br/>");
            for (Ingredient ingredient : ingredients) {
                ingredientsStr.append("- ").append(ingredient.getName()).append(": ").append(" ").append(ingredient.getQuantity()).append(" ").append(ingredient.getMeasure()).append("<br/>");
            }

            return ingredientsStr.toString();
        }
        return "";
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_RECIPE_DATA_KEY, this.currentRecipe);
        super.onSaveInstanceState(outState);
    }
}