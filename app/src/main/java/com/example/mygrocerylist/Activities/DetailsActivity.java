package com.example.mygrocerylist.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mygrocerylist.R;

public class DetailsActivity extends AppCompatActivity {
    private TextView itemNames;
    private TextView quantity;
    private int groceryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        itemNames = findViewById(R.id.itemNameDet);
        quantity = findViewById(R.id.quantityDet);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            itemNames.setText(bundle.getString("name"));
            quantity.setText(bundle.getString("quantity"));
            groceryId = bundle.getInt("id");

        }
    }
}
