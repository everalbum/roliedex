package com.everalbum.roliedex.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.everalbum.roliedex.RoliedexLayout;

public class MainActivity extends AppCompatActivity {

    private EditText       enterText;
    private Button         tryItButton;
    private RoliedexLayout roliedex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterText = (EditText) findViewById(R.id.enter_text);
        tryItButton = (Button) findViewById(R.id.try_it_button);
        roliedex = (RoliedexLayout) findViewById(R.id.counter);

        tryItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roliedex.setText(Double.valueOf(enterText.getText()
                                                         .toString()),
                                 " MB",
                                 true);
            }
        });
    }
}