package com.example.danilo.appdebts.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.danilo.appdebts.classes.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private SQLiteDatabase mConnection;
    public CategoriaDAO(SQLiteDatabase connection){

        mConnection = connection;
    }
    public void insert(Categoria cat){

        ContentValues contentValues = new ContentValues();
        contentValues.put("tipo",cat.getTipo());
        mConnection.insertOrThrow("categoria",null,contentValues);
        Log.d("CategoriaDAO", "Inserção Feita!");

    }
    public void remove(long id){
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        mConnection.delete("categoria", "id=?", params);
    }
    public void alter(Categoria cat){
        ContentValues contentValues = new ContentValues();
        contentValues.put("tipo", cat.getTipo());
        String[] params = new String[1];
        params[0] = String.valueOf(cat.getId());
        mConnection.update("categoria", contentValues,"id=?", params);
    }

    public List<Categoria> listCategorias(){
        List<Categoria> categorias = new ArrayList<Categoria>();
        Cursor result = mConnection.rawQuery("select id, tipo from categoria",null);
        if(result.getCount()>0){
            result.moveToFirst();
            do{
                Categoria cat = new Categoria();
                cat.setId(result.getInt(result.getColumnIndexOrThrow("id")));
                cat.setTipo(result.getString(result.getColumnIndexOrThrow("tipo")));
                categorias.add(cat);
                Log.d("CategoryDAO","Listando: "+cat.getId()+" - "+cat.getTipo());
            }while(result.moveToNext());
            result.close();
        }
        return categorias;
    }
    public Categoria getCategoria(int id){
        Categoria cat = new Categoria();
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        Cursor result = mConnection.rawQuery("Select * from categoria where id=?", params);
        if (result.getCount()>0){
            result.moveToFirst();
            cat.setId(result.getInt(result.getColumnIndexOrThrow("id")));
            cat.setTipo(result.getString(result.getColumnIndexOrThrow("Tipo")));
            result.close();
            return cat;
        }

        return null;
    }
}
