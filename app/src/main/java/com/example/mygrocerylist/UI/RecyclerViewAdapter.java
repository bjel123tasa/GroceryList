package com.example.mygrocerylist.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygrocerylist.Activities.DetailsActivity;
import com.example.mygrocerylist.Data.DatabaseHandler;
import com.example.mygrocerylist.Model.Grocery;
import com.example.mygrocerylist.R;
import com.google.android.material.snackbar.Snackbar;

import java.sql.SQLException;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Grocery> listOfItems;
     private AlertDialog.Builder alertDialogBuilder;
     private AlertDialog dialog;
     private LayoutInflater inflater;


    public RecyclerViewAdapter(Context context, List<Grocery> listOfItems) {
        this.context = context;
        this.listOfItems = listOfItems;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);


        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Grocery grocery = listOfItems.get(position);


        holder.groceryName.setText(grocery.getmName());
        holder.groceryQuantity.setText(grocery.getmQuantity());



    }

    @Override
    public int getItemCount() {
        return listOfItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public TextView groceryName;
        public TextView groceryQuantity;
        public Button buttonEdit;
        public Button buttonDelete;
        public int id;

        public ViewHolder(@NonNull View view, Context ctx) {
            super(view);
            context = ctx;

            groceryName =  view.findViewById(R.id.name);
            groceryQuantity = view.findViewById(R.id.quantity);

            buttonEdit = view.findViewById(R.id.button_edit);
            buttonDelete = view.findViewById(R.id.button_delete);

            buttonEdit.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Grocery grocery = listOfItems.get(position);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("name", grocery.getmName());
                    intent.putExtra("quantity", grocery.getmQuantity());
                    intent.putExtra("id", grocery.getmId());
                    context.startActivity(intent);

                }
            });

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_edit:
                    int position = getAdapterPosition();
                    Grocery grocery = listOfItems.get(position);
                    editItem(grocery);

                    break;
                case R.id.button_delete:
                     int position1  = getAdapterPosition();
                      Grocery grocery1 = listOfItems.get(position1);
                    deleteItem(grocery1.getmId());

            }

        }

        public  void editItem(final Grocery grocery){
            alertDialogBuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.popup, null);

            final EditText groceryItem = view.findViewById(R.id.edit_groceryItem);
            final EditText groceryQuantity = view.findViewById(R.id.groceryQty);
           final TextView title = view.findViewById(R.id.title);
            title.setText("Edit Grocery Item");

            Button saveButton = view.findViewById(R.id.save_button);

            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);

                    grocery.setmName(groceryItem.getText().toString());
                    grocery.setmQuantity(groceryQuantity.getText().toString());

                    if (!groceryItem.getText().toString().isEmpty() &&
                    !groceryQuantity.getText().toString().isEmpty()){
                        try {
                            db.updateGrocery(grocery);
                            notifyItemChanged(getAdapterPosition(), grocery);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                    }
                    else {
                        Snackbar.make(view, "Add grocery", Snackbar.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });


        }

        public void deleteItem(final int id){

            alertDialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_dialog, null);

            Button noButton = view.findViewById(R.id.no_button);
            Button yesButton = view.findViewById(R.id.yes_button);

            alertDialogBuilder.setView(view);

            dialog = alertDialogBuilder.create();
            dialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    try {
                        db.deleteGrocery(id);
                        listOfItems.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());

                        dialog.dismiss();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
}
