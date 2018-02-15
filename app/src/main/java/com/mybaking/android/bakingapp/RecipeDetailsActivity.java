package com.mybaking.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mybaking.android.bakingapp.domain.Recipe;
import com.mybaking.android.bakingapp.ui.RecipeStepsFragment;
import com.mybaking.android.bakingapp.utils.StringConstants;

/**
 * Created by makrandsumant on 15/02/18.
 */

public class RecipeDetailsActivity extends AppCompatActivity {
    private RecipeStepsFragment recipeStepsFragment;
    private Recipe selectedRecipe = null;
    private final static String SELECTED_RECIPE_DATA_KEY = "selectedRecipe";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Intent intentThatStartedThisActivity = getIntent();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null &&
                savedInstanceState.containsKey(SELECTED_RECIPE_DATA_KEY)) {
            this.selectedRecipe = (Recipe) savedInstanceState.get(SELECTED_RECIPE_DATA_KEY);
        } else if(this.selectedRecipe == null && intentThatStartedThisActivity.hasExtra(StringConstants.EXTRA_CONTENT_NAME)) {
            this.selectedRecipe = (Recipe) intentThatStartedThisActivity.getParcelableExtra(StringConstants.EXTRA_CONTENT_NAME);
        }


        System.out.println("Selected recipe in details activity:" + selectedRecipe);
        if (this.recipeStepsFragment == null) {
            this.recipeStepsFragment = new RecipeStepsFragment();
            this.recipeStepsFragment.setCurrentRecipe(selectedRecipe);
        }
        if(!this.recipeStepsFragment.isAdded()) {
            fragmentManager.beginTransaction().add(R.id.steps_container, recipeStepsFragment).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SELECTED_RECIPE_DATA_KEY, this.selectedRecipe);
        super.onSaveInstanceState(outState);
    }
}
