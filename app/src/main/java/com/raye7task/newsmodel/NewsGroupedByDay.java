package com.raye7task.newsmodel;

import java.util.ArrayList;
import java.util.List;

public class NewsGroupedByDay {
    private String day;
    private List<Article> articleList;

    public NewsGroupedByDay(String day, List<Article> articleList) {
        this.day = day;
        this.articleList = articleList;
    }

    public String getDay() {
        return day;
    }

    public List<Article> getArticleList() {
        return articleList;
    }
}
