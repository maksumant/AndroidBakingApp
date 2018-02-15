package com.mybaking.android.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mybaking.android.bakingapp.R;
import com.mybaking.android.bakingapp.domain.Recipe;

/**
 * Created by makrandsumant on 11/02/18.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>{

    private Recipe[] recipes;

    private RecipeListAdapterOnClickHandler recipeListAdapterOnClickHandler;

    public RecipeListAdapter(RecipeListAdapterOnClickHandler onClickHandler) {
        this.recipeListAdapterOnClickHandler = onClickHandler;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForRecipeListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForRecipeListItem, viewGroup, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bind(recipes[position].getName());
    }

    @Override
    public int getItemCount() {
        if(recipes ==  null) {
            return 0;
        }
        return recipes.length;
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeText;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeText = itemView.findViewById(R.id.tv_recipe_name);
            recipeText.setOnClickListener(this);
        }

        void bind( String recipeName) {
            recipeText.setText(recipeName);
        }
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            System.out.println(" Recipe clicked: " + recipes[adapterPosition].getName());
            recipeListAdapterOnClickHandler.onClick(recipes[adapterPosition]);

        }
    }

    public void setRecipes(Recipe[] recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface RecipeListAdapterOnClickHandler {
        void onClick(Recipe clickedMovie);
    }
}
