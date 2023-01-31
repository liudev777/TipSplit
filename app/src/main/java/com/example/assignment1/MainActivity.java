package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NonNls;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText total_eV;
    private EditText numPeople_eV;
    private EditText tipAmount_eV;
    private EditText totalWTip_eV;
    private EditText totalPerPerson_eV;

    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;
    private RadioGroup radioGroup;

    private Button goBtn;
    private Button resetBtn;
    private double billTotal;
    private double tipPercent;
    private double tipAmount;
    private double totalWTip;
    private int numPeople;
    private double totalPer;
    private double percent = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tipAmount_eV = findViewById(R.id.tipAmount_eV);
        totalWTip_eV = findViewById(R.id.totalWTip_eV);
        totalPerPerson_eV = findViewById(R.id.totalPerPerson_eV);
        total_eV = findViewById(R.id.total_eV);
        numPeople_eV = findViewById(R.id.numPeople_eV);
        radio1 = findViewById(R.id.radioButton);
        radio2 = findViewById(R.id.radioButton2);
        radio3 = findViewById(R.id.radioButton3);
        radio4 = findViewById(R.id.radioButton4);
        goBtn = findViewById(R.id.goBtn);
        resetBtn = findViewById(R.id.resetBtn);
        radioGroup = findViewById(R.id.radioGroup);


        // make unclickable
        tipAmount_eV.setEnabled(false);
        totalWTip_eV.setEnabled(false);
        totalPerPerson_eV.setEnabled(false);
        radio1.setEnabled(false);
        radio2.setEnabled(false);
        radio3.setEnabled(false);
        radio4.setEnabled(false);
    }

    public void checkValue(View v) {
        String total = total_eV.getText().toString();
        if (total.equals("")) {
            setRadio(false);
            return;
        }
        if (Double.parseDouble(total) >= 0) {
            radioGroup.clearCheck();
            tipAmount_eV.setText(null);
            totalWTip_eV.setText(null);
            setRadio(true);
        } else {
            setRadio(false);
        }
    }

    public void setRadio(boolean b) {
        if (b == false) {
            radioGroup.clearCheck();
        }
        radio1.setEnabled(b);
        radio2.setEnabled(b);
        radio3.setEnabled(b);
        radio4.setEnabled(b);
    }

    public void clearFinal() {
        totalPerPerson_eV.setText(null);
    }

    public void setTip(View v) {
        RadioButton radio = findViewById(v.getId());
        clearFinal();
        switch (radio.getText().toString()) {
            case("12%"):
                percent = .12;
                break;

            case("15%"):
                percent = .15;
                break;

            case("18%"):
                percent = .18;
                break;

            case("20%"):
                percent = .20;
                break;
        }
        Log.d(TAG, radio.getText().toString());
        try {
            billTotal = Double.parseDouble(total_eV.getText().toString());
        } catch (NumberFormatException ex) {
            Toast myToast = Toast.makeText(this, "Please enter the bill total!", Toast.LENGTH_LONG);
            myToast.show();
            Log.d(TAG, ex.toString());
            return;
        }
        tipAmount = Math.ceil(billTotal * percent *100) / 100;
        totalWTip = tipAmount + Double.parseDouble(total_eV.getText().toString());
        tipAmount_eV.setText("$" + String.format("%.2f", tipAmount));
        totalWTip_eV.setText("$" + String.format("%.2f", totalWTip));
//        EditText radio = findViewById(v.getId());
//        Log.d(TAG, String.format("tip: %s", radio.getText()));
        return;
    }

    public void calculate(View v) {
        if (percent == -1) {
            Toast myToast = Toast.makeText(this, "Please select a Tip Percentage!", Toast.LENGTH_LONG);
            myToast.show();
        }
        try {
            numPeople = Integer.parseInt(numPeople_eV.getText().toString());
            if (numPeople <= 0 ) {
                Toast myToast = Toast.makeText(this, "Please input a valid number of people!", Toast.LENGTH_LONG);
                myToast.show();
                return;
            }
            double totalPerPer = Math.ceil((totalWTip / numPeople) * 100) / 100;
            totalPerPerson_eV.setText("$" + String.format("%.2f", (totalPerPer)));
        } catch (NumberFormatException ex){
            Toast myToast = Toast.makeText(this, "Please enter all fields!", Toast.LENGTH_LONG);
            myToast.show();
            return;
        } catch (Exception ex) {
            Log.d(TAG, ex.toString());
        }

    }


    public void reset(View v) {
        total_eV.setText(null);
        radioGroup.clearCheck();
        setRadio(false);
        percent = 0;
        tipAmount_eV.setText(null);
        tipAmount = 0;
        totalWTip_eV.setText(null);
        totalWTip = 0;
        numPeople_eV.setText(null);
        numPeople = 0;
        totalPerPerson_eV.setText(null);
        totalPer = 0;

//        Log.d(TAG, "Clear: ");
//        Intent intent = getIntent();
//        finish();
//        startActivity(intent);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("TOTAL_EV", total_eV.getText().toString());
        outState.putString("TIPAMOUNT_EV", tipAmount_eV.getText().toString());
        outState.putString("TOTALWTIP_EV", totalWTip_eV.getText().toString());
        outState.putString("NUMPEOPLE_EV", numPeople_eV.getText().toString());
        outState.putString("TOTALPERPERSON_EV", totalPerPerson_eV.getText().toString());
        outState.putDouble("PERCENT", percent);
        outState.putDouble("TIPAMOUNT", tipAmount);
        outState.putDouble("TOTALWTIP", totalWTip);
        outState.putInt("NUMPEOPLE", numPeople);
        outState.putDouble("TOTALPER", totalPer);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        total_eV.setText(savedInstanceState.getString("TOTAL_EV"));
        tipAmount_eV.setText(savedInstanceState.getString("TIPAMOUNT_EV"));
        totalWTip_eV.setText(savedInstanceState.getString("TOTALWTIP_EV"));
        numPeople_eV.setText(savedInstanceState.getString("NUMPEOPLE_EV"));
        totalPerPerson_eV.setText(savedInstanceState.getString("TOTALPERPERSON_EV"));

        percent = savedInstanceState.getDouble("PERCENT");
        tipAmount = savedInstanceState.getDouble("TIPAMOUNT");
        totalWTip = savedInstanceState.getDouble("TOTALWTIP");
        totalPer = savedInstanceState.getDouble("TOTALPER");
        numPeople = savedInstanceState.getInt("NUMPEOPLE");
    }



}