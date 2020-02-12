package com.foodmanager.utils;

/**
 *
 * @author roman
 */
public class InputValidator {
    
    public static boolean isTextValid(String text) {
        return !text.isEmpty() && !text.startsWith(" ");
    }
    
    public static boolean isNumberValid(String textNumber) {
        Double number = 0d;
        if(textNumber.isEmpty() || textNumber.startsWith(" "))
            return false;
        try {
            number = Double.valueOf(textNumber);
        } catch (NumberFormatException e) {
            return false;
        } 
        if(number >= 0)
            return true;
        return false;
    }
    
    public static boolean isPointerValid(int i) {
        return i > -1;
    }
    
}
