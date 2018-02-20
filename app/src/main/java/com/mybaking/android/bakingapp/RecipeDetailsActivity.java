package com.mybaking.android.bakingapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;

import com.mybaking.android.bakingapp.domain.Recipe;
import com.mybaking.android.bakingapp.domain.RecipeStep;
import com.mybaking.android.bakingapp.ui.RecipeStepsFragment;
import com.mybaking.android.bakingapp.ui.StepDetailsFragment;
import com.mybaking.android.bakingapp.utils.StringConstants;

import java.util.ArrayList;

/**
 * Created by makrandsumant on 15/02/18.
 */

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeStepsFragment.StepsFragementOnClickListener {
    private RecipeStepsFragment recipeStepsFragment;
    private StepDetailsFragment mStepDetailsFragment;
    private Recipe selectedRecipe = null;
    private final static String SELECTED_RECIPE_DATA_KEY = "selectedRecipe";
    private boolean mTwoPane;
    private ScrollView mDetailsScrollView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Intent intentThatStartedThisActivity = getIntent();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        this.mDetailsScrollView = (ScrollView) findViewById(R.id.sv_recipe_details);

        if (findViewById(R.id.ll_recipe_details) != null) {
            mTwoPane  = true;
//            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


            // Recipe details fragement
            if (savedInstanceState != null &&
                    savedInstanceState.containsKey(SELECTED_RECIPE_DATA_KEY)) {
                this.selectedRecipe = (Recipe) savedInstanceState.get(SELECTED_RECIPE_DATA_KEY);
            } else if (this.selectedRecipe == null && intentThatStartedThisActivity.hasExtra(StringConstants.EXTRA_CONTENT_NAME)) {
                this.selectedRecipe = (Recipe) intentThatStartedThisActivity.getParcelableExtra(StringConstants.EXTRA_CONTENT_NAME);
            }

            if (this.recipeStepsFragment == null) {
                this.recipeStepsFragment = new RecipeStepsFragment();
                this.recipeStepsFragment.setCurrentRecipe(selectedRecipe);
                this.recipeStepsFragment.setOnClickHandler(this);
                this.recipeStepsFragment.setRetainInstance(true);
            }
            if (!this.recipeStepsFragment.isAdded()) {
                fragmentManager.beginTransaction().add(R.id.steps_container, recipeStepsFragment).commit();
            }

            // Step details fragment
            if (mStepDetailsFragment == null) {
                mStepDetailsFragment = new StepDetailsFragment();
                mStepDetailsFragment.setShowFullScreenVideo(false);
            }
            if (!mStepDetailsFragment.isAdded()) {
                fragmentManager.beginTransaction().replace(R.id.fl_step_details_fragment, mStepDetailsFragment).commit();
            }


        } else {
            mTwoPane = false;
            if (savedInstanceState != null &&
                    savedInstanceState.containsKey(SELECTED_RECIPE_DATA_KEY)) {
                this.selectedRecipe = (Recipe) savedInstanceState.get(SELECTED_RECIPE_DATA_KEY);
                recipeStepsFragment = (RecipeStepsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "recipeStepsFragment");

            } else if (this.selectedRecipe == null && intentThatStartedThisActivity.hasExtra(StringConstants.EXTRA_CONTENT_NAME)) {
                this.selectedRecipe = (Recipe) intentThatStartedThisActivity.getParcelableExtra(StringConstants.EXTRA_CONTENT_NAME);
            }

            if (this.recipeStepsFragment == null) {
                this.recipeStepsFragment = new RecipeStepsFragment();
                this.recipeStepsFragment.setCurrentRecipe(selectedRecipe);
                this.recipeStepsFragment.setOnClickHandler(this);
                this.recipeStepsFragment.setRetainInstance(true);
            }
            if (!this.recipeStepsFragment.isAdded()) {
                fragmentManager.beginTransaction().replace(R.id.steps_container, recipeStepsFragment).commit();
            }
            if(savedInstanceState != null) {
                if (savedInstanceState.containsKey("SCROLL_POSITION")) {
                    final int[] scrollViewPosition = savedInstanceState.getIntArray("SCROLL_POSITION");
                    if (scrollViewPosition != null)
                        mDetailsScrollView.post(new Runnable() {
                            public void run() {
                                mDetailsScrollView.scrollTo(scrollViewPosition[0], scrollViewPosition[1]);
                            }
                        });
                }
            }
        }
        this.setTitle(this.selectedRecipe.getName());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SELECTED_RECIPE_DATA_KEY, this.selectedRecipe);
        getSupportFragmentManager().putFragment(outState, "recipeStepsFragment", recipeStepsFragment);
        outState.putIntArray("SCROLL_POSITION",
                new int[]{ mDetailsScrollView.getScrollX(), mDetailsScrollView.getScrollY()});
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(RecipeStep step) {
        if (!this.mTwoPane) {
            final Intent intent = new Intent(this, StepDetailsActivity.class);
            int index = selectedRecipe.getSteps().indexOf(step);
            intent.putExtra(StringConstants.EXTRA_CURRENT_STEP_INDEX, index);
            intent.putParcelableArrayListExtra(StringConstants.EXTRA_RECIPE_ALL_STEPS, (ArrayList<RecipeStep>) selectedRecipe.getSteps());
            startActivity(intent);
        } else {
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setCurrentStep(step);
            stepDetailsFragment.setShowFullScreenVideo(false);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_step_details_fragment, stepDetailsFragment).commit();
        }
    }
}
