package com.mybaking.android.bakingapp.domain;

/**
 * Created by makrandsumant on 11/02/18.
 */

public class Ingredient {
    private int quantity;
    private String measure;
    private String name;
    private long recipeId;

    public Ingredient(long recipeId) {
        this.recipeId = recipeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Ingredient setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getMeasure() {
        return measure;
    }

    public Ingredient setMeasure(String measure) {
        this.measure = measure;
        return this;
    }

    public String getName() {
        return name;
    }

    public Ingredient setName(String name) {
        this.name = name;
        return this;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public Ingredient setRecipeId(long recipeId) {
        this.recipeId = recipeId;
        return this;
    }
}
