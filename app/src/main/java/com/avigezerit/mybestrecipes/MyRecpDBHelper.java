package com.avigezerit.mybestrecipes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shaharli on 19/07/2016.
 */
public class MyRecpDBHelper extends SQLiteOpenHelper {

    //constants name of data base and table
    public static final String DB_NAME = "recipes.db";
    public static final int DB_VERSION = 1;

    //get instance of database manager
    MyRecpDBManager dbm;

    //get context
    Context c;

    public MyRecpDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        c = context;
        dbm = MyRecpDBManager.getInstance();
        dbm.setContext(c);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //creating my recipes table
        String createRecipesTable = "CREATE TABLE "
                + dbm.MY_RECIPES + " ("
                + dbm.COL_ID_0 + " INTEGER PRIMARY KEY, "
                + dbm.COL_NAME_1 + " TEXT, "
                + dbm.COL_DESC_2 + " TEXT, "
                + dbm.COL_URL_3 + " TEXT, "
                + dbm.COL_TYPE_4 + " INTEGER);";

        db.execSQL(createRecipesTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
