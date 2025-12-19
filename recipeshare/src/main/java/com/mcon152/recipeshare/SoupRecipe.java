package com.mcon152.recipeshare;

public class SoupRecipe extends Recipe {
    private String spiceLevel;


    public SoupRecipe(Long id, String title, String description, String ingredients, String instructions, String spiceLevel) {
        super(id, title, description, ingredients, instructions);
        this.spiceLevel = spiceLevel;
    }

    public String getSpiceLevel() {
        return spiceLevel;
    }

    public void setSpiceLevel(String spiceLevel) {
        this.spiceLevel = spiceLevel;
    }
}
