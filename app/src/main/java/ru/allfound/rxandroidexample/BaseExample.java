package ru.allfound.rxandroidexample;

import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by vvv on 05.07.16.
 */
public class BaseExample {

    public void Example1(final TextView textView) {

        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, Example1!");
                        sub.onCompleted();
                    }
                }
        );

        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) { textView.setText(s); }

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) { }
        };

        myObservable.subscribe(mySubscriber);
    }

    public void Example2(final TextView textView) {
        Observable<String> myObservable = Observable.just("Hello, Example2!");

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                textView.setText(s);
            }
        };

        myObservable.subscribe(onNextAction);
    }

    public void Example3(final TextView textView) {
        Observable.just("Hello, Example3!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        textView.setText(s);
                    }
                });
    }

    public void ExampleMap1(final TextView textView) {
        Observable.just("Hello, ExampleMap1!")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + " - Dan";
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                textView.setText(s);
            }
        });
    }

    public void ExampleMap2(final TextView textView) {
        Observable.just("Hello, ExampleMap1!")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer s) {
                textView.setText(Integer.toString(s));
            }
        });
    }

    public void ExampleMap3(final TextView textView) {
        Observable.just("Hello, ExampleMap1!")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer s) {
                        return Integer.toString(s);
                }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        textView.setText(s);
                }
        });
    }

    public void ExampleMap3_1(final TextView textView) {
        Observable.just("Hello, world!")
                .map(s -> s + " -Dan")
                .map(String::hashCode)
                .map(s -> Integer.toString(s))
                .subscribe(textView::setText);
    }
}
