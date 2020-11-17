package com.example.a8lab.databaseManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.a8lab.units.Category;
import com.example.a8lab.units.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeSQLiteDataBase {

    private static RecipesDataBaseHelper dataBaseHelper;
    private static RecipeSQLiteDataBase recipeSQLiteDataBaseSingleton;
    private SQLiteDatabase sqLiteDatabase;
    Context context;

    private RecipeSQLiteDataBase(){}

    public static RecipeSQLiteDataBase getInstance(Context context){
        if(recipeSQLiteDataBaseSingleton == null)
        {
            recipeSQLiteDataBaseSingleton = new RecipeSQLiteDataBase();
        }
        recipeSQLiteDataBaseSingleton.context = context;
        dataBaseHelper = new RecipesDataBaseHelper(context);
        recipeSQLiteDataBaseSingleton.sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        return recipeSQLiteDataBaseSingleton;
    }




    public void addRecipeToDb(Recipe recipe) throws SQLDBException{

            ContentValues contentValues = new ContentValues();
            contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_ID,recipe.getId());
            contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_NAME,recipe.getName());
            contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_INGREDIENT,recipe.getIngredient());
            contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_COOKING,recipe.getCookingRecipe());
            contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_TIME,recipe.getTimeCooking());
            contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_CATEGORY,recipe.getCategory().toString());
            contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_PHOTO,recipe.getPhoto());
            contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_FAVORITE,String.valueOf(recipe.isFavourite()));

            if(sqLiteDatabase.insert
                    (RecipesDataBaseContract.DbContract.TABLE_NAME,null,contentValues)
            == -1){
                throw new SQLDBException("not inserted");
            }
    }

    public void updateRecipeInDb(Recipe recipe){

        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_ID,recipe.getId());
        contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_NAME,recipe.getName());
        contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_INGREDIENT,recipe.getIngredient());
        contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_COOKING,recipe.getCookingRecipe());
        contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_TIME,recipe.getTimeCooking());
        contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_CATEGORY,recipe.getCategory().toString());
        contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_PHOTO,recipe.getPhoto());
        contentValues.put(RecipesDataBaseContract.DbContract.COLUMN_FAVORITE,recipe.isFavourite());

        sqLiteDatabase.update(RecipesDataBaseContract.DbContract.TABLE_NAME,contentValues,RecipesDataBaseContract.DbContract.COLUMN_ID + "= ? AND"
                + RecipesDataBaseContract.DbContract.COLUMN_NAME + " = ?",
                new String[] {recipe.getId().toString(), recipe.getName()});

    }

    public void deleteRecipeFromDb(Recipe recipe) throws SQLDBException{
        if(sqLiteDatabase.delete(RecipesDataBaseContract.DbContract.TABLE_NAME,
                RecipesDataBaseContract.DbContract.COLUMN_ID + "= ? AND" +
                         RecipesDataBaseContract.DbContract.COLUMN_NAME + "= ?",
                new String[] {recipe.getId().toString(),recipe.getName()}) == -1)
            throw new SQLDBException("not deleted");

    }

    public List<Recipe> takeAllRecipesFromDb() throws SQLDBException{
        List<Recipe> recipeList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(RecipesDataBaseContract.DbContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            do
            recipeList.add(takeRecipeFromCursor(cursor));
            while (cursor.moveToNext());
            cursor.close();
            return recipeList;
        }
        else
            throw  new SQLDBException("not readed");
    }

    private Recipe takeRecipeFromCursor(Cursor cursor){
        Recipe recipe = new Recipe();

        int indexId = cursor.getColumnIndexOrThrow(RecipesDataBaseContract.DbContract.COLUMN_ID);
        int indexName = cursor.getColumnIndexOrThrow(RecipesDataBaseContract.DbContract.COLUMN_NAME);
        int indexIngredient = cursor.getColumnIndexOrThrow(RecipesDataBaseContract.DbContract.COLUMN_INGREDIENT);
        int indexCooking = cursor.getColumnIndexOrThrow(RecipesDataBaseContract.DbContract.COLUMN_COOKING);
        int indexTime = cursor.getColumnIndexOrThrow(RecipesDataBaseContract.DbContract.COLUMN_TIME);
        int indexCategory = cursor.getColumnIndexOrThrow(RecipesDataBaseContract.DbContract.COLUMN_CATEGORY);
        int indexPhoto = cursor.getColumnIndexOrThrow(RecipesDataBaseContract.DbContract.COLUMN_PHOTO);
        int indexFavorite = cursor.getColumnIndexOrThrow(RecipesDataBaseContract.DbContract.COLUMN_FAVORITE);

        recipe.setId(cursor.getInt(indexId));
        recipe.setName(cursor.getString(indexName));
        recipe.setIngredient(cursor.getString(indexIngredient));
        recipe.setCookingRecipe(cursor.getString(indexCooking));
        recipe.setTimeCooking(cursor.getString(indexTime));
        recipe.setCategory(Category.convertStringIntoCategoryType(cursor.getString(indexCategory)));
        recipe.setPhoto(cursor.getString(indexPhoto));
        recipe.setFavourite(Boolean.getBoolean(cursor.getString(indexFavorite)));

        return recipe;
    }

    public void closeDataBase(){

        recipeSQLiteDataBaseSingleton.sqLiteDatabase.close();
    }

}
