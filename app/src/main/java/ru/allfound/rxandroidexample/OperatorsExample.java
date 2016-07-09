package ru.allfound.rxandroidexample;

import android.widget.TextView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by vvv on 08.07.16.
 */

public class OperatorsExample {

    public void Example1(final TextView textView, String url) {
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

    public void Example1_1(final TextView textView, String url) {
        queryURLs(url)
                .subscribe(urls -> {
                    Observable.from(urls)
                            .subscribe(url1 -> {
                                String string = (String) textView.getText();
                                textView.setText(string + url + "\n\n");
                            });
                });
    }

    public void Example2(final TextView textView, String url) {
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

    public void Example2_1(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(Observable::from)
                .subscribe(url1 -> {
                    String string = (String) textView.getText();
                    textView.setText(string + url1 + "\n\n");
                });
    }

    public void Example3(final TextView textView, String url) {
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

    public void Example3_1(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(urls -> Observable.from(urls))
                .map(url1 -> textView.getText() + url1 + "\n\n")
                .subscribe(url1 -> {
                    textView.setText(url1);
                });
    }

    public void Example3_2(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(Observable::from)
                .map(url1 -> textView.getText() + url1 + "\n\n")
                .subscribe(textView::setText);
    }

    public void Example3_3(final TextView textView, String url) {
        queryTitle(url)
                .subscribe(textView::setText);
    }

    public void Example4(final TextView textView, String url) {
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

    public void Example4_1(final TextView textView, String url) {
        queryURLs(url)
                .flatMap(Observable::from)
                .flatMap(this::queryTitle)
                .subscribe(textView::setText);
    }

    Observable<List<String>> queryURLs(String url) {
        WebParsing webParsing = new WebParsing();
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                subscriber.onNext(webParsing.getURLs(url));
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
