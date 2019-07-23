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
    private SQLiteDatabase connection;
    private static final String TAG = "DebtDAO";

    public DebtsDAO(SQLiteDatabase connection) {
        this.connection = connection;
    }

    public Debts insert(Debts debt) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cod_cat", debt.getCategoria().getId());
        contentValues.put("valor", debt.getValor());
        contentValues.put("descricao", debt.getDescricao());
        contentValues.put("data_vencimento", debt.getDataPagamento());
        contentValues.put("data_pagamento", debt.getDataPagamento());
        long id = this.connection.insertOrThrow("dividas", null, contentValues);
        debt.setId(id);
        Log.d(TAG, "Divida inserida com sucesso");
        return debt;
    }

    public void remove(long id) {
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        connection.delete("dividas", "id = ?", params);
        Log.d(TAG, "Divida ID: " + id + " excluida com sucesso");
    }

    public void alter(Debts debt) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cod_cat", debt.getCategoria().getId());
        contentValues.put("valor", debt.getValor());
        contentValues.put("descricao", debt.getDescricao());
        contentValues.put("data_vencimento", debt.getDataVencimento());
        contentValues.put("data_pagamento", debt.getDataPagamento());
        String[] params = new String[1];
        params[0] = String.valueOf(debt.getId());
        connection.update("dividas", contentValues, "id = ?", params);
        Log.d(TAG, "Divida ID: " + debt.getId() + " alterada com sucesso");
    }

    public List<Debts> list() {
        List<Debts> debts = new ArrayList<Debts>();
        Cursor result = connection.rawQuery("SELECT * FROM dividas", null);
        CategoriaDAO categoriaDAO = new CategoriaDAO(connection);
        if (result.getCount() > 0) {
            result.moveToFirst();
            do {
                Debts deb = new Debts();
                Categoria categoria = categoriaDAO.get(result.getInt(result.getColumnIndexOrThrow("cod_cat")));
                deb.setId(result.getInt(result.getColumnIndexOrThrow("id")));
                deb.setValor(result.getDouble(result.getColumnIndexOrThrow("valor"));
                deb.setDescricao(result.getString(result.getColumnIndexOrThrow("descricao")));
                deb.setDataVencimento(result.getString(result.getColumnIndexOrThrow("data_vencimento")));;
                deb.setDataPagamento(result.getString(result.getColumnIndexOrThrow("data_pagamento")));
                deb.setCategoria(categoria);
                debts.add(deb);
                Log.d(TAG, "Listando: " + deb.getId() + " - " + deb.getDescricao());
            } while(result.moveToNext());
            result.close();
        }
        return debts;
    }

    public Debts get(long id) {
        Debts debts = new Debts();
        CategoriaDAO categoriaDAO = new CategoriaDAO(connection);
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        Cursor result = connection.rawQuery("SELECT * FROM dividas WHERE id = ?", params);
        if(result.getCount() > 0) {
            result.moveToFirst();
            Categoria categoria = categoriaDAO.get(result.getColumnIndexOrThrow("cod_cat"));
            debts.setId(result.getInt(result.getColumnIndexOrThrow("id")));
            debts.setValor(result.getDouble(result.getColumnIndexOrThrow("valor"));
            debts.setDescricao(result.getString(result.getColumnIndexOrThrow("descricao")));
            debts.setDataVencimento(result.getString(result.getColumnIndexOrThrow("data_vencimento")))];
            debts.setDataPagamento(result.getString(result.getColumnIndexOrThrow("data_pagamento")));
            debts.setCategoria(categoria);
            result.close();
            Log.d(TAG, "Divida ID: " + id + " obtida com sucesso");
            return debts;
        }
        return null;
    }



}