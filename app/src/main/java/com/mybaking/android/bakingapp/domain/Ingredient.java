package com.mybaking.android.bakingapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by makrandsumant on 11/02/18.
 */

public class Ingredient implements Parcelable {
    private int quantity;
    private String measure;
    private String name;
    private long recipeId;

    public Ingredient(long recipeId) {
        this.recipeId = recipeId;
    }

    protected Ingredient(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        name = in.readString();
        recipeId = in.readLong();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(quantity);
        parcel.writeString(measure);
        parcel.writeString(name);
        parcel.writeLong(recipeId);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", name='" + name + '\'' +
                ", recipeId=" + recipeId +
                '}';
    }
}
