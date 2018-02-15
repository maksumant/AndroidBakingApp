package com.mybaking.android.bakingapp.domain;

import java.util.List;

/**
 * Created by makrandsumant on 11/02/18.
 */

public class Recipe {

    private long id;
    private String name;
    private List<RecipeStep> steps;
    private List<Ingredient> ingredients;
    private int servings;
    private String imageUrl;

    public long getId() {
        return id;
    }

    public Recipe setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Recipe setName(String name) {
        this.name = name;
        return this;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }

    public Recipe setSteps(List<RecipeStep> steps) {
        this.steps = steps;
        return this;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Recipe setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public int getServings() {
        return servings;
    }

    public Recipe setServings(int servings) {
        this.servings = servings;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Recipe setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
