package com.example.mygrocerylist.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.mygrocerylist.Data.DatabaseHandler;
import com.example.mygrocerylist.Model.Grocery;
import com.example.mygrocerylist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText groceryItem;
    private EditText quantity;
    private Button button;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUpDialog();


            }
        });

        try {
            byPasssActivity();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createPopUpDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        groceryItem = view.findViewById(R.id.edit_groceryItem);
        quantity = view.findViewById(R.id.groceryQty);
        button = view.findViewById(R.id.save_button);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!groceryItem.getText().toString().isEmpty() &&
                !quantity.getText().toString().isEmpty() ){

                    saveToDB(v);

                }

            }
        });


    }
    private  void saveToDB(View v){
        Grocery grocery = new Grocery();

        String newGrocery = groceryItem.getText().toString();
        String newQuantity = quantity.getText().toString();

        grocery.setmName(newGrocery);
        grocery.setmQuantity(newQuantity);

        try {
            getDatabaseHandler().getGroceryDao().create(grocery);
            Toast.makeText(this, "Grocery inserted", Toast.LENGTH_SHORT).show();


        } catch (SQLException e) {
            e.printStackTrace();
        }







        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                finish();

            }
        }, 1200);


    }

    public DatabaseHandler getDatabaseHandler() {
        if (db == null) {
            db = OpenHelperManager.getHelper(this, DatabaseHandler.class);
        }
        return db;
    }

    public  void byPasssActivity() throws SQLException {
        if (db.getGroceryCount() > 0){
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }

    }
}
