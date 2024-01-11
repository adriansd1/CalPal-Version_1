package com.example.calpal_version1;

import android.content.Intent;
import android.database.Cursor;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "macros.db";
    DatabaseHelper myDbHelper;
    private ListView myListView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        myListView = findViewById(R.id.listview);
        myDbHelper = new DatabaseHelper(this);

        populateListView();
    }

    private void populateListView() {
        Log.d(DATABASE_NAME, "populateListView: Displaying data in the ListView.");
        Cursor data = myDbHelper.getData();
        ArrayList<String> nameData = new ArrayList<>();
        ArrayList<Integer> fatData = new ArrayList<>();
        ArrayList<Food> foodItems = new ArrayList<>();


        while(data.moveToNext()){
            //Name
            String name = data.getString(4);
            int fat = data.getInt(1);
            int carbs = data.getInt(2);
            int protein = data.getInt(3);

            Food foodItem = new Food(fat, carbs, protein, 0, name);
            foodItems.add(foodItem);
        }
        CustomAdapter adapter = new CustomAdapter(this, foodItems);

        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = myListView.getItemAtPosition(position).toString();
                Log.d(DATABASE_NAME, "onItemClick: You clicked " + name);

                Cursor data = myDbHelper.getFoodId(name);
                int foodID = -1;
                while(data.moveToNext()){
                    foodID = data.getInt(0);
                    toastMsg("" + foodID);
                }
                if(foodID > -1){
                    Log.d(DATABASE_NAME, "onItemClick: ID: " + foodID);
                    Intent editScreenIntent = new Intent(ListDataActivity.this, EditFoodActivity.class);
                    editScreenIntent.putExtra("ID", foodID);
                    editScreenIntent.putExtra("Name", name);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMsg("No ID associated with given Food");
                }
            }
        });
    }
    private void toastMsg(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
