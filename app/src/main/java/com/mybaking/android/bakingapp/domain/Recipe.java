package com.mybaking.android.bakingapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makrandsumant on 11/02/18.
 */

public class Recipe implements Parcelable{

    private long id;
    private String name;
    private List<RecipeStep> steps;
    private List<Ingredient> ingredients;
    private int servings;
    private String imageUrl;

    public Recipe() {

    }
    protected Recipe(Parcel in) {
        id = in.readLong();
        name = in.readString();
        servings = in.readInt();
        imageUrl = in.readString();
        steps = new ArrayList<RecipeStep>();
        in.readList(steps, RecipeStep.class.getClassLoader());
        ingredients = new ArrayList<Ingredient>();
        in.readList(ingredients, Ingredient.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeInt(servings);
        parcel.writeString(imageUrl);
        parcel.writeList(steps);
        parcel.writeList(ingredients);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", steps=" + steps +
                ", ingredients=" + ingredients +
                ", servings=" + servings +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (id != recipe.id) return false;
        if (servings != recipe.servings) return false;
        return name != null ? name.equals(recipe.name) : recipe.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + servings;
        return result;
    }
}
