package com.demo.bmi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListeners();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
*/
    private Button button_calc;
    private EditText field_height;
    private EditText field_weight;
    private TextView view_result;
    private TextView view_suggest;

    private void findViews() {
        button_calc = (Button)findViewById(R.id.submit);
        field_height = (EditText)findViewById(R.id.height);
        field_weight = (EditText)findViewById(R.id.weight);
        view_result = (TextView)findViewById(R.id.result);
        view_suggest = (TextView)findViewById(R.id.suggest);
    }

    private void setListeners() {
        button_calc.setOnClickListener(calcBMI);
    }

    private Button.OnClickListener calcBMI = new OnClickListener() {
        public void onClick(View v) {
            DecimalFormat nf = new DecimalFormat("0.00");
            try {
                double height = Double.parseDouble(field_height.getText().toString())/100;
                double weight = Double.parseDouble(field_weight.getText().toString());
                double BMI = weight / (height * height);

                view_result.setText(getText(R.string.bmi_result)+nf.format(BMI));

                if (BMI>25) {
                    view_suggest.setText(R.string.advice_heavy);
                } else if (BMI<20) {
                    view_suggest.setText(R.string.advice_light);
                } else {
                    view_suggest.setText(R.string.advice_average);
                }
                openOptionsDialog();
            } catch(Exception obj) {
                Toast.makeText(MainActivity.this, R.string.toast_msg, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void openOptionsDialog() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.about_title)
                .setMessage(R.string.about_msg)
                .setPositiveButton(R.string.about_btn,  // 現在這沒用，最多三個按鈕，所以這個不做事的先取代掉(dial)
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                            }
                        })
                .setNegativeButton(R.string.about_homepage,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                Uri uri = Uri.parse(getString(R.string.about_homepage_uri));
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })
                .setNeutralButton(R.string.about_map,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                Uri uri = Uri.parse(getString(R.string.about_map_uri));
                                final Intent intent = new Intent(Intent.ACTION_VIEW, uri);  //what does "final" mean?
                                startActivity(intent);
                            }
                        })
                .setPositiveButton(R.string.about_dial,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                Uri uri = Uri.parse(getString(R.string.about_dial_uri));
                                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                                startActivity(intent);
                            }
                        })
                .show();
    }
}
