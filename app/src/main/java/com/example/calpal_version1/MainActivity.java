package com.example.calpal_version1;

import static com.example.calpal_version1.DatabaseHelper.COL2;
import static com.example.calpal_version1.DatabaseHelper.COL3;
import static com.example.calpal_version1.DatabaseHelper.COL4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Declaration of all variables

    private static final String TAG = "MainActivity";
    DatabaseHelper myDbHelper;

    final int CALS_G_OF_FAT = 9;
    final int CALS_G_OF_CARBS = 4;
    final int CALS_G_OF_PROTEIN = 4;

    Button fatButton;
    Button carbButton;
    Button proteinButton;

    Button addFoodButton;

    Button viewLogButton;

    //Inputs for macronutrients
    EditText editTextFat;
    EditText editTextCarb;
    EditText editTextProtein;

    EditText editTextName;

    //Integer values for macronutrient inputs
    int fatNum = 0;
    int carbNum = 0;
    int proteinNum = 0;

    String foodName = new String();

    //Total value of each macronutrient as well as total calories
    int fatTotal = 0;
    int carbTotal = 0;
    int proteinTotal = 0;
    int totalCalories = 0;

    //Outputs for macros
    TextView fatOutput;
    TextView carbOutput;
    TextView proteinOutput;
    TextView calOutput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDbHelper = new DatabaseHelper(this);

        //fatButton = findViewById(R.id.fatButton);
        //fatButton.setOnClickListener(this);

        //carbButton = findViewById(R.id.carbButton);
        //carbButton.setOnClickListener(this);

        //proteinButton = findViewById(R.id.proteinButton);
        //proteinButton.setOnClickListener(this);

        addFoodButton = findViewById(R.id.addFoodButton);
        addFoodButton.setOnClickListener(this);

        viewLogButton = findViewById(R.id.viewLogButton);
        viewLogButton.setOnClickListener(this);

        editTextFat = findViewById(R.id.fatInput);
        editTextCarb = findViewById(R.id.carbInput);
        editTextProtein = findViewById(R.id.proteinInput);
        editTextName = findViewById(R.id.foodNameInput);


        fatOutput = findViewById(R.id.fatOutput);
        carbOutput = findViewById(R.id.carbOutput);
        proteinOutput = findViewById(R.id.proteinOutput);
        calOutput = findViewById(R.id.totalCalories);

    }

    public int getConvertInput(EditText editText, String macro){
        if(editText.getText().toString().equals("")){
            Toast.makeText(this, "Input " + macro + "(g)", Toast.LENGTH_SHORT).show();
            return 0;
        } else
            return Integer.parseInt(editText.getText().toString());
    }
    public String getConvertInputName(EditText editText){
        if(editText.getText().toString().equals("")){
            Toast.makeText(this, "Input Food Name", Toast.LENGTH_SHORT).show();
            return "";
        } else
            return editText.getText().toString();
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addFoodButton) {
            fatNum = getConvertInput(editTextFat, "Fat");
            fatTotal += fatNum;
            totalCalories += CALS_G_OF_FAT * fatNum;
            fatOutput.setText("Fat(g): " + fatTotal + "g");
            calOutput.setText("Total Calories: " + totalCalories);

            carbNum = getConvertInput(editTextCarb, "Carbs");
            carbTotal += carbNum;
            totalCalories += CALS_G_OF_CARBS * carbNum;
            carbOutput.setText("Carbs(g): " + carbTotal + "g");
            calOutput.setText("Total Calories: " + totalCalories);

            proteinNum = getConvertInput(editTextProtein, "Protein");
            totalCalories += CALS_G_OF_PROTEIN * proteinNum;
            proteinTotal += proteinNum;
            proteinOutput.setText("Protein(g): " + proteinTotal + "g");
            calOutput.setText("Total Calories: " + totalCalories);

            foodName = new String(getConvertInputName(editTextName));

            AddData(fatNum, carbNum, proteinNum, foodName);
        }
        else if(v.getId() == R.id.viewLogButton){
            Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
            startActivity(intent);
        }


            /*if(v.getId() == R.id.fatButton){
                fatNum = getConvertInput(editTextFat, "Fat");
                fatTotal +=fatNum;
                totalCalories += CALS_G_OF_FAT*fatNum;
                fatOutput.setText("Fat(g): " + fatTotal + "g");
                calOutput.setText("Total Calories: " + totalCalories);
                AddData(0, 0, 0, foodName);
            }
            else if(v.getId() == R.id.carbButton){
                carbNum = getConvertInput(editTextCarb, "Carbs");
                carbTotal +=carbNum;
                totalCalories += CALS_G_OF_CARBS*carbNum;
                carbOutput.setText("Carbs(g): " + carbTotal + "g");
                calOutput.setText("Total Calories: " + totalCalories);
                //TEMP MOVE TO KNEW BUTTON LATER
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);

            }
            else if(v.getId() == R.id.proteinButton){
                proteinNum = getConvertInput(editTextProtein, "Protein");
                totalCalories += CALS_G_OF_PROTEIN*proteinNum;
                proteinTotal +=proteinNum;
                proteinOutput.setText("Protein(g): " + proteinTotal + "g");
                calOutput.setText("Total Calories: " + totalCalories);
            }*/
        }
        public void AddData(int fatEntry, int carbEntry, int proteinEntry, String nameEntry)
        {
            boolean insertData = myDbHelper.addData(fatEntry, carbEntry, proteinEntry, nameEntry);
            if(insertData){
                Toast.makeText(this, "Food Added Successfully", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Error Adding Macro", Toast.LENGTH_SHORT).show();
            }

        }
}
