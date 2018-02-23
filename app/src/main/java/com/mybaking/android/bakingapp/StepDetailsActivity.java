package com.mybaking.android.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.mybaking.android.bakingapp.domain.RecipeStep;
import com.mybaking.android.bakingapp.ui.StepDetailsFragment;
import com.mybaking.android.bakingapp.utils.StringConstants;

import java.util.ArrayList;
import java.util.List;

public class StepDetailsActivity extends AppCompatActivity {
    private RecipeStep mCurrentStep;
    private StepDetailsFragment mStepDetailsFragment;
    private ArrayList<RecipeStep> mAllSteps;
    private int mCurrentStepIndex;
    private static final String SELECTED_STEP_KEY = "selectedStep";
    private static final String ALL_STEPS_KEY = "allSteps";
    private static final String SELECTED_STEP_INDEX_KEY = "selectedStepIndex";
    private final String TAG_STEPS_FRAGMENT = "tagStepFragment";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        boolean isLandscapeMode = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;


        if (savedInstanceState == null) {
            Intent intentThatStartedThisActivity = getIntent();
            if (intentThatStartedThisActivity.hasExtra(StringConstants.EXTRA_RECIPE_ALL_STEPS) &&
                    intentThatStartedThisActivity.hasExtra(StringConstants.EXTRA_CURRENT_STEP_INDEX)) {
                mCurrentStepIndex = intentThatStartedThisActivity.getIntExtra(StringConstants.EXTRA_CURRENT_STEP_INDEX, 0);
                mAllSteps = intentThatStartedThisActivity.getParcelableArrayListExtra(StringConstants.EXTRA_RECIPE_ALL_STEPS);
                mCurrentStep = mAllSteps.get(mCurrentStepIndex);
            }
        } else {
            if (savedInstanceState.containsKey(SELECTED_STEP_KEY)) {
                mCurrentStep = (RecipeStep) savedInstanceState.get(SELECTED_STEP_KEY);
            }
            if (savedInstanceState.containsKey(SELECTED_STEP_INDEX_KEY)) {
                mCurrentStepIndex = savedInstanceState.getInt(SELECTED_STEP_INDEX_KEY);
            }
            if (savedInstanceState.containsKey(ALL_STEPS_KEY)) {
                mAllSteps = savedInstanceState.getParcelableArrayList(ALL_STEPS_KEY);
            }
//            mStepDetailsFragment = (StepDetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "stepDetailsFragement");
        }

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        mStepDetailsFragment = (StepDetailsFragment) fragmentManager.findFragmentByTag(TAG_STEPS_FRAGMENT);
        if (mStepDetailsFragment == null) {
            mStepDetailsFragment = new StepDetailsFragment();
            mStepDetailsFragment.setRetainInstance(true);
            mStepDetailsFragment.setCurrentStep(mCurrentStep);
        }
        mStepDetailsFragment.setShowFullScreenVideo(isLandscapeMode);
        if (!mStepDetailsFragment.isAdded()) {
            fragmentManager.beginTransaction().add(R.id.fl_step_details_fragment, mStepDetailsFragment,TAG_STEPS_FRAGMENT).commit();
        }
        this.setTitle(mCurrentStep.getShortDescription());


        // Set next step button
        final Intent nextStepIntent = createIntentForNextStep();
        Button nextButton = (Button) findViewById(R.id.next_button);
        if (nextStepIntent == null) {
            nextButton.setEnabled(false);
        } else {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(nextStepIntent);
                    finish();
                }
            });
        }

        // Set prev step button
        final Intent prevStepIntent = createIntentForPrevStep();
        Button prevButton = (Button) findViewById(R.id.prev_button);
        if (prevStepIntent == null) {
            prevButton.setEnabled(false);
        } else {
            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(prevStepIntent);
                    finish();
                }
            });
        }

    }

    private Intent createIntentForNextStep() {
        int nextIndex =  mCurrentStepIndex + 1;
        if(nextIndex == mAllSteps.size()) {
            return null;
        }
        Intent nextStepIntent = new Intent(this, StepDetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelableArrayList(StringConstants.EXTRA_RECIPE_ALL_STEPS, mAllSteps);
        extras.putInt(StringConstants.EXTRA_CURRENT_STEP_INDEX, nextIndex);
        nextStepIntent.putExtras(extras);
        return nextStepIntent;
    }

    private Intent createIntentForPrevStep() {
        int prevIndex =  mCurrentStepIndex - 1;
        if(prevIndex < 0) {
            return null;
        }
        Intent prevStepIntent = new Intent(this, StepDetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelableArrayList(StringConstants.EXTRA_RECIPE_ALL_STEPS, mAllSteps);
        extras.putInt(StringConstants.EXTRA_CURRENT_STEP_INDEX, prevIndex);
        prevStepIntent.putExtras(extras);
        return prevStepIntent;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SELECTED_STEP_KEY, this.mCurrentStep);
        outState.putInt(SELECTED_STEP_INDEX_KEY, this.mCurrentStepIndex);
        outState.putParcelableArrayList(ALL_STEPS_KEY, this.mAllSteps);
//        getSupportFragmentManager().putFragment(outState, "stepDetailsFragement", mStepDetailsFragment);
        super.onSaveInstanceState(outState);
    }

}
