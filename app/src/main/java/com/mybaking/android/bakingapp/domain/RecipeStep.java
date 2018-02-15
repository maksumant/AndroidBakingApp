package com.mybaking.android.bakingapp.domain;

/**
 * Created by makrandsumant on 11/02/18.
 */

public class RecipeStep {
    private long stepId;
    private long recipeId;
    private String description;
    private String shortDescription;
    private String videoURL;
    private String thumbnailURL;

    public RecipeStep(long recipeId) {
        this.recipeId = recipeId;
    }

    public long getStepId() {
        return stepId;
    }

    public RecipeStep setStepId(long stepId) {
        this.stepId = stepId;
        return this;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public RecipeStep setRecipeId(long recipeId) {
        this.recipeId = recipeId;
        return  this;
    }

    public String getDescription() {
        return description;
    }

    public RecipeStep setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public RecipeStep setVideoURL(String videoURL) {
        this.videoURL = videoURL;
        return this;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public RecipeStep setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
        return this;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public RecipeStep setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }
}
