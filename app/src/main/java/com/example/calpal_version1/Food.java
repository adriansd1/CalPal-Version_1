package com.example.calpal_version1;

import java.util.Objects;

public class Food {
    protected int fat;
    protected int carbs;
    protected int protein;
    protected int calories;
    protected String name;
    public Food(){
        this.fat = 0;
        this.carbs = 0;
        this.protein = 0;
        this.calories = 0;
        this.name = new String();
    }
    public Food(int fat, int carbs, int protein, int calories, String name){
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
        this.name = new String(name);
    }
    public int getFat(){
        return fat;
    }
    public int getCarbs(){
        return carbs;
    }
    public int getProtein(){
        return protein;
    }
    public String getName() {
        return new String(name);
    }

    public int getCalories() {
        return calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }
    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }
    public void setFat(int fat) {
        this.fat = fat;
    }
    public void setName(String name) {
        this.name = new String(name);
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return fat == food.fat && carbs == food.carbs && protein == food.protein && calories == food.calories && Objects.equals(name, food.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(fat, carbs, protein, calories, name);
    }

    @Override
    public String toString() {
        return "food{" +
                "fat=" + fat +
                ", carbs=" + carbs +
                ", protein=" + protein +
                ", calories=" + calories +
                ", name='" + name + '\'' +
                '}';
    }
}
