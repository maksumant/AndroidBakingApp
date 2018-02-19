package com.mybaking.android.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.RemoteViews;

import com.mybaking.android.bakingapp.domain.Ingredient;
import com.mybaking.android.bakingapp.domain.Recipe;
import com.mybaking.android.bakingapp.service.BakingRecipesService;
import com.mybaking.android.bakingapp.utils.StringConstants;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {
    public static final String ACTION_GET_RECIPE = "com.mybaking.android.bakingapp.action.get_recipe";

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe currentRecipe) {

        CharSequence recipeName = currentRecipe.getName();
        System.out.println("Loaded recipe : "+ recipeName);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.recipe_name, recipeName);

        String ingredients = generateIngredientsString(currentRecipe.getIngredients(), context);
        views.setTextViewText(R.id.recipe_ingredients, Html.fromHtml(ingredients));

        Intent intent = new Intent(context, BakingRecipesService.class);
        intent.setAction(ACTION_GET_RECIPE);
        if (currentRecipe != null) {
            intent.putExtra(StringConstants.EXTRA_CURRENT_RECIPE, currentRecipe);
        }
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.iv_next_recipe, pendingIntent);

        Intent appIntent = new Intent(context, MainActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, 0);
        views.setOnClickPendingIntent(R.id.recipe_name, appPendingIntent);

        views.setViewVisibility(R.id.iv_next_recipe, View.VISIBLE);
        views.setViewVisibility(R.id.recipe_name, View.VISIBLE);
        views.setViewVisibility(R.id.laoding_text, View.GONE);
        views.setViewVisibility(R.id.iv_app_icon, View.GONE);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static String generateIngredientsString(List<Ingredient> ingredients, Context context) {
        if (ingredients != null && !ingredients.isEmpty()) {
            StringBuilder ingredientsStr = new StringBuilder();
            ingredientsStr.append("<b>").append(context.getString(R.string.ingredients)).append(":</b><br/><br/>");
            ingredientsStr.append("<small>");
            for (Ingredient ingredient : ingredients) {
                ingredientsStr.append("- ").append(ingredient.getName()).append(": ").append(" ").append(ingredient.getQuantity()).append(" ").append(ingredient.getMeasure()).append("<br/>");
            }
            ingredientsStr.append("</small>");
            return ingredientsStr.toString();
        }
        return "";
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        BakingRecipesService.startActionUpdateBakingWidgets(context);
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
    }

    public static void updateBakingAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int[] appWidgetIds, Recipe recipe) {
        for (int appWidgetId: appWidgetIds) {
            updateAppWidget(context,appWidgetManager, appWidgetId, recipe);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

