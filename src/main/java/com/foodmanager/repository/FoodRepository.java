/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.foodmanager.repository;

import com.foodmanager.entities.Ingredient;
import com.foodmanager.entities.Recipe;
import com.foodmanager.interfaces.IFoodContainer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roman
 */
public class FoodRepository implements IFoodContainer{
    
    private static final List<Ingredient> ingredientList = new ArrayList<>();
    private static final List<Recipe> recipeList = new ArrayList<>();
    
    @Override
    public List<Ingredient> getIngredientList() {
        return FoodRepository.ingredientList;
    }
    @Override
    public List<Recipe> getRecipeList() {
        return FoodRepository.recipeList;
    }
    @Override
    public void addIngredient(Ingredient ingredient) {
        FoodRepository.ingredientList.add(ingredient);
    }
    @Override
    public boolean removeIngredient(String name) {
        for (Ingredient ingredient : FoodRepository.ingredientList) {
            if(ingredient.getName().equals(name)) {
                FoodRepository.ingredientList.remove(ingredient);
                return true;
            }
        }
        return false;
    }
    @Override
    public void addRecipe(Recipe recipe) {
        FoodRepository.recipeList.add(recipe);
    }
    @Override
    public void overwriteRecipe(Recipe recipe, int i) {
        FoodRepository.recipeList.add(i, recipe);
        FoodRepository.recipeList.remove(i+1);
    }
    @Override
    public boolean removeRecipe(String name) {
        for (Recipe recipe : FoodRepository.recipeList) {
            if(recipe.getName().equals(name)) {
                FoodRepository.ingredientList.remove(recipe);
                return true;
            }
        }
        return false;
    }
}
