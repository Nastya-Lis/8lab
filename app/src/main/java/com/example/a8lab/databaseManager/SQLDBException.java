package com.example.a8lab.databaseManager;

import android.database.sqlite.SQLiteDatabase;

public class SQLDBException extends Exception {
    public SQLDBException(String message){
        super(message);
    }
}
