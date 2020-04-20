package com.example.mygrocerylist.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mygrocerylist.Model.Grocery;
import com.example.mygrocerylist.Util.Constants;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME    = "ormlite.db";
    private static final int    DATABASE_VERSION = 1;

    private Dao<Grocery, Integer> mProductDao = null;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Grocery.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Grocery.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Dao<Grocery, Integer> getGroceryDao() throws SQLException {
        if (mProductDao == null) {
            mProductDao = getDao(Grocery.class);
        }

        return mProductDao;
    }

    @Override
    public void close() {
        mProductDao = null;

        super.close();
    }

    public List<Grocery> getAllGroceries() throws SQLException {
        final List<Grocery> groceryList = getGroceryDao().queryForAll();

        return groceryList;
    }

    public Grocery getGrorcery(int id) throws SQLException {
        Grocery grocery = getGroceryDao().queryForId(id);

        return grocery;
    }
    public void deleteGrocery(int id) throws SQLException {

            getGroceryDao().deleteById(id);

        }


    public  void updateGrocery(Grocery grocery) throws SQLException {
            getGroceryDao().update(grocery);




    }

    public int getGroceryCount() throws SQLException {
        final List<Grocery> groceryList = getGroceryDao().queryForAll();

        return  groceryList.size();

    }


}
