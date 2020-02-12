package com.foodmanager.entities;

public class Ingredient {
    
    private String name;
    private String presentation;
    private String description;
    private double weight;
    private double price;

    public Ingredient(String name, String presentation, double weight, double price, String description) {
        this.name = name;
        this.presentation = presentation;
        this.weight = weight;
        this.price = price;
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }
    

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public void setAll(String name, String presentation, double weight, double price, String description) {
        setName(name);
        setPresentation(presentation);
        setWeight(weight);
        setPrice(price);
        setDescription(description);
    }

    @Override
    public String toString() {
        return "Food{" + "name=" + name + ", presentation=" + presentation + ", weight=" + weight + ", price=" + price + '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
