package com.mybaking.android.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mybaking.android.bakingapp.adapter.RecipeListAdapter;
import com.mybaking.android.bakingapp.domain.Recipe;
import com.mybaking.android.bakingapp.test.MainIdlingResource;
import com.mybaking.android.bakingapp.utils.StringConstants;
import com.mybaking.android.bakingapp.utils.JsonUtils;
import com.mybaking.android.bakingapp.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Recipe[]>, RecipeListAdapter.RecipeListAdapterOnClickHandler{

    @BindView(R.id.rv_recipe_cards)
    RecyclerView mRecyclerView;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mProgressBar;

    @BindView(R.id.tv_error_message)
    TextView mErrorMessageDisplay;

    private static final int RECIPES_DATA_LOADER = 23;

    private RecipeListAdapter mRecipeListAdapter;

    private static final String SAVED_LAYOUT_MANAGER = "savedLayoutManager";

    private Parcelable mLayoutManagerSavedState = null;

    @Nullable
    private MainIdlingResource mIdlingResource;

    /**
     * Only called from test
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new MainIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(false);

        mRecipeListAdapter = new RecipeListAdapter(this);
        mRecyclerView.setAdapter(mRecipeListAdapter);


        this.loadRecipeDetails();
    }

    private void loadRecipeDetails() {
        android.support.v4.app.LoaderManager manager = getSupportLoaderManager();
        Loader<Recipe[]> recipesDataLoader = manager.getLoader(RECIPES_DATA_LOADER);
        if(recipesDataLoader == null) {
            manager.initLoader(RECIPES_DATA_LOADER, new Bundle(), this);
        } else {
            manager.restartLoader(RECIPES_DATA_LOADER, new Bundle(), this);
        }

    }

    @Override
    public Loader<Recipe[]> onCreateLoader(int id, final Bundle bundle) {
        return new AsyncTaskLoader<Recipe[]>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(bundle == null) {
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public Recipe[] loadInBackground() {
                URL recipesURL = NetworkUtils.buildUrl();

                try {
                    String jsonRecipesResponce = NetworkUtils.getResponseFromHttpUrl(recipesURL);
                    return JsonUtils.parseResponseJson(jsonRecipesResponce);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new Recipe[0];
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<Recipe[]> loader, Recipe[] recipes) {
        mProgressBar.setVisibility(View.INVISIBLE);
        if (recipes == null || recipes.length == 0) {
            this.showErrorMessage();
        } else {
            this.showRecipesDataView();
            mRecipeListAdapter.setRecipes(recipes);
        }
    }

    /**
     * This method makes the view for the movies data visible and hides error message.
     */
    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    /**
     * This method makes the view for the movies data visible and hides error message.
     */
    private void showRecipesDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onLoaderReset(Loader<Recipe[]> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVED_LAYOUT_MANAGER, mRecyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(SAVED_LAYOUT_MANAGER)) {
            mLayoutManagerSavedState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void restoreLayoutManagerPosition() {
        if (mLayoutManagerSavedState != null) {
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mLayoutManagerSavedState);
        }
    }

    @Override
    public void onClick(Recipe clickedRecipe) {
            Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
            intent.putExtra(StringConstants.EXTRA_CONTENT_NAME, clickedRecipe);
            startActivity(intent);
    }
}
