package com.example.calpal_version1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditFoodActivity extends AppCompatActivity {
    private static final String TAG = "EditFoodActivity";
    private Button saveButton, deleteButton;
    private EditText editable_Food;
    DatabaseHelper myDbHelper;
    private String selectedName;
    private int selectedID;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_food_layout);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
        editable_Food = findViewById(R.id.editable_food);
        myDbHelper = new DatabaseHelper(this);

        Intent recievedIntent = getIntent();

        selectedID = recievedIntent.getIntExtra("ID", -1);
        selectedName = recievedIntent.getStringExtra("Name");

        editable_Food.setText(selectedName);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = editable_Food.getText().toString();
                if(!item.equals("")){
                    myDbHelper.updateName(item, selectedID, selectedName);
                }
                else{toastMsg("You Must Enter a Food Name");}
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDbHelper.deleteFood(selectedID, selectedName);
                editable_Food.setText("");
                toastMsg("Food Deleted From Log");
            }
        });

    }

    private void toastMsg(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
