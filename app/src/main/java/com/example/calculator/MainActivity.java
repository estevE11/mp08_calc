package com.example.calculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String NAME_LLIURE = "Lliure";
    private final String NAME_DOLLAR = "Dollar";
    private final String NAME_YEN = "Yen";
    private final String NAME_YUAN = "Yuan";

    private double CONV_LLIURE = 0;
    private double CONV_DOLLAR = 0;
    private double CONV_YEN = 0;
    private double CONV_YUAN = 0;
    private double conversion = 1.1;

    private String input = "";

    private int[] buttons_ids = {
            R.id.btn_0,
            R.id.btn_1,
            R.id.btn_2,
            R.id.btn_3,
            R.id.btn_4,
            R.id.btn_5,
            R.id.btn_6,
            R.id.btn_7,
            R.id.btn_8,
            R.id.btn_9,
            R.id.btn_ce,
            R.id.btn_del,
            R.id.btn_com,
            R.id.btn_lliures,
            R.id.btn_dollar,
            R.id.btn_yen,
            R.id.btn_yuan

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int id : buttons_ids) {
            findViewById(id).setOnClickListener(this);
        }

        this.updateValue();
    }

    private void handleClick(int id) {
        switch(id) {
            case R.id.btn_0:
                this.addNumber(0);
                break;
            case R.id.btn_1:
                this.addNumber(1);
                break;
            case R.id.btn_2:
                this.addNumber(2);
                break;
            case R.id.btn_3:
                this.addNumber(3);
                break;
            case R.id.btn_4:
                this.addNumber(4);
                break;
            case R.id.btn_5:
                this.addNumber(5);
                break;
            case R.id.btn_6:
                this.addNumber(6);
                break;
            case R.id.btn_7:
                this.addNumber(7);
                break;
            case R.id.btn_8:
                this.addNumber(8);
                break;
            case R.id.btn_9:
                this.addNumber(9);
                break;
            case R.id.btn_del:
                this.deleteLast();
                break;
            case R.id.btn_ce:
                this.clearInput();
                break;
            case R.id.btn_com:
                this.addComma();
                break;
            case R.id.btn_lliures:
                if(this.CONV_LLIURE == 0) this.openDialogConversion(this.NAME_LLIURE);
                else this.changeConversion(this.NAME_LLIURE, this.CONV_LLIURE);
                break;
            case R.id.btn_dollar:
                if(this.CONV_DOLLAR == 0) this.openDialogConversion(this.NAME_DOLLAR);
                else this.changeConversion(this.NAME_DOLLAR, this.CONV_DOLLAR);
                break;
            case R.id.btn_yen:
                if(this.CONV_YEN == 0) this.openDialogConversion(this.NAME_YEN);
                else this.changeConversion(this.NAME_YEN, this.CONV_YEN);
                break;
            case R.id.btn_yuan:
                if(this.CONV_YUAN == 0) this.openDialogConversion(this.NAME_YUAN);
                else this.changeConversion(this.NAME_YUAN, this.CONV_YUAN);
                break;
        }
    }

    private void addNumber(int n) {
        if(getNDecimals() > 1) return;
        try {
            this.input += String.valueOf(n);
            this.updateValue();
        } catch(Exception e) {
            Toast.makeText(this, "Error, int needed", Toast.LENGTH_SHORT).show();
        }
    }

    private void addComma() {
        if(this.input.equals("")) {
            this.addNumber(0);
        } else if(this.input.endsWith(".") || this.input.endsWith("0") || this.getInput() % 1 != 0 || getNDecimals() > 1) return;
        try {
            this.input += String.valueOf(".");
            this.updateValue();
        } catch(Exception e) {
            Toast.makeText(this, "Error, int needed", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteLast() {
        if(this.input.equals("0.")) {
            this.input = "";
            this.updateValue();
        }
        if(this.input.equals("")) return;
        this.input = this.input.substring(0, this.input.length()-1);
        this.updateValue();
    }

    private void clearInput() {
        this.input = "";
        this.updateValue();
    }

    private void updateValue() {
        ((TextView) findViewById(R.id.textView_input)).setText(this.input);
        double res = this.getInput()*this.conversion;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);
        ((TextView) findViewById(R.id.textView_result)).setText(df.format(res));
    }

    private double getInput() {
        if(this.input.equals("")) return 0;
        String in = this.input;
        if(in.endsWith(".")) in = in.substring(0, in.length()-1);
        try {
            return Double.parseDouble(in);
        } catch(Exception e) {
            Toast.makeText(this, "Error parsing input to int", Toast.LENGTH_SHORT).show();
        }
        return -1;
    }

    private void openDialogConversion(final String conv_name) {
        AlertDialog ad;

        ad = new AlertDialog.Builder(this).create();
        ad.setTitle("");
        ad.setMessage("Please insert the current currency value");

        // Ahora forzamos que aparezca el editText
        final EditText edtValor = new EditText(this);
        ad.setView(edtValor);

        ad.setButton(AlertDialog.BUTTON_POSITIVE, "Insert", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                double val = 0;
                try {
                    val = Double.parseDouble(edtValor.getText().toString());
                    updateConversion(conv_name, val);
                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "Input needs to be a number!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // no fem res.
            }
        });
        ad.show();
    }

    private void updateConversion(String name, double val) {
        Toast.makeText(getApplicationContext(), name + ": " + val, Toast.LENGTH_SHORT).show();
        switch (name) {
            case "Lliure":
                this.CONV_LLIURE = val;
                break;
            case "Dollar":
                this.CONV_DOLLAR = val;
                break;
            case "Yen":
                this.CONV_YEN = val;
                break;
            case "Yuan":
                this.CONV_YUAN = val;
                break;
        }
        this.changeConversion(name, val);
    }

    private void changeConversion(String name, double val) {
        this.conversion = val;
        ((TextView) findViewById(R.id.textView_conv)).setText(name + " = " + String.valueOf(this.conversion));
        this.updateValue();
    }

    private int getNDecimals() {
        String[] arr = this.input.split("\\.");
        if(arr.length == 1) return 0;
        return arr[1].length();
    }

    public void onClick(View v) {
        this.handleClick(v.getId());
    }
}
