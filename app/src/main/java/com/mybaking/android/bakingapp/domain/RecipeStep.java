package com.mybaking.android.bakingapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by makrandsumant on 11/02/18.
 */

public class RecipeStep implements Parcelable {
    private long stepId;
    private long recipeId;
    private String description;
    private String shortDescription;
    private String videoURL;
    private String thumbnailURL;

    public RecipeStep(long recipeId) {
        this.recipeId = recipeId;
    }

    protected RecipeStep(Parcel in) {
        stepId = in.readLong();
        recipeId = in.readLong();
        description = in.readString();
        shortDescription = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<RecipeStep> CREATOR = new Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel in) {
            return new RecipeStep(in);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(stepId);
        parcel.writeLong(recipeId);
        parcel.writeString(description);
        parcel.writeString(shortDescription);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);

    }

    @Override
    public String toString() {
        return "RecipeStep{" +
                "stepId=" + stepId +
                ", recipeId=" + recipeId +
                ", description='" + description + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                '}';
    }
}
