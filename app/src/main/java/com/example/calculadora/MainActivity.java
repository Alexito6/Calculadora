package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button clear;
    private Button buttonDivision;
    private Button buttonSuma;
    private Button buttonResta;
    private Button buttonMulti;
    private Button buttonResultado;
    private Button buttonPunto;
    private CheckBox checkBoxOpciones;
    private RadioGroup opciones;
    private TextView operacionTextView;
    private double primerNum,segundoNum,resultado;
    private int indiceOperador;
    private boolean suma,resta,multiplicacion,division, primerPunto, segundoPunto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.calculadora);
        clear=findViewById(R.id.clear);
        buttonDivision=findViewById(R.id.buttonDiv);
        buttonSuma=findViewById(R.id.buttonSuma);
        buttonMulti=findViewById(R.id.buttonMulti);
        buttonResta=findViewById(R.id.buttonResta);
        buttonResultado=findViewById(R.id.buttonResultado);
        buttonPunto=findViewById(R.id.buttonPunto);
        checkBoxOpciones=findViewById(R.id.checkBoxOpciones);
        opciones=findViewById(R.id.opciones);
        opciones.setVisibility(View.INVISIBLE);
        operacionTextView =findViewById(R.id.textView);
        primerPunto=false;
        segundoPunto=false;
        if (savedInstanceState != null) {
            String textoTextView=savedInstanceState.getString("texto");
            operacionTextView.setText(textoTextView);
            suma=savedInstanceState.getBoolean("booleanSuma");
            resta=savedInstanceState.getBoolean("booleanResta");
            multiplicacion=savedInstanceState.getBoolean("booleanMulti");
            division=savedInstanceState.getBoolean("booleanDiv");
            primerPunto=savedInstanceState.getBoolean("booleanPrimerPunto");
            segundoPunto=savedInstanceState.getBoolean("booleanSegundoPunto");
            primerNum=savedInstanceState.getDouble("primerNum");
            segundoNum=savedInstanceState.getDouble("segundoNum");
        }
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operacionTextView.setText("0");
                indiceOperador =0;
                primerNum=0;
                segundoNum=0;
                primerPunto=false;
                segundoPunto=false;
                suma=false;
                division=false;
                resta=false;
                multiplicacion=false;
            }
        });
        checkBoxOpciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    opciones.setVisibility(View.VISIBLE);
                } else {
                    opciones.setVisibility(View.INVISIBLE);
                }
            }
        });
        opciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.desSuma){
                    buttonSuma.setVisibility(View.INVISIBLE);
                    buttonResta.setVisibility(View.VISIBLE);
                    buttonDivision.setVisibility(View.VISIBLE);
                    buttonMulti.setVisibility(View.VISIBLE);
                }else if (checkedId==R.id.desResta){
                    buttonSuma.setVisibility(View.VISIBLE);
                    buttonResta.setVisibility(View.INVISIBLE);
                    buttonDivision.setVisibility(View.VISIBLE);
                    buttonMulti.setVisibility(View.VISIBLE);
                } else if (checkedId==R.id.desDiv) {
                    buttonSuma.setVisibility(View.VISIBLE);
                    buttonResta.setVisibility(View.VISIBLE);
                    buttonDivision.setVisibility(View.INVISIBLE);
                    buttonMulti.setVisibility(View.VISIBLE);
                } else if (checkedId==R.id.desMulti){
                    buttonSuma.setVisibility(View.VISIBLE);
                    buttonResta.setVisibility(View.VISIBLE);
                    buttonDivision.setVisibility(View.VISIBLE);
                    buttonMulti.setVisibility(View.INVISIBLE);
                }
            }
        });
        //Seccion de sintaxis
        buttonSuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!operacionTextView.getText().toString().contains("+") && !isInProcess()){
                    primerNum =Double.parseDouble(operacionTextView.getText().toString());
                    operacionTextView.setText(operacionTextView.getText()+"+");
                    indiceOperador =operacionTextView.getText().toString().length()-1;
                    suma=true;
                    division=false;
                    resta=false;
                    multiplicacion=false;
                }
            }
        });
        buttonDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!operacionTextView.getText().toString().contains("/") && !isInProcess()){
                    primerNum =Double.parseDouble(operacionTextView.getText().toString());
                    operacionTextView.setText(operacionTextView.getText()+"/");
                    indiceOperador =operacionTextView.getText().toString().length()-1;
                    suma=false;
                    division=true;
                    resta=false;
                    multiplicacion=false;
                }
            }
        });
        buttonMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!operacionTextView.getText().toString().contains("*") && !isInProcess()){
                    primerNum =Double.parseDouble(operacionTextView.getText().toString());
                    operacionTextView.setText(operacionTextView.getText()+"*");
                    indiceOperador =operacionTextView.getText().toString().length()-1;
                    suma=false;
                    division=false;
                    resta=false;
                    multiplicacion=true;
                }
            }
        });
        buttonResta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!operacionTextView.getText().toString().contains("-") && !isInProcess()){
                    primerNum =Double.parseDouble(operacionTextView.getText().toString());
                    operacionTextView.setText(operacionTextView.getText()+"-");
                    indiceOperador =operacionTextView.getText().toString().length()-1;
                    suma=false;
                    division=false;
                    resta=true;
                    multiplicacion=false;
                } else if (Double.parseDouble(operacionTextView.getText().toString())<0 && !isInProcess()) {
                    primerNum =Double.parseDouble(operacionTextView.getText().toString());
                    operacionTextView.setText(operacionTextView.getText()+"-");
                    indiceOperador =operacionTextView.getText().toString().length()-1;
                    suma=false;
                    division=false;
                    resta=true;
                    multiplicacion=false;
                }
            }
        });
        buttonPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (operacionTextView.getText().toString().charAt(operacionTextView.getText().toString().length()-1)!=46){
                    if (!operacionTextView.getText().toString().contains(".") && !isInProcess() && !primerPunto){
                        operacionTextView.setText(operacionTextView.getText()+".");
                        primerPunto =true;
                    } else if (isInProcess() && operacionTextView.getText().toString().charAt(indiceOperador)!=43 && operacionTextView.getText().toString().charAt(indiceOperador)!=42 && operacionTextView.getText().toString().charAt(indiceOperador)!=47 && operacionTextView.getText().toString().charAt(indiceOperador)!=45 && !segundoPunto) {
                        operacionTextView.setText(operacionTextView.getText()+".");
                        segundoPunto =true;
                    }
                }
            }
        });
        //Resultado operacion
        buttonResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                segundoNum=Double.parseDouble(operacionTextView.getText().toString().substring(indiceOperador+1));
                if (suma){
                    resultado=primerNum+segundoNum;
                } else if (resta) {
                    resultado=primerNum-segundoNum;
                } else if (division) {
                    resultado=primerNum/segundoNum;
                } else if (multiplicacion) {
                    resultado=primerNum*segundoNum;
                }
                operacionTextView.setText(String.valueOf(resultado));
                primerNum=resultado;
                segundoNum=0;
                suma=false;
                resta=false;
                multiplicacion=false;
                division=false;
            }
        });
    }
    public void onClick(View view){
        if (operacionTextView.getText().equals("0")){
            operacionTextView.setText(((Button) view).getText());
            indiceOperador=0;
        }
        else {
            operacionTextView.setText(operacionTextView.getText().toString()+((Button) view).getText());
        }
    }
    public boolean isInProcess(){
        return suma || resta || multiplicacion || division;
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("texto", operacionTextView.getText().toString());
        outState.putSerializable("booleanSuma",suma);
        outState.putSerializable("booleanResta",resta);
        outState.putSerializable("booleanMulti",multiplicacion);
        outState.putSerializable("booleanDiv",division);
        outState.putSerializable("booleanPrimerPunto",primerPunto);
        outState.putSerializable("booleanSegundoPunto",segundoPunto);
        outState.putSerializable("primerNum",primerNum);
        outState.putSerializable("segundoNum",segundoNum);
    }
}