package com.foodmanager.utils;

import com.foodmanager.entities.Ingredient;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author roman
 */
public class IngredientSearcher {
    
    public static List<String> jComboBoxToList(JComboBox jcb) {
        List list = new ArrayList<>(); 
        for(int i = 0; i < jcb.getModel().getSize(); i++) {
            list.add(jcb.getModel().getElementAt(i));
        }
        return list;
    }
    
    public static List<Ingredient> getIngredientListByNames(List<String> IngredientNameList,List<Ingredient> ingredients) {
        List<Ingredient> list = new ArrayList<>();
        IngredientNameList.forEach((name) -> {
            ingredients.stream().filter((ingredient) -> (name.equals(ingredient.getName()))).forEachOrdered((ingredient) -> {
                list.add(ingredient);
            });
        });
        if(IngredientNameList.size() == list.size()) {
            return list;
        }
        return null;
    }
    
    public static boolean ingredientExist(String name, List<String> ingredients) {
        if (ingredients.stream().anyMatch((ingredient) -> (name.equals(ingredient)))) {
            return true;
        }
        return false;
    }
    
    
}
