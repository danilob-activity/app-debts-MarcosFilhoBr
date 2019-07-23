package com.example.danilo.appdebts;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.danilo.appdebts.classes.Categoria;
import com.example.danilo.appdebts.classes.Debts;
import com.example.danilo.appdebts.dao.CategoriaDAO;
import com.example.danilo.appdebts.dao.DebtsDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class InsertDebts extends AppCompatActivity {

    private FloatingActionButton mFabNewCategory;
    private Spinner mSpinnerCategorias;
    private EditText mEditTextDescricao;
    private EditText mEditTextData;
    private EditText mEditTextValor;
    private Switch mSwitchPay;

    private final Calendar myCalendar = Calendar.getInstance();
    private CategoriaDAO mCategoriaDAO;
    private DebtsDAO mDebtsDAO;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_debts,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_debt);

        //Linking Elements
        mFabNewCategory = findViewById(R.id.fabNewCategory);
        mSpinnerCategorias = findViewById(R.id.spinnerCategories);
        mEditTextDescricao = findViewById(R.id.editTextDescription);
        mEditTextData = findViewById(R.id.editTextDate);
        mEditTextValor = findViewById(R.id.editTextValue);
        mSwitchPay = findViewById(R.id.switchPay);

        //configurate DAO classes to access db
        SQLiteDatabase connection = DBConnection.getConnection(this);
        mCategoriaDAO = new CategoriaDAO(connection);
        mDebtsDAO = new DebtsDAO(connection);

        //ActionBar Configuration
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true); //Ativar o botão
        getSupportActionBar().setTitle(R.string.insertTitle);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        mEditTextData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(InsertDebts.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mFabNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InsertDebts.this);
                builder.setTitle(R.string.newCategoryTitle);

                final EditText edtText = new EditText(InsertDebts.this);
                edtText.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(edtText);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mCategoriaDAO.insert(new Categoria(edtText.getText().toString()));
                        updateSpinnerCategory();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

        updateSpinnerCategory();
    }

    private void updateSpinnerCategory() {
        List<Categoria> categories = mCategoriaDAO.list();
        final List<String> list = new ArrayList<String>();

        for (Categoria c : categories) {
            list.add(c.getTipo());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCategorias.setAdapter(adapter);
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        mEditTextData.setText(sdf.format(myCalendar.getTime()));
    }

    // Faz a validação dos dados
    private Debts checkData() {
        String msg = "";

        if (mEditTextDescricao.getText().toString().isEmpty())
            msg = "* Informe a descrição do débito.\n";

        if (mEditTextValor.getText().toString().isEmpty())
            msg += "* Informe o valor do débito.\n";
        else if (Float.parseFloat(mEditTextValor.getText().toString()) <= 0)
            msg += "* Informe um valor maior que zero para o débito.\n";

        if (mEditTextData.getText().toString().isEmpty())
            msg += "* Informe a data do débito.\n";

        if (!msg.isEmpty())
            createErrorDialog(msg);
        else
            return createDebt();

        return null;
    }

    // Cria o Dialog para exibir os erros
    private void createErrorDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.create();

        builder.show();
    }

    // Cria o Debt com base nos valores dos campos
    private Debts createDebt() {
        Debts debt = new Debts();
        debt.setCategoria(mCategoriaDAO.getByType(mSpinnerCategorias.getSelectedItem().toString()));
        debt.setDataPagamento(mEditTextData.getText().toString());
        debt.setDescricao(mEditTextDescricao.getText().toString());
        debt.setValor(Float.parseFloat(mEditTextValor.getText().toString()));

        if (mSwitchPay.isChecked())
            debt.setDataPagamento(debt.getDataPagamento());
        else
            debt.setDataPagamento("");

        return debt;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home: //ID do seu botão (gerado automaticamente pelo android, usando comoestá, deve funcionar
                startActivity(new Intent(this, MainWindow. class)); //O efeito ao ser pressionado do botão(no caso abre a activity)
                finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;

            case R.id.okMenu:
                Debts debts = checkData();
                if (debts != null) {
                    mDebtsDAO.insert(debts);
                    startActivity( new Intent(this, MainWindow.class));
                    finishAffinity();
                }
                break;
            default:break;
        }
        return true;
    }
    }
}
