package ru.allfound.rxandroidexample;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView textView1, textView2, textView3, textViewTitle1, textViewTitle2, textViewTitle3,
            textViewTime1, textViewTime2, textViewTime3;
    long start, stop;
    Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);

        textViewTime1 = (TextView) findViewById(R.id.textViewTime1);
        textViewTime2 = (TextView) findViewById(R.id.textViewTime2);
        textViewTime3 = (TextView) findViewById(R.id.textViewTime3);

        textViewTitle1 = (TextView) findViewById(R.id.textViewTitle1);
        textViewTitle1.setText("Test 1");
        textViewTitle2 = (TextView) findViewById(R.id.textViewTitle2);
        textViewTitle2.setText("Test 2");
        textViewTitle3 = (TextView) findViewById(R.id.textViewTitle3);
        textViewTitle3.setText("Test 3");

        Test test = new Test();

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(view -> {
            textView1.setText(" ");
            textView2.setText(" ");
            textView3.setText(" ");
            //test 3
            start = System.nanoTime();
            test.test3(textView3, "https://yandex.ru/", System.currentTimeMillis());
            stop = System.nanoTime();
            textViewTime3.setText(String.format("%,d ns", stop-start));
            //test 1
            start = System.nanoTime();
            test.test1(textView1, "https://yandex.ru/", System.currentTimeMillis());
            stop = System.nanoTime();
            textViewTime1.setText(String.format("%,d ns", stop-start));
            //test 2
            start = System.nanoTime();
            test.test2(textView2, "https://yandex.ru/", System.currentTimeMillis());
            stop = System.nanoTime();
            textViewTime2.setText(String.format("%,d ns", stop-start));
        });
    }
}
