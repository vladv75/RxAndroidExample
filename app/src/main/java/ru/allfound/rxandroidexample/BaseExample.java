package ru.allfound.rxandroidexample;

import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/*
 * BaseExample.java    v.1.0 05.07.2016
 *
 * Copyright (c) 2015-2016 Vladislav Laptev,
 * All rights reserved. Used by permission.
 */

public class BaseExample {

    public void example1(final TextView textView) {

        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("example1");
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

    public void example2(final TextView textView) {
        Observable<String> myObservable = Observable.just("example2");

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                textView.setText(s);
            }
        };

        myObservable.subscribe(onNextAction);
    }

    public void example3(final TextView textView) {
        Observable.just("example3")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        textView.setText(s);
                    }
                });
    }

    public void exampleMap1(final TextView textView) {
        Observable.just("exampleMap1")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + " + data...";
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                textView.setText(s);
            }
        });
    }

    public void exampleMap2(final TextView textView) {
        Observable.just("exampleMap2")
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

    public void exampleMap3(final TextView textView) {
        Observable.just("exampleMap3")
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

    public void exampleMap3_1(final TextView textView) {
        Observable.just("exampleMap3_1")
                .map(s -> s + " + data...")
                .map(String::hashCode)
                .map(s -> Integer.toString(s))
                .subscribe(textView::setText);
    }
}
