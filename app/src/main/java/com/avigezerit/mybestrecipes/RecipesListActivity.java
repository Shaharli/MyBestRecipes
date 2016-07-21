package com.avigezerit.mybestrecipes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class RecipesListActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    //get instance of database manager
    MyRecpDBManager dbm = MyRecpDBManager.getInstance();

    //initialize db
    MyRecpDBHelper db;

    //xml ref
    ListView recpLV;

    //re-using intent
    Intent intent;

    //define _id var for context menu
    int selected_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //action add=0
                intent = new Intent(RecipesListActivity.this,RecipeFormActivity.class).putExtra("action", 0);
                startActivity(intent);
            }
        });

        //initialize database helper
        db = new MyRecpDBHelper(this);

        //xml ref
        recpLV = (ListView) findViewById(R.id.recpLV);

        recpLV.setOnItemClickListener(this);

        refreshData();

        registerForContextMenu(recpLV);

    }

    private void refreshData(){

        //cursor adapter
        MyCAdapter adapter = new MyCAdapter(this, dbm.getAllDataAsCursor());

        //bind list view with cursor
        recpLV.setAdapter(adapter);

        if (adapter.getCount() != 0){
            recpLV.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.exitMI:
                finish();
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.recipe_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //get position in list view
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //getting a cursor to access all the database
        final Cursor c = dbm.getAllDataAsCursor();

        //moving the cursor to a info.position
        c.moveToPosition(info.position);

        //extracting id and setting the var
        selected_id = c.getInt(c.getColumnIndex(dbm.COL_ID_0));

        switch (item.getItemId()){

            case R.id.editRecpMI:
                //action edit=1, with _id
                intent = new Intent(this,RecipeFormActivity.class);
                intent.putExtra("action", 1).putExtra("_id", selected_id);
                startActivity(intent);
                break;

            case R.id.deleteRecpMI:
                //creating dialog click listener
                DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //delete by _id using manager
                                dbm.deleteRecipe(selected_id);
                                refreshData();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                        }
                    }
                };

                //creating alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete this recipe?")
                        .setPositiveButton("YES", dialog)
                        .setNegativeButton("NO", dialog)
                        .show();
        }

        return true;
    }

    @Override
    protected void onResume() {
        refreshData();
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //getting a cursor to access all the database
        Cursor c = dbm.getAllDataAsCursor();

        //moving the cursor to a position
        c.moveToPosition(position);

        String uri = c.getString(c.getColumnIndex(dbm.COL_URL_3));
        String name = c.getString(c.getColumnIndex(dbm.COL_NAME_1));

        Toast.makeText(this, "opening " + name, Toast.LENGTH_SHORT).show();

        if (!uri.startsWith("http://") && !uri.startsWith("https://")) uri = "http://" + uri;

        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
}
