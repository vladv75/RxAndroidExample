package ru.allfound.rxandroidexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import rx.Observable;
import rx.functions.Action1;

public class MainActivity1 extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);

        MainExample mainExample = new MainExample();

        textView = (TextView) findViewById(R.id.textView);

        Button loadButton = (Button) findViewById(R.id.loadButton);
        assert loadButton != null;
        Observable<Void> clicksObservable = RxView.clicks(loadButton);

        clicksObservable
                .skip(2)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void v) {
                        mainExample.example4_4(textView, "https://yandex.ru/");
                    }
                });
    }
}
