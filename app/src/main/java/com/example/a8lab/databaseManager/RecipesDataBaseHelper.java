package com.example.a8lab.databaseManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipesDataBaseHelper extends SQLiteOpenHelper {


    private static final String queryCreation = "CREATE TABLE " +
            RecipesDataBaseContract.DbContract.TABLE_NAME + "(" +
            RecipesDataBaseContract.DbContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            RecipesDataBaseContract.DbContract.COLUMN_NAME + " TEXT" + "," +
            RecipesDataBaseContract.DbContract.COLUMN_INGREDIENT + " TEXT," +
            RecipesDataBaseContract.DbContract.COLUMN_COOKING + " TEXT," +
            RecipesDataBaseContract.DbContract.COLUMN_TIME + " TEXT," +
            RecipesDataBaseContract.DbContract.COLUMN_CATEGORY + " TEXT," +
            RecipesDataBaseContract.DbContract.COLUMN_PHOTO + " TEXT," +
            RecipesDataBaseContract.DbContract.COLUMN_FAVORITE + " TEXT"+")";

    private final static String queryDelete = "DROP TABLE IF EXISTS " +
            RecipesDataBaseContract.DbContract.TABLE_NAME;


    public RecipesDataBaseHelper(Context context){
        super(context,RecipesDataBaseContract.DATABASE_NAME,
                null,RecipesDataBaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(queryCreation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(queryDelete);
        onCreate(sqLiteDatabase);
    }
}
