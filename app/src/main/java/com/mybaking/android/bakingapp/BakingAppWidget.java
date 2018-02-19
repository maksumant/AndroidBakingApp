package com.mybaking.android.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.mybaking.android.bakingapp.domain.Recipe;
import com.mybaking.android.bakingapp.service.BakingRecipesService;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {
    public static final String ACTION_GET_RECIPE = "com.mybaking.android.bakingapp.action.get_recipe";

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe currentRecipe) {

        CharSequence widgetText = context.getString(R.string.appwidget_text) + "Loaded recipe:" + currentRecipe.getName();
        System.out.println("Loaded recipe : "+ widgetText);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

//        Intent intent = new Intent(context, MainActivity.class);
        Intent intent = new Intent(context, BakingRecipesService.class);
        intent.setAction(ACTION_GET_RECIPE);
        if (currentRecipe != null) {
            intent.putExtra("currentRecipe", currentRecipe);
        }
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
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

