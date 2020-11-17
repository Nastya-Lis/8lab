package com.example.a8lab.databaseManager;

import android.provider.BaseColumns;

public final class RecipesDataBaseContract {

    public final static String DATABASE_NAME = "recipesDb.db";
    public final static int DATABASE_VERSION = 1;


    public RecipesDataBaseContract(){

    }

    public static abstract class DbContract implements BaseColumns {

        public final static String TABLE_NAME = "Recipes";
        public final static String COLUMN_ID = "id";
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_INGREDIENT = "ingredients";
        public final static String COLUMN_COOKING = "cooking_recipe";
        public final static String COLUMN_TIME = "time_for_cooking";
        public final static String COLUMN_CATEGORY = "category";
        public final static String COLUMN_PHOTO = "photography";
        public final static String COLUMN_FAVORITE = "isFavorite";

    }
}
