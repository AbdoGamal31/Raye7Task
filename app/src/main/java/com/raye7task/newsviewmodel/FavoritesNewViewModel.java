package com.raye7task.newsviewmodel;

import android.arch.lifecycle.ViewModel;

import com.raye7task.newsmodel.Article;

import java.util.List;

public class FavoritesNewViewModel extends ViewModel {
    List<Article> articleList;

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}