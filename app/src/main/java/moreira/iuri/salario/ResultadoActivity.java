package moreira.iuri.salario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultadoActivity extends AppCompatActivity {

    TextView totalLiquido;
    TextView totalDescontos;
    TextView percentualDescontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        totalLiquido = (TextView) findViewById(R.id.liquido);
        totalDescontos = (TextView) findViewById(R.id.totalDescontos);
        percentualDescontos = (TextView) findViewById(R.id.percentualDescontos);

        Bundle intent = getIntent().getExtras();
        double salarioLiquido = intent.getDouble("salarioLiquido");
        double valorDescontos = intent.getDouble("totalDescontos");
        double percentDescontos = intent.getDouble("percentualDescontos");

        totalLiquido.setText(Double.toString(salarioLiquido));
        totalDescontos.setText(Double.toString(valorDescontos));
        percentualDescontos.setText(String.format("%.2f",percentDescontos));

    }
}
