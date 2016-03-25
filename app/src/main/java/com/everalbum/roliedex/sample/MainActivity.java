package com.everalbum.roliedex.sample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.everalbum.roliedex.RoliedexLayout;

public class MainActivity extends AppCompatActivity {

    private EditText       enterNumber;
    private EditText       enterDecorator;
    private SwitchCompat   forceDecimal;
    private Button         tryItButton;
    private RoliedexLayout roliedex;
    private RoliedexLayout roliedexSlow;
    private RoliedexLayout roliedexLonger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterNumber = (EditText) findViewById(R.id.enter_number);
        enterDecorator = (EditText) findViewById(R.id.enter_decorator);
        forceDecimal = (SwitchCompat) findViewById(R.id.switch_force_decimals);
        tryItButton = (Button) findViewById(R.id.try_it_button);
        roliedex = (RoliedexLayout) findViewById(R.id.counter);
        roliedexSlow = (RoliedexLayout) findViewById(R.id.slow_counter);
        roliedexLonger = (RoliedexLayout) findViewById(R.id.longer_counter);

        tryItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = enterNumber.getText()
                                           .toString();
                String decorator = enterDecorator.getText()
                                                 .toString();
                double value = "".equals(number) ? 0.0 : Double.valueOf(number);

                roliedex.setText(value,
                                 "".equals(decorator) ? null : " " + decorator,
                                 forceDecimal.isChecked());
                roliedexSlow.setText(value,
                                     "".equals(decorator) ? null : " " + decorator,
                                     forceDecimal.isChecked());
                roliedexLonger.setText(value,
                                       "".equals(decorator) ? null : " " + decorator,
                                       forceDecimal.isChecked());
            }
        });

        forceDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }
}