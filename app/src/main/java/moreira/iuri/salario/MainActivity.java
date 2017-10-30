package moreira.iuri.salario;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    EditText salarioBruto;
    EditText dependentes;
    EditText pensao;
    EditText planoMedico;
    EditText outrosDescontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salarioBruto = (EditText) findViewById(R.id.txtSalarioBruto);
        dependentes = (EditText) findViewById(R.id.txtNumeroDependentes);
        pensao = (EditText) findViewById(R.id.txtPensao);
        planoMedico = (EditText) findViewById(R.id.txtPlanoMedico);
        outrosDescontos = (EditText) findViewById(R.id.txtOutrosDescontos);

    }

    public void gerarResultado(View v){
        if(TextUtils.isEmpty(salarioBruto.getText())){

            Toast.makeText(getBaseContext(), "Preencha o campo com o seu salário bruto.", Toast.LENGTH_SHORT).show();

        }else {
            double salario = Double.parseDouble(salarioBruto.getText().toString());
            int numDependentes;
            double pensaoAlimenticia;
            double valorPlanoMedico;
            double valorOutrosDescontos;

            if(TextUtils.isEmpty(dependentes.getText())){
                numDependentes = 0;
            }else{
                numDependentes = Integer.parseInt(dependentes.getText().toString());
            }

            if(TextUtils.isEmpty(pensao.getText())){
                pensaoAlimenticia = 0;
            }else{
                pensaoAlimenticia = Double.parseDouble(pensao.getText().toString());
            }

            if(TextUtils.isEmpty(planoMedico.getText())){
                valorPlanoMedico = 0;
            }else{
                valorPlanoMedico = Double.parseDouble(planoMedico.getText().toString());
            }

            if(TextUtils.isEmpty(outrosDescontos.getText())){
                valorOutrosDescontos = 0;
            }else{
                valorOutrosDescontos = Double.parseDouble(outrosDescontos.getText().toString());
            }

            double salarioLiquido = calcularSalarioLiquido(salario, numDependentes, pensaoAlimenticia, valorPlanoMedico, valorPlanoMedico);

            salvarContato(salario, numDependentes, pensaoAlimenticia, valorPlanoMedico, valorOutrosDescontos);

            Intent resultadoActivity = new Intent(this, ResultadoActivity.class);
            resultadoActivity.putExtra("totalDescontos",calcularTotalDescontos(salario,salarioLiquido));
            resultadoActivity.putExtra("percentualDescontos",calcularPercentualDescontos(salario,salarioLiquido));
            resultadoActivity.putExtra("salarioLiquido", salarioLiquido);
            startActivity(resultadoActivity);
        }
    }

    public double calcularSalarioLiquido(double salario, int numDependentes, double pensaoAlimenticia, double planoMedico, double outrosDescontos){
        double inss = calcularINSS(salario);
        double  irpf = calcularIRPF(salario);

        return salario - inss - irpf - pensaoAlimenticia - planoMedico - outrosDescontos - (numDependentes*189.59);
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

    public double calcularTotalDescontos(double salarioBruto, double salarioLiquido){
        return salarioBruto - salarioLiquido;
    }

    public double calcularPercentualDescontos(double salarioBruto, double salarioLiquido){
        return (salarioLiquido*100)/salarioBruto;
    }

    public void salvarContato(double salario, int numDependentes, double pensaoAlimenticia, double planoMedico, double outrosDescontos) {

            String stringSalario = Double.toString(salario);
            String stringDependentes= Integer.toString(numDependentes);
            String stringPensao = Double.toString(pensaoAlimenticia);
            String stringPlanoMedico = Double.toString(planoMedico);
            String stringOutros = Double.toString(outrosDescontos);

            try {
              
                FileOutputStream fileout = openFileOutput("resultados.txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                BufferedWriter bwriter = new BufferedWriter(outputWriter);


                bwriter.write(
                        "Salario: "+stringSalario+","+"Dependentes: "+stringDependentes+","+"Pensão: "+stringPensao+","+"Plano médico: "+stringPlanoMedico+","+"Outros: "+stringOutros
                );
                bwriter.write("\n\r");
                bwriter.close();
                outputWriter.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void deletarArquivo(View v){

        try {
            FileOutputStream fileout = openFileOutput("resultados.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);

            outputWriter.write("");
            outputWriter.close();

            Toast.makeText(getBaseContext(), "Resultados apagados do arquivo.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
