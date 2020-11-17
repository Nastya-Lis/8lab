package com.example.a8lab.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


public class RecipeListFragment extends Fragment {

    Context context;

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
       /* outState.putString(nameUser,userId);*/
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  databaseReference = FirebaseDatabase.getInstance().getReference(userId);*/
            try
            {
                recipeSQLiteDataBase = RecipeSQLiteDataBase.getInstance(context);
                recipesFromDb = new ArrayList<>();
                recipesFromDb = recipeSQLiteDataBase.takeAllRecipesFromDb();
                //new ArrayList<>();
               // createListFromDb();
            } catch (SQLDBException e) {
                e.printStackTrace();
            }

    }


    public void createListFromDb(){
 /*      databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(recipesFromDb.size() > 0 ) recipesFromDb.clear();
                for (DataSnapshot snap: snapshot.getChildren()) {
                    Recipe recipe  = snap.getValue(Recipe.class);
                    String str = snap.getKey();
                    forListManager.put(str,recipe);
                    recipesFromDb.add(recipe);
                }
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
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
    public void onStart() {
        super.onStart();
    }

    public RecipeSQLiteDataBase returnSQLiteDb(){
        return recipeSQLiteDataBase;
    }

    public void delDB(){
        //recipeSQLiteDataBaseSingleton.sqLiteDatabase
        context.deleteDatabase(RecipesDataBaseContract.DATABASE_NAME);
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