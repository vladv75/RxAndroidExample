package ru.allfound.rxandroidexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        OperatorsExample operatorsExample = new OperatorsExample();
        operatorsExample.Example0(textView, "https://yandex.ru/");
    }

}
