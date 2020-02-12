package com.foodmanager.entities;

import java.util.ArrayList;

/**
 *
 * @author roman
 */
public class Recipe {
    
    public static final int INGREDIENT_LIMIT = 20;
    private String name;
    private String preparation;
    private ArrayList<Ingredient> ingredients;
    private double totalPrice;

    public Recipe(String name, String preparation, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.preparation = preparation;
        this.ingredients = new ArrayList<>(ingredients);
        this.totalPrice = calculateTotalPrice();
    }
    
    private double calculateTotalPrice() {
        double total = 0;
        total = ingredients.stream()
                .map((ingredient) -> ingredient
                        .getPrice()).reduce(total, (accumulator, _item) -> accumulator + _item);
        return total;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }
    
    public Ingredient getIngredient(int i) {
        return ingredients.get(i);
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public int getNumIngredients() {
        return ingredients.size();
    }
    
    
    
    
}
