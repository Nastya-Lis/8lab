package com.example.a8lab.fragments;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.a8lab.R;
import com.example.a8lab.activities.MainActivity;
import com.example.a8lab.activities.ShowCurrentRecipeActivity;
import com.example.a8lab.activities.UpdateRecipeActivity;
import com.example.a8lab.databaseManager.RecipeSQLiteDataBase;
import com.example.a8lab.databaseManager.RecipesDataBaseContract;
import com.example.a8lab.databaseManager.SQLDBException;
import com.example.a8lab.recyclerViewPack.RecipeAdapter;
import com.example.a8lab.units.ListExistingRecipesManager;
import com.example.a8lab.units.Recipe;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


public class RecipeListFragment extends Fragment {

    Context context;
    String stringInstanceContext;

    View ListFragmentView;
    public RecyclerView recyclerView;
    public RecipeAdapter recipeAdapter;
   /* DatabaseReference databaseReference;*/

    public List<Recipe> recipesFromDb;
//    final String nameUser = "User_ID";
    public Map<String,Recipe> forListManager = new HashMap<String, Recipe>() ;

    RecipeSQLiteDataBase recipeSQLiteDataBase;


    RecipeAdapter.OnRecipeClickListener onRecipeClickListener;
    RecipeAdapter.OnRecipeLongClickListener onRecipeLongClickListener;

    public void setOnRecipeFragmentClickListener(RecipeAdapter.OnRecipeClickListener
                                                         onRecipeClickListener){
        this.onRecipeClickListener = onRecipeClickListener;
    }

    public void setOnRecipeFragmentLongClickListener(RecipeAdapter.OnRecipeLongClickListener
                                                     onRecipeLongClickListener){
        this.onRecipeLongClickListener = onRecipeLongClickListener;
    }

    public RecipeListFragment() {
        // Required empty public constructor
    }

    public void updateFragmentData(){
        if(onRecipeClickListener!=null)
        recipeAdapter.setOnRecipeClickListener(onRecipeClickListener);
        if(onRecipeLongClickListener!=null)
        recipeAdapter.setOnRecipeLongClickListener(onRecipeLongClickListener);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //outState.putString();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            try
            {
                /*recipeSQLiteDataBaseSingleton.sqLiteDatabase = context.openOrCreateDatabase(RecipesDataBaseContract.DATABASE_NAME,
                        Context.MODE_PRIVATE,null );*/
                recipeSQLiteDataBase = RecipeSQLiteDataBase.getInstance(getActivity());
                recipesFromDb = new ArrayList<>();
                recipesFromDb = recipeSQLiteDataBase.takeAllRecipesFromDb();

               // createListFromDb();
            } catch (SQLDBException e) {
                e.printStackTrace();
            }

    }

    @Override
    public void onPause() {
        //recipeSQLiteDataBase.openOrCreate(context);
        super.onPause();
    }

    public void createListFromDb(){

        try {
            recipesFromDb = recipeSQLiteDataBase.takeAllRecipesFromDb();
            //recipeAdapter.notifyDataSetChanged();
            recipeAdapter = new RecipeAdapter(recipesFromDb);
            recyclerView.setAdapter(recipeAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        } catch (SQLDBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    public RecipeSQLiteDataBase returnSQLiteDb(){
        return recipeSQLiteDataBase;
    }

    public void delDB(){
        //recipeSQLiteDataBaseSingleton.sqLiteDatabase
        context.deleteDatabase(RecipesDataBaseContract.DATABASE_NAME);
    }

    public void closeDB(){
        recipeSQLiteDataBase.closeDataBase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ListFragmentView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        recyclerView = ListFragmentView.findViewById(R.id.myFragmentRecycler);
        recipeAdapter = new RecipeAdapter(recipesFromDb);
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return ListFragmentView;
    }

    public void takeContext(Context context){
        this.context = context;
    }

}