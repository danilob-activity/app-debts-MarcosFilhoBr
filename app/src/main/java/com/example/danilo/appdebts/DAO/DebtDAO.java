package com.example.danilo.appdebts.DAO;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.danilo.appdebts.classes.Category;
import com.example.danilo.appdebts.classes.Debt;

import java.util.ArrayList;
import java.util.List;

public class DebtDAO {
    private SQLiteDatabase connection;
    private static final String TAG = "DebtDAO";

    public DebtDAO(SQLiteDatabase connection) {
        this.connection = connection;
    }

    public Debt insert(Debt debt) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cod_cat", debt.getCategory().getId());
        contentValues.put("valor", debt.getValue());
        contentValues.put("descricao", debt.getDescription());
        contentValues.put("data_vencimento", debt.getPaymentDate());
        contentValues.put("data_pagamento", debt.getPayDate());
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

    public void alter(Debt debt) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cod_cat", debt.getCategory().getId());
        contentValues.put("valor", debt.getValue());
        contentValues.put("descricao", debt.getDescription());
        contentValues.put("data_vencimento", debt.getPaymentDate());
        contentValues.put("data_pagamento", debt.getPayDate());
        String[] params = new String[1];
        params[0] = String.valueOf(debt.getId());
        connection.update("dividas", contentValues, "id = ?", params);
        Log.d(TAG, "Divida ID: " + debt.getId() + " alterada com sucesso");
    }

    public List<Debt> list() {
        List<Debt> debts = new ArrayList<Debt>();
        Cursor result = connection.rawQuery("SELECT * FROM dividas", null);
        CategoryDAO categoryDAO = new CategoryDAO(connection);
        if (result.getCount() > 0) {
            result.moveToFirst();
            do {
                Debt deb = new Debt();
                Category category = categoryDAO.get(result.getInt(result.getColumnIndexOrThrow("cod_cat")));
                deb.setId(result.getInt(result.getColumnIndexOrThrow("id")));
                deb.setValue(result.getDouble(result.getColumnIndexOrThrow("valor")));
                deb.setDescription(result.getString(result.getColumnIndexOrThrow("descricao")));
                deb.setPaymentDate(result.getString(result.getColumnIndexOrThrow("data_vencimento")));
                deb.setPayDate(result.getString(result.getColumnIndexOrThrow("data_pagamento")));
                deb.setCategory(category);
                debts.add(deb);
                Log.d(TAG, "Listando: " + deb.getId() + " - " + deb.getDescription());
            } while(result.moveToNext());
            result.close();
        }
        return debts;
    }

    public Debt get(long id) {
        Debt debts = new Debt();
        CategoryDAO categoryDAO = new CategoryDAO(connection);
        String[] params = new String[1];
        params[0] = String.valueOf(id);
        Cursor result = connection.rawQuery("SELECT * FROM dividas WHERE id = ?", params);
        if(result.getCount() > 0) {
            result.moveToFirst();
            Category category = categoryDAO.get(result.getColumnIndexOrThrow("cod_cat"));
            debts.setId(result.getInt(result.getColumnIndexOrThrow("id")));
            debts.setValue(result.getDouble(result.getColumnIndexOrThrow("valor")));
            debts.setDescription(result.getString(result.getColumnIndexOrThrow("descricao")));
            debts.setPaymentDate(result.getString(result.getColumnIndexOrThrow("data_vencimento")));
            debts.setPayDate(result.getString(result.getColumnIndexOrThrow("data_pagamento")));
            debts.setCategory(category);
            result.close();
            Log.d(TAG, "Divida ID: " + id + " obtida com sucesso");
            return debts;
        }
        return null;
    }
}
