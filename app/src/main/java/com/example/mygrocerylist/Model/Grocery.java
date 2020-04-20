package com.example.mygrocerylist.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Grocery.TABLE_NAME_GROCERY)
public class Grocery {

    public static final String TABLE_NAME_GROCERY = "grocery";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_NAME = "groceryName";
    public static final String FIELD_NAME_QUANTITY = "quantity";
    public static final String FIELD_NAME_DATE = "dateItemAdded";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = FIELD_NAME_NAME)
    private String mName;


    @DatabaseField(columnName = FIELD_NAME_QUANTITY)
    private String mQuantity;


    @DatabaseField(columnName = FIELD_NAME_DATE)
    private String mDate;

    public Grocery() {
    }

    public Grocery(int mId, String mName, String mQuantity, String mDate) {
        this.mId = mId;
        this.mName = mName;
        this.mQuantity = mQuantity;
        this.mDate = mDate;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(String mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
}