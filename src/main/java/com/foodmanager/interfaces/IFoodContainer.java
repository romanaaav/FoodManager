/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foodmanager.interfaces;

import com.foodmanager.entities.Ingredient;
import com.foodmanager.entities.Recipe;
import java.util.List;

/**
 *
 * @author roman
 */
public interface IFoodContainer {
    
    public abstract List<Ingredient> getIngredientList();
    
    public abstract List<Recipe> getRecipeList();
    
    public abstract void addIngredient(Ingredient ingredient);
    
    public abstract boolean removeIngredient(String name);
    
    public abstract void addRecipe(Recipe recipe);
    
    public abstract void overwriteRecipe(Recipe recipe, int i);
    
    public abstract boolean removeRecipe(String name);
    
}
