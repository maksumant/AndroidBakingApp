package com.mybaking.android.bakingapp.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.mybaking.android.bakingapp.BakingAppWidget;
import com.mybaking.android.bakingapp.domain.Recipe;
import com.mybaking.android.bakingapp.utils.JsonUtils;
import com.mybaking.android.bakingapp.utils.NetworkUtils;
import com.mybaking.android.bakingapp.utils.StringConstants;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by makrandsumant on 19/02/18.
 */

public class BakingRecipesService extends IntentService {

    public static final String ACTION_GET_RECIPES = "com.mybaking.android.bakingapp.action.get_recipes";
    public static final String ACTION_GET_RECIPE = "com.mybaking.android.bakingapp.action.get_recipe";
    public BakingRecipesService() {
        super("BakingRecipesService");
    }
    private List<Recipe> mAllRecipes;
    private Recipe mCurrentRecipe;

    public static void startActionUpdateBakingWidgets(Context context) {
        Intent intent = new Intent(context, BakingRecipesService.class);
        intent.setAction(ACTION_GET_RECIPE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_RECIPE.equals(action)) {
                handleGetIndividualRecipeAction(intent);
            }
        }



    }

    private void handleGetIndividualRecipeAction(Intent intent) {
        if (intent.hasExtra(StringConstants.EXTRA_CURRENT_RECIPE)) {
            mCurrentRecipe = (Recipe) intent.getParcelableExtra(StringConstants.EXTRA_CURRENT_RECIPE);
        }
        if (mAllRecipes == null) {
            new LoadRecipeTask().execute(new Context[]{this});

            while (mAllRecipes == null) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if(mAllRecipes != null) {
            if(mCurrentRecipe == null) {
                mCurrentRecipe = mAllRecipes.get(0);
            } else {
                int currentRecipeIndex = mAllRecipes.indexOf(mCurrentRecipe);
                int nextIndex = currentRecipeIndex + 1;
                if (currentRecipeIndex == mAllRecipes.size() - 1) {
                    nextIndex = 0;
                }

                mCurrentRecipe = mAllRecipes.get(nextIndex);
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
            BakingAppWidget.updateBakingAppWidget(this, appWidgetManager, appWidgetIds, mCurrentRecipe);
        }
    }

    public class LoadRecipeTask extends AsyncTask<Context, Void, Recipe[]> {
        private Context context;

        @Override
        protected Recipe[] doInBackground(Context... contexts) {
            if (contexts != null && contexts.length != 0) {
                context = contexts[0];
            }
            return loadRecipes();
        }

        private Recipe[] loadRecipes() {
            URL recipesURL = NetworkUtils.buildUrl();

            try {
                String jsonRecipesResponce = NetworkUtils.getResponseFromHttpUrl(recipesURL);
                return JsonUtils.parseResponseJson(jsonRecipesResponce);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Recipe[0];
        }

        @Override
        protected void onPostExecute(Recipe[] recipes) {
            super.onPostExecute(recipes);
           mAllRecipes = Arrays.asList(recipes);
        }
    }
}
