package com.example.danilo.appdebts.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.danilo.appdebts.classes.Categoria;
import com.example.danilo.appdebts.classes.Debts;

import java.util.ArrayList;
import java.util.List;

public class DebtsDAO {
    private SQLiteDatabase mConnection;

    public DebtsDAO(SQLiteDatabase connection){

        mConnection = connection;
    }
    public void insertDebts(Debts debts){
        ContentValues contentValues = new ContentValues();
        contentValues.put("valor", debts.getValor());
        contentValues.put("descricao", debts.getDescricao());
        contentValues.put("data_vencimento", debts.getDataVencimento());
        contentValues.put("data_pagamento", debts.getDataPagamento());
        contentValues.put("cod_cat", debts.getCategoria().getId());
        mConnection.insertOrThrow("dividas", null, contentValues);
        Log.d("DebtsDAO", "Inserção Feita!");
    }

    public void remove(int id){
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        mConnection.delete("dividas", "id = ?",params);
    }
    public void alter(Debts debts){
        ContentValues contentValues = new ContentValues();
        contentValues.put("valor", debts.getValor());
        contentValues.put("descricao", debts.getDescricao());
        contentValues.put("data_vencimento", debts.getDataVencimento());
        contentValues.put("data_pagamento", debts.getDataPagamento());
        contentValues.put("cod_cat", debts.getCategoria().getId());
        String[] params = new String[1];
        params[0] = String.valueOf(debts.getId());
        mConnection.update("dividas",contentValues, "id = ?",params);
    }

    public List<Debts> listDividas(){
        List<Debts> dividas = new ArrayList<Debts>();
        Cursor result = mConnection.rawQuery("Select id, tipo from dividas",null);
        if(result.getCount()>0){
            result.moveToFirst();
            CategoriaDAO categoryDAO = new CategoriaDAO(mConnection);
            do{
                Debts div = new Debts();
                Categoria cat = categoryDAO.getCategoria(result.getInt(result.getColumnIndexOrThrow("cod_cat")));
                div.setId(result.getInt(result.getColumnIndexOrThrow("id")));
                div.setValor(result.getFloat(result.getColumnIndexOrThrow("valor")));
                div.setDescricao(result.getString(result.getColumnIndexOrThrow("descricao")));
                div.setDataVencimento(result.getString(result.getColumnIndexOrThrow("data_vencimento")));
                div.setDataPagamento(result.getString(result.getColumnIndexOrThrow("data_pagamento")));
                div.setCategoria(cat);
                dividas.add(div);
                Log.d("DebtsDAO","Listando: "+ div.getId()+" - "+ div.getDescricao()+" - " + div.getValor());
            }while(result.moveToNext());
            result.close();
        }
        return dividas;
    }

    public Debts getDebts(int id){
        Debts div = new Debts();
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        CategoriaDAO categoryDAO = new CategoriaDAO(mConnection);
        Cursor result = mConnection.rawQuery("Select * from categoria where id='?'",params);
        if(result.getCount()>0){
            result.moveToFirst();
            Categoria cat = categoryDAO.getCategoria(result.getInt(result.getColumnIndexOrThrow("cod_cat")));
            div.setId(result.getInt(result.getColumnIndexOrThrow("id")));
            div.setValor(result.getFloat(result.getColumnIndexOrThrow("valor")));
            div.setDescricao(result.getString(result.getColumnIndexOrThrow("descricao")));
            div.setDataVencimento(result.getString(result.getColumnIndexOrThrow("data_vencimento")));
            div.setDataPagamento(result.getString(result.getColumnIndexOrThrow("data_pagamento")));
            div.setCategoria(cat);
            result.close();
            return div;
        }

        return null;

    }



}