package com.mybaking.android.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.mybaking.android.bakingapp.domain.RecipeStep;
import com.mybaking.android.bakingapp.ui.StepDetailsFragment;

public class StepDetailsActivity extends AppCompatActivity {
    private RecipeStep mCurrentStep;
    private StepDetailsFragment mStepDetailsFragment;
    private static final String SELECTED_STEP_KEY = "selectedStep";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        if (savedInstanceState == null) {
            Intent intentThatStartedThisActivity = getIntent();
            if (intentThatStartedThisActivity.hasExtra("clickedStep")) {
                mCurrentStep = intentThatStartedThisActivity.getParcelableExtra("clickedStep");
            }
        } else {
            if (savedInstanceState.containsKey(SELECTED_STEP_KEY)) {
                mCurrentStep = (RecipeStep) savedInstanceState.get(SELECTED_STEP_KEY);
            }
            mStepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "stepDetailsFragement");
        }

            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        if(mStepDetailsFragment == null) {
            mStepDetailsFragment = new StepDetailsFragment();
            mStepDetailsFragment.setRetainInstance(true);
            mStepDetailsFragment.setCurrentStep(mCurrentStep);
        }
            if(!mStepDetailsFragment.isAdded()) {
                fragmentManager.beginTransaction().replace(R.id.fl_step_details_fragment, mStepDetailsFragment).commit();
            }
            this.setTitle(mCurrentStep.getShortDescription());


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SELECTED_STEP_KEY, this.mCurrentStep);
        getSupportFragmentManager().putFragment(outState, "stepDetailsFragement", mStepDetailsFragment);
        super.onSaveInstanceState(outState);
    }

}
