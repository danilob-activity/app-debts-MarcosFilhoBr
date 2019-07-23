package com.example.danilo.appdebts;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.danilo.appdebts.DAO.DebtDAO;
import com.example.danilo.appdebts.adapters.DebtsAdapter;
import com.example.danilo.appdebts.database.DBConnection;
import com.example.danilo.appdebts.database.Seeder;

public class MainWindow extends AppCompatActivity {

    RecyclerView mListDebts;
    DebtsAdapter mDebtsAdapter;
    DebtDAO mDebtsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteDatabase connection = DBConnection.getConnection(this);
        mDebtsDAO = new DebtDAO(connection);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cad = new Intent(MainWindow.this, InsertDebt.class);
                startActivity(cad);
            }
        });

        mListDebts = findViewById(R.id.recycler_view_debts);

//        Seeder.seed(connection);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mListDebts.setLayoutManager(linearLayoutManager);
        mDebtsAdapter = new DebtsAdapter(mDebtsDAO.list());
        mListDebts.setAdapter(mDebtsAdapter);
        mListDebts.setHasFixedSize(true);
    }

}
