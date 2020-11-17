package com.example.a8lab.units;

public enum Category {
    MAIN_DISHES,
    OTHERS,
    SOUP,
    SALAD,
    DELLY,
    BLUE_PLATE;

    int count;

    public int getCount() {
        return count;
    }

    public void setCount() {
        count++;
    }

    public static Category convertStringIntoCategoryType(String categoryString){

        Category[] categoryValues = Category.values();
        Category categoryReturn = null;
        for (Category category: categoryValues) {
            if(category.toString().equals(categoryString)){
                categoryReturn = category;
                break;
            }
        }
        return categoryReturn;
    }

}
