package ru.allfound.rxandroidexample;

import android.widget.TextView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/*
 * .java    v.1.0 05.07.2016
 *
 * Copyright (c) 2015-2016 Vladislav Laptev,
 * All rights reserved. Used by permission.
 */

public class MainExample {

    public void example0(final TextView textView, String url) {
        queryURLs(url)
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> urls) {
                        for (String url: urls) {
                            String string = (String) textView.getText();
                            textView.setText(string + url + "\n\n");
                        }
                    }
                });
    }

    public void example1(final TextView textView, String url) {
        queryURLs(url)
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> urls) {
                        Observable.from(urls)
                                .subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String url) {
                                        String string = (String) textView.getText();
                                        textView.setText(string + url + "\n\n");
                                    }
                                });
                    }
                });
    }

    public void example1_1(final TextView textView, String url) {
        queryURLs(url)
                .subscribe(urls -> {
                    Observable.from(urls)
                            .subscribe(url1 -> {
                                String string = (String) textView.getText();
                                textView.setText(string + url + "\n\n");
                            });
                });
    }

    public void example2(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> urls) {
                        return Observable.from(urls);
                    }
                })
                .subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String url) {
                                        String string = (String) textView.getText();
                                        textView.setText(string + url + "\n\n");
                                    }
                                });
    }

    public void example2_1(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(Observable::from)
                .subscribe(url1 -> {
                    String string = (String) textView.getText();
                    textView.setText(string + url1 + "\n\n");
                });
    }

    public void example3(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> urls) {
                        return Observable.from(urls);
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String url) {
                        return textView.getText() + url + "\n\n";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String url) {
                        textView.setText(url);
                    }
                });
    }

    public void example3_1(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(urls -> Observable.from(urls))
                .map(url1 -> textView.getText() + url1 + "\n\n")
                .subscribe(url1 -> {
                    textView.setText(url1);
                });
    }

    public void example3_2(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(Observable::from)
                .map(url1 -> textView.getText() + url1 + "\n\n")
                .subscribe(textView::setText);
    }

    public void example3_3(final TextView textView, String url) {
        queryTitle(url)
                .subscribe(textView::setText);
    }

    public void example4(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> urls) {
                        return Observable.from(urls);
                    }
                })
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String url) {
                        return queryTitle(url);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String title) {
                        textView.setText(title);
                    }
                });
    }

    public void example4_1(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(Observable::from)
                .flatMap(this::queryTitle)
                .subscribe(textView::setText);
    }

    public void example4_2(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(Observable::from)
                .flatMap(this::queryTitle)
                .map(url1 -> textView.getText() + url1 + "\n\n")
                .subscribe(textView::setText);
    }

    public void example4_3(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(Observable::from)
                .flatMap(this::queryTitle)
                .filter(title -> title != null)
                .map(url1 -> textView.getText() + url1 + "\n\n")
                .subscribe(textView::setText);
    }

    public void example4_4(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(Observable::from)
                .flatMap(this::queryTitle)
                .filter(title -> title != null)
                .take(7)
                .map(url1 -> textView.getText() + url1 + "\n\n")
                .subscribe(textView::setText);
    }

    public void example5(final TextView textView, String url) {
        queryTitles(url)
                .flatMap(Observable::from)
                .subscribe(textView::setText);
    }

    public void example6(final TextView textView) {
        Observable<List<String>> zipped
                = Observable.zip(
                queryURLs("https://yandex.ru/"),
                queryURLs("https://mail.ru/"),
                new Func2<List<String>, List<String>, List<String>>() {
            @Override
            public List<String> call(List<String> list1, List<String> list2) {
                list1.addAll(list2);
                return list1;
            }
        });

        zipped
                .flatMap(Observable::from)
                .flatMap(this::queryTitle)
                .filter(title -> title != null)
                .map(url1 -> textView.getText() + url1 + "\n\n")
                .subscribe(textView::setText);
    }

    public void example6_1(final TextView textView) {
        Observable<List<String>> zipped
                = Observable.zip(
                queryURLs("https://yandex.ru/"),
                queryURLs("https://mail.ru/"),
                (list1, list2) -> {
                    list1.addAll(list2);
                    return list1;
                });

        zipped
                .flatMap(Observable::from)
                .flatMap(this::queryTitle)
                .filter(title -> title != null)
                .map(url1 -> textView.getText() + url1 + "\n\n")
                .subscribe(textView::setText);
    }

    Observable<List<String>> queryURLs(String url) {
        WebParsing webParsing = new WebParsing();
        return Observable.create(
                new Observable.OnSubscribe<List<String>>() {
                    @Override
                    public void call(Subscriber<? super List<String>> subscriber) {
                        subscriber.onNext(webParsing.getURLs(url));
                        subscriber.onCompleted();
                    }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    Observable<List<String>> queryTitles(String url) {
        WebParsing webParsing = new WebParsing();
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                subscriber.onNext(webParsing.getTitles(url));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    Observable<String> queryTitle(String url) {
        WebParsing webParsing = new WebParsing();
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(webParsing.getTitle(url));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
