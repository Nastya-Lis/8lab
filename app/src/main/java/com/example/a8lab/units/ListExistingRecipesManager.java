package com.example.a8lab.units;



import java.util.List;
import java.util.Map;

public class ListExistingRecipesManager {

    private List<Recipe> recipeList;
    private Map<String,Recipe> map;

    /*DatabaseReference db;*/


    public ListExistingRecipesManager(List<Recipe> recipeList,Map<String,Recipe> map){
        this.recipeList = recipeList;
      /*  db = FirebaseDatabase.getInstance().getReference(userCurrentId);*/
        this.map = map;
    }


    public void removeElementV2(Recipe recipeSend){
    String currentKey = "";
        for (String key:map.keySet()) {
            if(recipeSend == map.get(key))
            currentKey = key;
        }
//        db.child(currentKey).removeValue();
    }
}
