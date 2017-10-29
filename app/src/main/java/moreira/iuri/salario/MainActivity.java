package moreira.iuri.salario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText salarioBruto;
    EditText dependentes;
    EditText pensao;
    Button btCalcularSalarioLiquido;
    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salarioBruto = (EditText) findViewById(R.id.txtSalarioBruto);
        dependentes = (EditText) findViewById(R.id.txtNumeroDependentes);
        pensao = (EditText) findViewById(R.id.txtPensao);
        resultado = (TextView) findViewById(R.id.txt_resultado);
    }

    public void gerarResultado(View v){

            double salario = Double.parseDouble(salarioBruto.getText().toString());
            int numDependentes = Integer.parseInt(dependentes.getText().toString());
            double pensaoAlimenticia = Double.parseDouble(pensao.getText().toString());


                double salarioLiquido = calcularSalarioLiquido(salario,numDependentes,pensaoAlimenticia);
                resultado.setText(String.valueOf(salarioLiquido));

    }

    public double calcularSalarioLiquido(double salario, int numDependentes, double pensaoAlimenticia){
        double inss = calcularINSS(salario);
        double  irpf = calcularIRPF(salario);

        return salario - inss - irpf - pensaoAlimenticia - (numDependentes*189.59);
    }

    public double calcularINSS(double salarioBruto){

        if (salarioBruto<=1659.38){

            return salarioBruto*0.08;

        }else if((salarioBruto>=1659.39)&&(salarioBruto<=2765.66)){

            return salarioBruto*0.09;

        }else if((salarioBruto>=2765.67)&&(salarioBruto<=5531.31)){

            return salarioBruto*0.11;

        }else if(salarioBruto>=5531.32){

            return salarioBruto - 608.44;
        }

        return salarioBruto;
    }

    public double calcularIRPF(double salarioBruto){

        if (salarioBruto<=1903.98){

            return salarioBruto;

        }else if((salarioBruto>=1903.99)&&(salarioBruto<=2826.65)){

            return salarioBruto*0.075;

        }else if((salarioBruto>=2826.66)&&(salarioBruto<=3751.05)){

            return salarioBruto*0.15;

        }else if((salarioBruto>=3751.06)&&(salarioBruto<=4664.68)){

            return salarioBruto*0.225;

        } else if(salarioBruto>=4664.69){

            return salarioBruto*0.275;

        }

        return salarioBruto;
    }


}
