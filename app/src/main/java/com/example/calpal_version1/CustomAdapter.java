package com.example.calpal_version1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Food> {
    public CustomAdapter(Context context, ArrayList<Food> foodItems){
        super(context, 0, foodItems);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Food foodItem = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView fatTextView = convertView.findViewById(R.id.fatTextView);
        TextView carbsTextView = convertView.findViewById(R.id.carbsTextView);
        TextView proteinTextView = convertView.findViewById(R.id.proteinTextView);

        if (foodItem != null) {
            nameTextView.setText(foodItem.getName());
            fatTextView.setText("Fat: " + foodItem.getFat());
            carbsTextView.setText("Carbs: " + foodItem.getCarbs());
            proteinTextView.setText("Protein: " + foodItem.getProtein());
        }

        return convertView;
    }
}
