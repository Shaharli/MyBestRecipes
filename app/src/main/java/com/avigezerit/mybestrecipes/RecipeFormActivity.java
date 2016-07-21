package com.avigezerit.mybestrecipes;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class RecipeFormActivity extends AppCompatActivity implements View.OnClickListener {

    //get instance of database manager
    MyRecpDBManager dbm = MyRecpDBManager.getInstance();

    //xml ref
    EditText recpNameET;
    EditText recpDescET;
    EditText uriET;
    Spinner typeSP;

    //intent action
    int action;
    boolean isEditing=false;

    //Cursor
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_form);

        //xml ref
        recpNameET = (EditText) findViewById(R.id.recpNameET);
        recpDescET = (EditText) findViewById(R.id.recpDescET);
        uriET = (EditText) findViewById(R.id.uriET);
        typeSP = (Spinner) findViewById(R.id.typeSP);

        checkPurpose();

        populateSpinner();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.saveBtn:

                //validate fields
                if (recpNameET.getText().toString().equals(null) || uriET.getText().toString().equals(null)){
                    Toast.makeText(this, "Make sure all fields are filled", Toast.LENGTH_SHORT).show();
                } else addNewRecipe();
                break;
            case R.id.cancelBtn:
                finish();
        }
    }

    private void addNewRecipe() {

        //creating new recipe
        Recipe r = new Recipe(
                recpNameET.getText().toString(),
                recpDescET.getText().toString(),
                uriET.getText().toString(),
                typeSP.getSelectedItemPosition());

        if (!isEditing){
            //adding to db using manager
            dbm.addNewRecipe(r);
            finish();
        }
        else{
            //setting the right _id
            r.setSql_id(getIntent().getIntExtra("_id", 0));
            //updating by id using manager
            dbm.updateRecipe(r);
            finish();
        }
    }

    private void checkPurpose() {
        //checking intent action: add=0 or edit=1
        action = getIntent().getIntExtra("action", 0);

        if (action==1){
            editMode();
            isEditing=true;
        }
    }

    private void editMode(){

        if (action == 1){

            //getting data by _id
            c = dbm.getDataAsCursorByID(getIntent().getIntExtra("_id", 0));

            if (c.moveToNext()){
                //setting data from cursor to view
                recpNameET.setText(c.getString(c.getColumnIndex(dbm.COL_NAME_1)));
                recpDescET.setText(c.getString(c.getColumnIndex(dbm.COL_DESC_2)));
                uriET.setText(c.getString(c.getColumnIndex(dbm.COL_URL_3)));
            }
        }
    }

    private void populateSpinner() {

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Vegetable");
        categories.add("Fruit");
        categories.add("Pastry");
        categories.add("Meat");
        categories.add("Dairy");
        categories.add("Soup");
        categories.add("Sushi");
        categories.add("Sweets");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSP.setAdapter(adapter);

    }
}
