package com.example.danilo.appdebts.DAO;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.danilo.appdebts.classes.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private SQLiteDatabase connection;
    private static final String TAG = "CategoryDAO";

    public CategoryDAO(SQLiteDatabase connection) {
        this.connection = connection;
    }

    public Category insert(Category category){
        ContentValues contentValues = new ContentValues();
        contentValues.put("tipo", category.getType());
        long id = this.connection.insertOrThrow("categoria", null, contentValues);
        category.setId(id);
        Log.d(TAG, "Categoria inserida com sucesso");
        return category;
    }

    public void remove(long id){
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        connection.delete("categoria", "id = ?", params);
        Log.d(TAG, "Categoria ID: " + id + " exlcuida com sucesso");
    }

    public void alter(Category category){
        ContentValues contentValues = new ContentValues();
        contentValues.put("tipo", category.getType());
        String[] params = new String[1];
        params[0] = String.valueOf(category.getId());
        connection.update("categoria", contentValues, "id = ?", params);
        Log.d(TAG, "Categoria ID: " + category.getId() + " alterada com sucesso");
    }

    public List<Category> list() {
        List<Category> categories = new ArrayList<Category>();
        Cursor result = connection.rawQuery("SELECT * FROM categoria", null);
        if(result.getCount() > 0) {
            result.moveToFirst();
            do {
                Category cat = new Category();
                cat.setId(result.getInt(result.getColumnIndexOrThrow("id")));
                cat.setType(result.getString(result.getColumnIndexOrThrow("tipo")));
                categories.add(cat);
                Log.d(TAG, "Listando: " + cat.getId() + " " + cat.getType());
            } while(result.moveToNext());
            result.close();
        }
        return categories;
    }

    public Category getByType(String type) {
        Category cat = new Category();
        String[] params = new String[1];
        params[0] = type;
        Cursor result = connection.rawQuery("SELECT * FROM categoria WHERE tipo=?", params);
        if(result.getCount() > 0) {
            result.moveToFirst();
            cat.setId(result.getLong(result.getColumnIndexOrThrow("id")));
            cat.setType(result.getString(result.getColumnIndexOrThrow("tipo")));
            result.close();
            return cat;
        }
        return null;
    }

    public Category get(long id){
        Category category = new Category();
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        Cursor result = connection.rawQuery("SELECT * FROM categoria WHERE id=?", params);
        if(result.getCount() > 0) {
            result.moveToFirst();
            category.setId(result.getInt(result.getColumnIndexOrThrow("id")));
            category.setType(result.getString(result.getColumnIndexOrThrow("tipo")));
            result.close();
            Log.d(TAG, "Categoria ID: " + id + " obtida com sucesso");
            return category;
        }
        return null;
    }
}
