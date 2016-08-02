package ru.allfound.rxandroidexample;

import android.widget.TextView;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vvv on 02.08.16.
 */
public class Test {
    public void test1(final TextView textView, String url, long startTime) {
        queryURLs(url)
                .flatMap(Observable::from)
                .flatMap(this::queryTitle)
                .filter(title -> title != null)
                .take(5)
                .map(url1 -> textView.getText() + url1
                        + "(" + (System.currentTimeMillis() - startTime) + " ms)" + "\n")
                .subscribe(textView::setText);
    }

    public void test2(final TextView textView, String url, long startTime) {
        queryURLs1(url)
                .flatMap(Observable::from)
                .flatMap(this::queryTitle1)
                .observeOn(AndroidSchedulers.mainThread())
                .filter(title -> title != null)
                .take(5)
                .map(url1 -> textView.getText() + url1
                        + "(" + (System.currentTimeMillis() - startTime) + " ms)" + "\n")
                .subscribe(textView::setText);
    }

    public void test3(final TextView textView, String url, long startTime) {
        queryURLs2(url)
                .flatMap(Observable::from)
                .flatMap(this::queryTitle2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(title -> title != null)
                .take(5)
                .map(url1 -> textView.getText() + url1
                        + "(" + (System.currentTimeMillis() - startTime) + " ms)" + "\n")
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

    Observable<List<String>> queryURLs1(String url) {
        WebParsing webParsing = new WebParsing();
        return Observable.create(
                new Observable.OnSubscribe<List<String>>() {
                    @Override
                    public void call(Subscriber<? super List<String>> subscriber) {
                        subscriber.onNext(webParsing.getURLs(url));
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.io());
    }

    Observable<List<String>> queryTitles1(String url) {
        WebParsing webParsing = new WebParsing();
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                subscriber.onNext(webParsing.getTitles(url));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    Observable<String> queryTitle1(String url) {
        WebParsing webParsing = new WebParsing();
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(webParsing.getTitle(url));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    Observable<List<String>> queryURLs2(String url) {
        WebParsing webParsing = new WebParsing();
        return Observable.create(
                new Observable.OnSubscribe<List<String>>() {
                    @Override
                    public void call(Subscriber<? super List<String>> subscriber) {
                        subscriber.onNext(webParsing.getURLs(url));
                        subscriber.onCompleted();
                    }
                });
    }

    Observable<List<String>> queryTitles2(String url) {
        WebParsing webParsing = new WebParsing();
        return Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                subscriber.onNext(webParsing.getTitles(url));
                subscriber.onCompleted();
            }
        });
    }

    Observable<String> queryTitle2(String url) {
        WebParsing webParsing = new WebParsing();
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(webParsing.getTitle(url));
                subscriber.onCompleted();
            }
        });
    }
}
