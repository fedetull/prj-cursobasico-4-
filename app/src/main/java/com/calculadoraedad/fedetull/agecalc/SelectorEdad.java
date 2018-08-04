package com.calculadoraedad.fedetull.agecalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SelectorEdad extends AppCompatActivity implements View.OnClickListener{
    private DatePicker mDatePicker;
    int ageYears,ageMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        mDatePicker = findViewById(R.id.selectorFecha);
        findViewById(R.id.selectorFecha).setOnClickListener(this);

        init();

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Seleccionar fecha de nacimiento");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
    public void init(){
    findViewById(R.id.btnsetEdad).setOnClickListener(this);
    findViewById(R.id.btncancel).setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnsetEdad:
                int bDay = mDatePicker.getDayOfMonth();
                int bMonth = mDatePicker.getMonth();
                bMonth = bMonth + 1;
                int bYear = mDatePicker.getYear();

                calcularEdad(bDay, bMonth, bYear);
                break;
            case R.id.btncancel:
                Cancelar();
                break;
        }
    }
    public boolean onOptionsItemSelected (MenuItem item){
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    public void calcularEdad(int diaN, int mesN, int anoN){
        Calendar mCalendar = Calendar.getInstance();
        Locale local = Locale.getDefault();

        //Dia
        SimpleDateFormat mDayFormat = new SimpleDateFormat("dd", local);
        String diaa = mDayFormat.format(mCalendar.getTime());

        //Mes
        SimpleDateFormat mMonthFormat = new SimpleDateFormat("MM", local);
        String mesa = mMonthFormat.format(mCalendar.getTime());

        //Anio
        SimpleDateFormat mYearFormat = new SimpleDateFormat("YYYY", local);
        String anoa = mYearFormat.format(mCalendar.getTime());

        int aDay = Integer.parseInt(diaa);
        int aMonth = Integer.parseInt(mesa);
        int aYear = Integer.parseInt(anoa);

        ageYears = aYear - anoN;
        ageMonth = aMonth - mesN;

        if(diaN == aDay && mesN == aMonth && anoN < aYear){
            PasarValores();
            Toast.makeText(this, "Felicidades", Toast.LENGTH_SHORT).show();
        }
        else if(anoN > aYear || anoN == aYear && mesN >= aMonth && diaN > aDay){
            Toast.makeText(this, "No te puedo creer, Vienes del futuro!!", Toast.LENGTH_SHORT).show();
        }
        else if(diaN < aDay && mesN == aMonth){
            ageMonth = ageMonth - 1;
            PasarValores();
        }
        else if (aMonth < mesN && aYear != anoN){
            ageYears = ageYears-1;
            PasarValores();
        }
        else {
            PasarValores();
        }
    }
    private void PasarValores(){
        Intent intent = getIntent();
        intent.putExtra("ageyears", ageYears);
        setResult(RESULT_OK, intent);
        finish();
    }
    private void Cancelar(){
        setResult(RESULT_CANCELED);
        finish();
    }

}
