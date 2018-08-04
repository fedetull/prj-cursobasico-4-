package com.calculadoraedad.fedetull.agecalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txEdad, txDifEdad;
    private final static int ageyear = 0;
    private ArrayList<Integer> diferencia = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        findViewById(R.id.btnCalcularEdad).setOnClickListener(this);
        txEdad = findViewById(R.id.txEdad);
        txDifEdad = findViewById(R.id.txDifEdad);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCalcularEdad:
                Intent intent = new Intent(this, SelectorEdad.class);
                startActivityForResult(intent, ageyear);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_CANCELED".
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Cancelado",Toast.LENGTH_SHORT).show();
        } else {
            // De lo contrario, recogemos el resultado de la segunda actividad.
            int edad = Objects.requireNonNull(data.getExtras()).getInt("ageyears");
            diferencia.add(edad);
            DiferenciaEdad();
            String text;
            text = Integer.toString(edad);
            switch (requestCode) {
                case ageyear:
                    txEdad.setText(text);
                    break;
            }
        }
    }
    public void DiferenciaEdad() {
        int i;
        int calcular = 0;
        i = diferencia.size();
        StringBuilder strdiferencia = new StringBuilder();
        if (i > 1) {
            int valor1 = diferencia.get(i - 2);
            int valor2 = diferencia.get(i - 1);
            calcular = valor2 - valor1;
        }
        if (calcular < 0) {
            calcular= calcular * -1;
            strdiferencia.append("Usted es ");
            strdiferencia.append(calcular);
            strdiferencia.append(" años menor");
            String texto = strdiferencia.toString();
            txDifEdad.setText(texto);
        } else {
            if (calcular > 0) {
                strdiferencia.append("Usted es ");
                strdiferencia.append(calcular);
                strdiferencia.append(" años mayor");
                String texto = strdiferencia.toString();
                txDifEdad.setText(texto);
            }
        }
    }
}
