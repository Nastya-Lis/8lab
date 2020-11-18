package com.example.a8lab.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.a8lab.R;
import com.example.a8lab.databaseManager.RecipeSQLiteDataBase;
import com.example.a8lab.databaseManager.RecipesDataBaseContract;
import com.example.a8lab.databaseManager.SQLDBException;
import com.example.a8lab.units.Category;
import com.example.a8lab.units.Recipe;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity {

    String photoDefault = "content://media/external/images/media/66";
    Recipe currentRecipe = new Recipe();

    private final int Pick_image = 1;

    EditText name,ingredient,cookingRecipe;
    ImageView image;
    CheckBox checkBox;
    TimePicker timePicker;
    Uri photoUri;


  /*  DatabaseReference dbRef;*/
    final String nameUser = "User_ID";
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipe);

        name = (EditText) findViewById(R.id.nameRecipeId);
        ingredient = (EditText) findViewById(R.id.ingredientId);
        cookingRecipe = (EditText) findViewById(R.id.cookingRecipeId);
        image = (ImageView) findViewById(R.id.photoRecipeId);
        timePicker = (TimePicker) findViewById(R.id.timerPickerId);
        checkBox = (CheckBox) findViewById(R.id.checkBoxFavId);

        //checkData();


        Button button = (Button) findViewById(R.id.setPictureRecipe);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Pick_image);
            }
        });


       resForCategoryView();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case Pick_image:
                if (resultCode == RESULT_OK) {
                    try {

                        final Uri imageUri = imageReturnedIntent.getData();
                        photoUri = imageUri;
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        image.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    private void createRecipe(){
       // currentRecipe.setId();
        currentRecipe.setName(name.getText().toString());
        currentRecipe.setIngredient(ingredient.getText().toString());
        currentRecipe.setCookingRecipe(cookingRecipe.getText().toString());
        currentRecipe.setTimeCooking(String.valueOf(timePicker.getHour()) + ":" +
                String.valueOf(timePicker.getMinute()));
        //currentRecipe.setPhoto(photoUri.toString());
        currentRecipe.setPhoto(photoDefault);
        currentRecipe.setFavourite(checkBox.isChecked());
    }

    private void checkData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            userId = bundle.get(nameUser).toString();

            }

    }

    private void resForCategoryView() {
        ArrayList<String> categories = new ArrayList<>();
        for (Category category : Category.values()) {
            categories.add(category.toString());
        }

        if(categories.size() != 0 ){
            Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
           ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                   android.R.layout.simple_spinner_item,categories);
           categorySpinner.setAdapter(adapter);


            AdapterView.OnItemSelectedListener onItemSelectedListener =
                    new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String selectedItem = adapterView.getSelectedItem().toString();
                    Category category = Category.valueOf(selectedItem);
                    currentRecipe.setCategory(category);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    currentRecipe.setCategory(Category.DELLY);
                }

            };

            categorySpinner.setOnItemSelectedListener(onItemSelectedListener);
        }
    }

    public void addNewRecipe(View view) {
        createRecipe();
        RecipeSQLiteDataBase recipeSQLiteDataBase = RecipeSQLiteDataBase.getInstance(this);
        try {
            recipeSQLiteDataBase =  RecipeSQLiteDataBase.getInstance(this);
            recipeSQLiteDataBase.addRecipeToDb(currentRecipe);
            Toast.makeText(this, "added:", Toast.LENGTH_LONG).show();
        } catch (SQLDBException e) {
            e.printStackTrace();
        }

    }

}

