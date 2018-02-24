package com.mybaking.android.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mybaking.android.bakingapp.R;
import com.mybaking.android.bakingapp.domain.Ingredient;
import com.mybaking.android.bakingapp.domain.Recipe;
import com.mybaking.android.bakingapp.domain.RecipeStep;
import com.squareup.picasso.Picasso;

import java.net.URLDecoder;
import java.util.List;

/**
 * Created by makrandsumant on 15/02/18.
 */

public class RecipeStepsFragment extends Fragment {

    private static final String CURRENT_RECIPE_DATA_KEY = "currentRecipe";
    private Recipe currentRecipe;
    private ImageView mRecipeImage;
    private StepsFragementOnClickListener onClickHandler;

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

        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.ll_recipes_fragment);

        mRecipeImage = (ImageView) rootView.findViewById(R.id.recipe_image);


        if(savedInstanceState!=null) {
            if( savedInstanceState.containsKey(CURRENT_RECIPE_DATA_KEY)) {
                this.currentRecipe = (Recipe) savedInstanceState.get(CURRENT_RECIPE_DATA_KEY);
            }


        }
        ingredientsTextView.setText(Html.fromHtml(this.generateIngredientsString(currentRecipe.getIngredients())));

        if (currentRecipe.getSteps()!=null && !currentRecipe.getSteps().isEmpty()) {
            if (currentRecipe.getImageUrl() != null && !currentRecipe.getImageUrl().isEmpty()) {
                Picasso.with(this.getContext()).load(URLDecoder.decode(currentRecipe.getImageUrl())).into(mRecipeImage);
                mRecipeImage.setVisibility(View.VISIBLE);
            } else {
                mRecipeImage.setVisibility(View.GONE);
            }
            TextView tvStepDesc = null;
            for (final RecipeStep step : currentRecipe.getSteps()) {
                tvStepDesc = new TextView(this.getContext());
                tvStepDesc.setText(step.getDescription());
                tvStepDesc.setContentDescription("stepDesc");
                tvStepDesc.setBackgroundResource(R.drawable.rounded_corner);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
                layoutParams.setMargins(5,10,5,0);
                tvStepDesc.setLayoutParams(layoutParams);
                tvStepDesc.setPadding(15,15,15,15);
                tvStepDesc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tvStepDesc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onClickHandler != null) {
                            onClickHandler.onClick(step);
                        }
                    }
                });
                linearLayout.addView(tvStepDesc);
            }
        }
        return rootView;
    }

    private String generateIngredientsString(List<Ingredient> ingredients) {
        if (ingredients != null && !ingredients.isEmpty()) {
            StringBuilder ingredientsStr = new StringBuilder();
            ingredientsStr.append("<h2>").append(this.getString(R.string.ingredients)).append(":</h2>");
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

    public void setOnClickHandler(StepsFragementOnClickListener onClickHandler) {
        this.onClickHandler = onClickHandler;
    }

    public interface StepsFragementOnClickListener {
        public void onClick(RecipeStep step);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}