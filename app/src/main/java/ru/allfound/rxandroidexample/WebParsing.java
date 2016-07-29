package ru.allfound.rxandroidexample;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/*
 * WebParsing.java    v.1.0 07.07.2016
 *
 * Copyright (c) 2015-2016 Vladislav Laptev,
 * All rights reserved. Used by permission.
 */

public class WebParsing {
    public WebParsing() {
    }

    public String parsing(String url) {
        Document doc;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            doc = Jsoup.connect(url).get();
            stringBuilder.append(doc.title()).append("\n\n");

            Elements select = doc.select("a");
            for (Element element : select) {
                stringBuilder.append(element.text()).append(" - ").append(element.attr("href")).append("\n\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return stringBuilder.toString();
    }

    public List<String> getURLs(String url) {
        Document doc;
        List<String> stringList = new ArrayList<>();
        try {
            doc = Jsoup.connect(url).get();
            Elements select = doc.select("a");
            for (Element element : select) {
                stringList.add(element.attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return stringList;
    }

    public List<String> getTitles(String url) {
        Document doc;
        List<String> stringList = new ArrayList<>();
        try {
            doc = Jsoup.connect(url).get();
            Elements select = doc.select("a");
            for (Element element : select) {
                try {
                    Document docTitle = Jsoup.connect(element.attr("href")).get();
                    stringList.add(docTitle.title());
                } catch (IOException exp) {
                    exp.printStackTrace();
                    stringList.add(null);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return stringList;
    }

    public String getTitle(String url) {
        String title;
        try {
            Document doc = Jsoup.connect(url).get();
            title = doc.title();
        } catch (MalformedURLException mue ) {
            //mue.printStackTrace();
            return null;
        } catch (HttpStatusException hse) {
            //hse.printStackTrace();
            return null;
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        } catch (IllegalArgumentException iae) {
            //iae.printStackTrace();
            return null;
        }
        return title;
    }
}