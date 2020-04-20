package com.example.mygrocerylist.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.mygrocerylist.Data.DatabaseHandler;
import com.example.mygrocerylist.Model.Grocery;
import com.example.mygrocerylist.UI.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mygrocerylist.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Grocery> groceryList;
    private List<Grocery> list;
    private DatabaseHandler db;
    AlertDialog dialog;
    AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab_list);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpDialog();




            }
        });
        showAllGroceries();
    }

    public  void showAllGroceries(){
        db = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryList = new ArrayList<>();
        list = new ArrayList<>();

        try {
            groceryList = getDatabaseHandler().getAllGroceries();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Grocery g : groceryList){
            Grocery grocery = new Grocery();
            grocery.setmName(g.getmName());
            grocery.setmQuantity("quantity: " + g.getmQuantity());
            grocery.setmId(g.getmId());

            list.add(grocery);

        }
        adapter = new RecyclerViewAdapter(this, list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public DatabaseHandler getDatabaseHandler() {
        if (db == null) {
            db = OpenHelperManager.getHelper(this, DatabaseHandler.class);
        }
        return db;
    }

    private void createPopUpDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        final EditText groceryItem = view.findViewById(R.id.edit_groceryItem);
        final EditText quantity = view.findViewById(R.id.groceryQty);
        Button button = view.findViewById(R.id.save_button);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();
        final Grocery grocery = new Grocery();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!groceryItem.getText().toString().isEmpty() &&
                        !quantity.getText().toString().isEmpty()) {
                    String newGrocery = groceryItem.getText().toString();
                    String newQuantity = quantity.getText().toString();

                    grocery.setmName(newGrocery);
                    grocery.setmQuantity(newQuantity);

                    try {
                        getDatabaseHandler().getGroceryDao().create(grocery);
                        list.add(grocery);
                        Toast.makeText(getBaseContext(), "Grocery inserted", Toast.LENGTH_SHORT).show();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            startActivity(new Intent(ListActivity.this, ListActivity.class));
                            finish();

                        }
                    }, 500);

                }

            }

            });

    }
}
