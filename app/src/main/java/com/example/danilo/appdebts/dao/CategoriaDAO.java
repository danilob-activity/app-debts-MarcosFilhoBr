package com.example.danilo.appdebts.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.danilo.appdebts.classes.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private SQLiteDatabase connection;
    private static final String TAG = "CategoriaDAO";

    public CategoriaDAO(SQLiteDatabase connection) {
        this.connection = connection;
    }

    public Categoria insert(Categoria categoria){
        ContentValues contentValues = new ContentValues();
        contentValues.put("tipo", categoria.getTipo());
        long id = this.connection.insertOrThrow("categoria", null, contentValues);
        categoria.setId(id);
        Log.d(TAG, "Categoria inserida com sucesso");
        return categoria;
    }

    public void remove(long id){
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        connection.delete("categoria", "id = ?", params);
        Log.d(TAG, "Categoria ID: " + id + " exlcuida com sucesso");
    }

    public void alter(Categoria categoria){
        ContentValues contentValues = new ContentValues();
        contentValues.put("tipo", categoria.getTipo());
        String[] params = new String[1];
        params[0] = String.valueOf(categoria.getId());
        connection.update("categoria", contentValues, "id = ?", params);
        Log.d(TAG, "Categoria ID: " + categoria.getId() + " alterada com sucesso");
    }

    public List<Categoria> list() {
        List<Categoria> categories = new ArrayList<Categoria>();
        Cursor result = connection.rawQuery("SELECT * FROM categoria", null);
        if(result.getCount() > 0) {
            result.moveToFirst();
            do {
                Categoria cat = new Categoria(edtText.getText().toString());
                cat.setId(result.getInt(result.getColumnIndexOrThrow("id")));
                cat.setTipo(result.getString(result.getColumnIndexOrThrow("tipo")));
                categories.add(cat);
                Log.d(TAG, "Listando: " + cat.getId() + " " + cat.getTipo());
            } while(result.moveToNext());
            result.close();
        }
        return categories;
    }

    public Categoria getByType(String type) {
        Categoria cat = new Categoria(edtText.getText().toString());
        String[] params = new String[1];
        params[0] = type;
        Cursor result = connection.rawQuery("SELECT * FROM categoria WHERE tipo=?", params);
        if(result.getCount() > 0) {
            result.moveToFirst();
            cat.setId(result.getLong(result.getColumnIndexOrThrow("id")));
            cat.setTipo(result.getString(result.getColumnIndexOrThrow("tipo")));
            result.close();
            return cat;
        }
        return null;
    }

    public Categoria get(long id){
        Categoria category = new Categoria(edtText.getText().toString());
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        Cursor result = connection.rawQuery("SELECT * FROM categoria WHERE id=?", params);
        if(result.getCount() > 0) {
            result.moveToFirst();
            category.setId(result.getInt(result.getColumnIndexOrThrow("id")));
            category.setTipo(result.getString(result.getColumnIndexOrThrow("tipo")));
            result.close();
            Log.d(TAG, "Categoria ID: " + id + " obtida com sucesso");
            return category;
        }
        return null;
    }
}
