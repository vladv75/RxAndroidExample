package ru.allfound.rxandroidexample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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

    public String getTitle(String url) {
        String title;
        try {
            Document doc = Jsoup.connect(url).get();
            title = doc.title();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return title;
    }
}