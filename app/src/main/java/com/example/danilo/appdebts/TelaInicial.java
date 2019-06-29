package com.example.danilo.appdebts;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.danilo.appdebts.database.DatabaseHelper;

public class TelaInicial extends AppCompatActivity {
    private SQLiteDatabase mConection;
    private DatabaseHelper mDataHelper;
    private ConstraintLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        mLayout = findViewById(R.id.Layout);

        createConnection();
//        Categoria cat = new Categoria("Tia do Lanche");
//        CategoriaDAO catDAO = new CategoriaDAO(mConection);
//        catDAO.insert(cat);
//        catDAO.listCategorias();

        //Ca
    }



    private void createConnection() {
        try {
            mDataHelper = new DatabaseHelper(this);
            mConection = mDataHelper.getWritableDatabase();
            Snackbar.make(mLayout,"conexaoCriada", Snackbar.LENGTH_LONG).show();
        } catch (SQLException e) {
            Snackbar.make(mLayout, e.toString(), Snackbar.LENGTH_LONG).show();
        }
    }
}
