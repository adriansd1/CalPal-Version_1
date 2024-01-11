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

/**
 * @author samda
 * @version 1.0.0
 *@Description Main activity for the calorie tracking app CalPal; contains the backend for the activity_main.xml
 * Contains everything needed to add custom foods and buttons to direct user to the log
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Tag for debugging purposes
    private static final String TAG = "MainActivity";

    //SQLite database used to store foods added by user
    DatabaseHelper myDbHelper;

    //Declaration of constant values for the number of calories per gram of each macronutrient
    protected final int CALS_G_OF_FAT = 9;
    protected final int CALS_G_OF_CARBS = 4;
    protected final int CALS_G_OF_PROTEIN = 4;

    //Declaration of buttons used to add foods to the log and to view said log
    Button addFoodButton;
    Button viewLogButton;

    //Declaration of inputs for macronutrients and name where user can add the values/name for each
    EditText editTextFat;
    EditText editTextCarb;
    EditText editTextProtein;
    EditText editTextName;

    //Declaration of variables to store the given macronutrient values and food name
    int fatNum = 0;
    int carbNum = 0;
    int proteinNum = 0;
    String foodName = new String();

    //Declaration of variable to store total value of each macronutrient as well as total calories
    int fatTotal = 0;
    int carbTotal = 0;
    int proteinTotal = 0;
    int totalCalories = 0;

    //Declaration of outputs for total macronutrients of the day as well as calories
    TextView fatOutput;
    TextView carbOutput;
    TextView proteinOutput;
    TextView calOutput;


    /**
     * 
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDbHelper = new DatabaseHelper(this);

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
