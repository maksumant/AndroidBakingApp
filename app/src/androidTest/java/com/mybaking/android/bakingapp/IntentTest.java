package com.mybaking.android.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mybaking.android.bakingapp.utils.StringConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by makrandsumant on 21/02/18.
 */
@RunWith(AndroidJUnit4.class)
public class IntentTest {

    private IdlingResource mIdlingResource;

    @Rule
    public IntentsTestRule<MainActivity> mIntentTestRule = new IntentsTestRule<MainActivity>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mIntentTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickRecipe_CallsRecipeDetailsActivityIntent() {
        onView(withText("Brownies")).perform(click());
        intended(hasComponent(RecipeDetailsActivity.class.getName()));
    }

    @Test
    public void clickRecipe_RecipeDetailsActivityIntentWithExtra() {
        onView(withText("Brownies")).perform(click());
        intended(hasExtraWithKey(StringConstants.EXTRA_CONTENT_NAME));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
