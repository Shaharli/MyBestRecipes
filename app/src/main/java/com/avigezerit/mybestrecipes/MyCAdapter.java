package com.avigezerit.mybestrecipes;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Shaharli on 19/07/2016.
 */
public class MyCAdapter extends CursorAdapter {

    MyRecpDBManager dbm = MyRecpDBManager.getInstance();
    Context context;

    //reuse of the url
    View itemView;
    String itemUrl;

    public MyCAdapter(Context context, Cursor c) {
        super(context, c);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.recp_item, null);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        itemView = view;

        //xml view ref
        TextView recpNameTV = (TextView) view.findViewById(R.id.recpNameTV);
        TextView recpDescTV = (TextView) view.findViewById(R.id.recpDescTV);
        ImageView typeICON = (ImageView) view.findViewById(R.id.typeIV);
        //Button openBtn = (Button) view.findViewById(R.id.goToBtn);

        //binding data from cursor to view
        recpNameTV.setText(cursor.getString(cursor.getColumnIndex(dbm.COL_NAME_1)));
        recpDescTV.setText(cursor.getString(cursor.getColumnIndex(dbm.COL_DESC_2)));

        itemUrl = cursor.getString(cursor.getColumnIndex(dbm.COL_ID_0));

        int typeIconId = 0;

        //get resource of category
        switch (cursor.getInt(cursor.getColumnIndex(dbm.COL_TYPE_4))) {
            case 0:
                typeIconId = R.drawable.ico_veggie;
                break;
            case 1:
                typeIconId = R.drawable.ico_fruits;
                break;
            case 2:
                typeIconId = R.drawable.ico_pastry;
                break;
            case 3:
                typeIconId = R.drawable.ico_meat;
                break;
            case 4:
                typeIconId = R.drawable.ico_cheese;
                break;
            case 5:
                typeIconId = R.drawable.ico_soup;
                break;
            case 6:
                typeIconId = R.drawable.ico_sushi;
                break;
            case 7:
                typeIconId = R.drawable.ico_sweets;
                break;
        }
        typeICON.setImageResource(typeIconId);
    }


}
