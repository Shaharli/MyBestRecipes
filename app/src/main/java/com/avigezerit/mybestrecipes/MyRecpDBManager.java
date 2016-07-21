package com.avigezerit.mybestrecipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by Shaharli on 19/07/2016.
 */
public class MyRecpDBManager {

    //bind to database helper
    MyRecpDBHelper db;
    Context context;
    ContentValues cv;

    //constants name of table
    public static final String MY_RECIPES = "myRecipes";

    //columns names
    public static final String COL_ID_0 = "_id";
    public static final String COL_NAME_1 = "name";
    public static final String COL_DESC_2 = "description";
    public static final String COL_URL_3 = "url";
    public static final String COL_TYPE_4 = "category";

    private static MyRecpDBManager dbm = new MyRecpDBManager();

    public static MyRecpDBManager getInstance() {
        return dbm;
    }

    private MyRecpDBManager() {
    }

    //set context
    public void setContext(Context c) {
        this.context = c;
    }

    //get all data as cursor
    public Cursor getAllDataAsCursor() {

        //initialize database helper
        db = new MyRecpDBHelper(context);

        Cursor c = db.getReadableDatabase().query(MY_RECIPES, null, null, null, null, null, null, null);
        return c;

    }

    //get specific data as cursor by given _id
    public Cursor getDataAsCursorByID(int id) {

        //getting query by the _id of recipe
        String[] whereArgs = new String[]{""+id};

        Cursor c = db.getReadableDatabase().query(MY_RECIPES, null, COL_ID_0+"=?", whereArgs, null, null, null);
        return c;
    }

    public void addNewRecipe(Recipe r){

        cv = new ContentValues();
        cv.put(COL_NAME_1, r.getName());
        cv.put(COL_DESC_2, r.getDesc());
        cv.put(COL_URL_3, r.getUri());
        cv.put(COL_TYPE_4, r.getCategory());

        db.getWritableDatabase().insert(MY_RECIPES, null, cv);

    }

    public void updateRecipe(Recipe r){

        cv = new ContentValues();
        cv.put(COL_NAME_1, r.getName());
        cv.put(COL_DESC_2, r.getDesc());
        cv.put(COL_URL_3, r.getUri());
        cv.put(COL_TYPE_4, r.getCategory());

        //getting query by the _id of recipe
        String[] whereArgs = new String[]{""+r.getSql_id()};

        db.getWritableDatabase().update(MY_RECIPES, cv, COL_ID_0+"=?", whereArgs);

    }

    public void deleteRecipe(int id){

        //getting query by the _id of recipe
        String[] whereArgs = new String[]{""+id};

        db.getWritableDatabase().delete(MY_RECIPES, COL_ID_0+"=?", whereArgs);

    }

}
