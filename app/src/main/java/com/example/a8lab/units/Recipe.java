package com.example.a8lab.units;

import java.io.Serializable;
import java.sql.Time;

public class Recipe implements Serializable {
    Integer id;
    String name;
    String ingredient;
    String photo;
    String cookingRecipe;
    String timeCooking;
    Category category;
    boolean favourite;

    static Integer idVal = 0;

    //что-то решить с конструктором, а конкретно со значением айдишника

    public Recipe(){
        //this.id = idVal++;
        this.category = Category.OTHERS;
        this.name = "Default";
        this.ingredient="Default";
        this.photo = null;
        this.timeCooking = null;
        this.favourite = false;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCookingRecipe() {
        return cookingRecipe;
    }

    public void setCookingRecipe(String cookingRecipe) {
        this.cookingRecipe = cookingRecipe;
    }
    public String getTimeCooking() {
        return timeCooking;
    }

    public void setTimeCooking(String timeCooking) {
        this.timeCooking = timeCooking;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
